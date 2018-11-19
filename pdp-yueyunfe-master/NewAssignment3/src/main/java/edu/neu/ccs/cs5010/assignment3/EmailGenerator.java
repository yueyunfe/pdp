package edu.neu.ccs.cs5010.assignment3;


import java.io.*;
import java.nio.charset.Charset;

public class EmailGenerator {

  private static String fileName;
  private static String template;
  private static String error = "\n";
  private static InfoFactory infoFactory;
  private static String departureCity;
  private static String destinationCity;
  private static String flightNumber;
  private static String folderName;
  private static String event;
  private static final String INSTRUCTION =
          "Usage:\n" +
                  "--email-template <file>                               accepts a filename that holds the email template.\n" +
                  "--output-dir <path>                                   accepts the name of a folder, all output is placed in this folder\n" +
                  "--csv-file <path>                                     accepts the name of the csv file to process, in a following format:Flight<id>From<departure-city>To<destination-city>.csv\n" +
                  "--event <details>                                     specifies if the delay refers to departure/arrival";


  public static void main(String[] argv) {

    System.out.println(INSTRUCTION + "\nPlease enter: \n");
    //check for the user input if valid
    if (!checkInput(argv)) {
      System.out.println(error);
      return;
    }

    infoFactory = new InfoFactory(fileName, template);
    infoFactory.setDepartureCity(departureCity);
    infoFactory.setDestinationCity(destinationCity);
    infoFactory.setFlightNumber(flightNumber);

    //check for if reading csv file successfully
    if (!infoFactory.readInfoFile()) {
      throw new IllegalArgumentException("Fail to read the csv file");
    }
    //check for if reading template file successfully
    if (!infoFactory.readTemplateFile()) {
      throw new IllegalArgumentException("Fail to read the template file");
    }

    //create the folder for output
    File file = new File(folderName);
    if (!file.exists()&&!file.mkdir()) {
        throw new IllegalArgumentException("Fail to create folder");
    }

    //pull the information and output to the folder
    for (int i = 1; i < infoFactory.getInfoList().size(); i++) {
      //process replacement
      if (!infoFactory.replaceTemplatePlaceHolder(infoFactory.getInfoList().get(i), event)) {
        System.out.println("Incorrect for replacing");
        return;
      }
      //output the result
      try {
        File childFile = new File(file.getPath() + "/email" + i + ".txt");
        PrintWriter outPutStream = new PrintWriter(new OutputStreamWriter(new FileOutputStream(childFile), Charset.defaultCharset()));
        outPutStream.println(infoFactory.getResult());
        outPutStream.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * This is the method to factory split and process the user input in order to get
   * the file name
   *
   * @param regex       expression for slit
   * @param inputString user input
   * @param fileType    template||msv file||output directory||event
   * @return the name of file for 4 different category, null if not exists.
   */
  private static String getInputFileName(String regex, String inputString, String fileType) {
    //split at eg. --email-template
    String[] parts = inputString.split(regex);
    //get all string after --email-template
    if (parts.length == 1) {
      error += "Error:  Missing " + fileType + " file \n" + INSTRUCTION;
      return null;
    }
    String restString = parts[1].trim();
    String templateName = "";
    //get the name from the rest of string
    if (restString.contains("--") && restString.charAt(0) != '-') {
      templateName = restString.substring(0, restString.indexOf("--")).trim();
    } else if (restString.length() == 0 || restString.charAt(0) == '-') {
      error += "Error:  Missing " + fileType + " file \n" + INSTRUCTION;
      return null;
    } else {
      templateName = restString;
    }
    return templateName;
  }

  /**
   * This is used to check if the user input is valid or not
   *
   * @param args the command from user side
   * @return true if input is valid
   */

  public static boolean checkInput(String[] args) {
    //check for command correctness
    String input = "";
    for (int i = 0; i < args.length; i++) {
      input += args[i];
    }
    Boolean missingCommand = false;
    if (!input.contains("--event")) {
      error += "Error:  Missing event command \n";
      missingCommand = true;
    }
    if (!input.contains("--email-template")) {
      error += "Error:  Missing template command \n";
      missingCommand = true;
    }
    if (!input.contains("--output-dir")) {
      error += "Error:  Missing output directory command \n";
      missingCommand = true;
    }
    if (!input.contains("--csv-file")) {
      error += "Error: Missing csv file command \n";
      missingCommand = true;
    }
    if (missingCommand) {
      error += INSTRUCTION;
      return false;
    }

    //check for email template

    String templateName = getInputFileName("--email-template", input, "template");
    if (templateName == null) {
      return false;
    }
    if (templateName.contains(".")) {
      template = templateName;
      String[] fileName = templateName.split("\\.");
      String prefix = fileName[0].trim();
      String suffix = fileName[1].trim();
      if (prefix.length() == 0 || suffix.length() == 0 || !suffix.equals("txt")) {
        error += "Error:  Wrong format of template file \n" + INSTRUCTION;
        return false;
      }
    } else {
      error += "Error:  Wrong format of template file \n" + INSTRUCTION;
      return false;
    }


    //check for output directory
    String directoryName = getInputFileName("--output-dir", input, "directory");
    if (directoryName == null) {
      return false;
    }
    if (directoryName.equals("")) {
      error += "Error:  Missing directory name \n" + INSTRUCTION;
      return false;
    }
    folderName = directoryName;


    //check for csv file command

    String csvFileName = getInputFileName("--csv-file", input, "csv");
    if (csvFileName == null) {
      return false;
    }
    if (csvFileName.contains(".")) {
      fileName = csvFileName;
      if (!setFlightInfo(fileName)) {
        return false;
      }
      String[] fileName = csvFileName.split("\\.");
      String prefix = fileName[0].trim();
      String suffix = fileName[1].trim();
      if (prefix.length() == 0 || suffix.length() == 0 || !suffix.equals("csv")) {
        error += "Error:  Wrong format of csv file \n" + INSTRUCTION;
        return false;
      }
    } else {
      error += "Error:  Wrong format of csv file \n" + INSTRUCTION;
      return false;
    }


    //check for event command --email-template email-template.txt --output-dir emails --csv-file Flight363FromSeattleToBoston.csv --event arrival
    //--email-template email-templ.txt --output-dir emails --event arrival --csv-file Flight363FromSeattleToBoston.csv
    String eventName = getInputFileName("--event", input, "event");
    if (eventName == null) {
      return false;
    }
    if (!(eventName.equals("departure") || eventName.equals("arrival"))) {
      error += "Error:  wrong event name \n" + INSTRUCTION;
      return false;
    }
    event = eventName;

    return true;
  }

  /**
   * This is the method to pull the flight information: flight number, destination,departure city
   * And then set those information for InfoFactory
   *
   * @param file the file name of the csv file
   * @return ture if information was set successfully
   */
  public static boolean setFlightInfo(String file) {
    if (!file.contains(".")) {
      error += "Error:  csv file format wrong";
      return false;
    }
    if (!file.contains("From") || !file.contains("To")) {
      error += "Error:  --csv-file argument does not contain departure-city or destination-city";
      return false;
    }
    String sufix = file.substring(0, file.indexOf("From"));
    if (sufix.length() == 0 || !sufix.contains("Flight")) {
      error += "Error:  --csv-file argument does not have correct Flight info" + INSTRUCTION;
      return false;
    } else {
      flightNumber = sufix.substring(sufix.indexOf("Flight") + 6, file.indexOf("From"));
      if (flightNumber.length() == 0) {
        error += "Error:  --csv-file argument does not have flight number" + INSTRUCTION;
        return false;
      }
    }
    departureCity = file.substring(file.indexOf("From") + 4, file.indexOf("To"));
    if (departureCity.length() == 0) {
      error += "Error:  --csv-file argument does not contain departure city" + INSTRUCTION;
      return false;
    }
    destinationCity = file.substring(file.indexOf("To") + 2, file.indexOf("."));
    if (destinationCity.length() == 0) {
      error += "Error:  --csv-file argument does not contain destination city" + INSTRUCTION;
      return false;
    }
    return true;
  }

  public String getError() {
    return error;
  }

  //--email-template email-template.txt --output-dir emails --csv-file Flight363FromSeattleToBoston.csv --event arrival

}

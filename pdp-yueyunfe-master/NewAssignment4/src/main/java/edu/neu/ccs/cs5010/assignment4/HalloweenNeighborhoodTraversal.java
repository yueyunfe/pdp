package edu.neu.ccs.cs5010.assignment4;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class HalloweenNeighborhoodTraversal {

  private static final String NOTAVAILABLE = "N/A";
  private static String errorMessage = null;
  private static ArrayList<String> csvFileCollection = new ArrayList<>();
  private static ArrayList<String> idCollection = new ArrayList<>();
  private static ArrayList<Child> childrenList = new ArrayList<>();
  private static int numberOfFiles;
  private static PrintWriter outPutStream;

  public static void main(String[] args) {
    System.out.println("Please give the command: number of files (space) file name (space).....");

    //check if the input command is valid and give appropriate message

    if (!processInput(args)) {
      System.out.println(errorMessage);
      return;
    }

    //read the file information after input is valid
    if (!readFile()) {
      System.out.println(errorMessage);
      return;
    }


    File file = creatFolder();
    if (file != null) {
      outputResult(file);
    }

  }


  /**
   * Create each child when reading each csv file
   * add the child object to the collection
   *
   * @return true if no error
   */
  public static boolean readFile() {
    for (int i = 0; i < numberOfFiles; i++) {
      Child child = new Child(csvFileCollection.get(i), idCollection.get(i));
      if (!child.readCSV()) {
        errorMessage = "Faile to read the information for child with id " + idCollection.get(i);
        return false;
      }
      childrenList.add(child);
    }
    return true;
  }

  //create the folder for output
  public static File creatFolder() {
    File file = new File("Candy");
    if (!file.exists() && !file.mkdir()) {
      return null;
    }
    return file;
  }

  /**
   * Output the result into a csv file
   *
   * @param file destination file
   */
  public static void outputResult(File file) {
    //loop through the child list
    for (int i = 0; i < childrenList.size(); i++) {
      Child child = childrenList.get(i);
      CandyVisitor candyVisitor = new CandyVisitor();
      errorMessage = null;

      File childFile = new File(file.getPath() + "/DreamTravesal" + child.getChildId() + ".csv");
      try {
        outPutStream = new PrintWriter(new OutputStreamWriter(new FileOutputStream(childFile), Charset.defaultCharset()));
        //give the title
        outPutStream.println("Candy type,Candy size,Household type");
      } catch (FileNotFoundException e) {
        errorMessage = "Fail to create file";
        continue;
      }
      //loop through the candy list for each child
      for (int j = 0; j < child.getCandies().size(); j++) {
        //not have the object when reading the file
        if (child.getCandies().get(j) == null) {
          errorMessage = "The desired candy is not available";
          if (!childFile.delete()) {
            throw new IllegalArgumentException("The original file was not able to be deleted");
          }
          break;
        }
        String result = child.getCandies().get(j).accept(candyVisitor);
        if (result == null) {
          errorMessage = "The candy size is not an option";
          if (!childFile.delete()) {
            throw new IllegalArgumentException("The original file was not able to be deleted");
          }
          break;
        } else if (result.equals(NOTAVAILABLE)) {
          errorMessage = "The size and type combination is not available";
          childFile.deleteOnExit();
          break;
        }
        outPutStream.println(result);
      }
      outPutStream.close();
      if (errorMessage != null) {
        System.out.println("Error: Child " + child.getChildId() + ", " + errorMessage);
      }
    }
  }

  /**
   * This method is used to check if the user input is correct
   * Also store the file name into the list
   *
   * @param args user input command
   * @return true for valid command
   */
  public static boolean processInput(String[] args) {

    if (args.length == 0) {
      return false;
    }

    String input = "";
    for (int i = 0; i < args.length; i++) {
      input += args[i] + " ";
    }
    input = input.trim();
    //split the command to get info
    String[] parts = input.split("\\s+");

    if (parts.length <= 1) {
      errorMessage = "At least two parameter command needed";
      return false;
    }
    try {
      //get the number of files
      numberOfFiles = Integer.parseInt(parts[0].trim());
      if (numberOfFiles <= 0) {
        errorMessage = "Number of files can not be less than 1";
        return false;
      }
    } catch (NumberFormatException e) {
      errorMessage = "Invalid format for number of files";
      return false;
    }
    //get the name of the files
    int countFileNumber = 0;
    for (int i = 1; i < parts.length; i++) {
      String file = parts[i].trim();
      if (!file.contains(".")) {
        errorMessage = "No dot for the file name";
        return false;
      }
      String[] fileName = file.split("\\.");
      String prefix = fileName[0].trim();
      String suffix = fileName[1].trim();
      if (prefix.length() == 0 || suffix.length() == 0 || !suffix.equals("csv")) {
        errorMessage = "Invalid format for the csv file name";
        return false;
      }
      //check if the file has the child number
      if (!prefix.contains("DreamCandy")) {
        errorMessage = "Invalid name for the csv file";
        return false;
      }
      String number = prefix.substring(prefix.indexOf("DreamCandy") + 10);
      idCollection.add(number);
      csvFileCollection.add(file);
      countFileNumber++;
    }
    if (numberOfFiles != countFileNumber) {
      errorMessage = "Number of files does not match";
      return false;
    }
    return true;
  }


  //8 DreamCandy3.csv DreamCandy4.csv DreamCandy5.csv DreamCandy6.csv DreamCandy7.csv DreamCandy8.csv DreamCandy9.csv DreamCandy10.csv
}

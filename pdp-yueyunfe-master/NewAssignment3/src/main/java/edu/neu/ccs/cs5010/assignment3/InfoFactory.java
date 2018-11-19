package edu.neu.ccs.cs5010.assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoFactory {
  private String infoFileName;
  private String templateFileName;
  private List<List> infoList;
  private String email = "";
  private String result = null;
  private String departureCity;
  private String destinationCity;
  private String flightNumber;
  private int emailIndex, firstnameIndex, lastnameIndex, rewardsIndex;
  private int addressIndex, cityIndex, countyIndex, stateIndex, zipIndex, phoneIndex;

  //"first_name","last_name","address","city","county","state","zip","phone","email","rewards"
  private final String EMAIL = "email";
  private final String DATE = "Date";
  private final String FIRSTNAME = "first_name";
  private final String LASTNAME = "last_name";
  private final String EVENT = "event";
  private final String DEPARTURECITY = "departure-city";
  private final String DESTINATIONCITY = "destination-city";
  private final String REWARDS = "rewards";
  private final String ADDRESS = "address";
  private final String CITY = "city";
  private final String COUNTY = "county";
  private final String STATE = "state";
  private final String ZIP = "zip";
  private final String PHONE = "phone";
  private final String FLIGHTNUMBER = "flight-number";


  /**
   * constructor
   *
   * @param infoFileName     given csv file name
   * @param templateFileName given template name
   */
  public InfoFactory(String infoFileName, String templateFileName) {
    this.infoFileName = infoFileName;
    this.templateFileName = templateFileName;
    this.infoList = new ArrayList<List>();
  }


  /**
   * This is the method to read and pull the information from the csv file
   * store the information like the following
   * [[customer1],[customer2],[customer3].........]
   *
   * @return true if read successfully
   */
  public boolean readInfoFile() {
    File file = createFile(infoFileName);
    if (file != null) {
      try {
        Scanner inputStream = new Scanner(file,"UTF-8");
        while (inputStream.hasNextLine()) {
          String line = inputStream.nextLine().toString();
          String textSearch = "\"(.*?)\"";
          Pattern pattern = Pattern.compile(textSearch);
          Matcher matcher = pattern.matcher(line);
          List<String> singleInfo = new ArrayList<String>();
          while (matcher.find()) {
            String info = matcher.group();
            singleInfo.add(info.substring(1, info.length() - 1));
          }
          //reach the end
          if (singleInfo.isEmpty()) {
            break;
          }
          infoList.add(singleInfo);
        }
        inputStream.close();
        //check for the title bar index was set
        if (!localizeInfoIndex()) {
          return false;
        }
        return true;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * This is the method to check and create a file if the input file name is correct
   *
   * @param name input file name
   * @return null if can not find the file in the resources, otherwise return the file obj
   */
  private File createFile(String name) {
    File file;
    try {
      URL fileUrl = getClass().getClassLoader().getResource(name);
      if (fileUrl == null) {
        return null;
      }
      file = new File(fileUrl.toURI());
    } catch (NullPointerException e) {
      e.printStackTrace();
      return null;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
    return file;
  }

  /**
   * This is the method to read and pull the email information from the template file
   *
   * @return true if read successfully
   */
  public boolean readTemplateFile() {

    File file = createFile(templateFileName);
    if (file != null) {
      try {
        Scanner inputStream = new Scanner(file,"UTF-8");
        while (inputStream.hasNextLine()) {
          email += inputStream.nextLine() + "\n";
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * This is the method to replace the placeHolder in the email
   *
   * @param list  the list contains the costumer information such as email,first_name,last_name,phone...
   * @param event arrival or departure
   * @return true if replace successfully
   */
  public boolean replaceTemplatePlaceHolder(List<String> list, String event) {
    result = email;
    if (result == "" || result == null) {
      return false;
    }

    Pattern pattern = Pattern.compile("(\\[\\[.*?]])+");
    Matcher matcher = pattern.matcher(email);

    while (matcher.find()) {
      String info = matcher.group();
      String keyWord = info.substring(2, info.length() - 2);
      if (result == null) {
        return false;
      }
      switch (keyWord) {
        case DATE:
          result = patternSearchAndReplace(DATE, result, list, event);
          break;
        case CITY:
          result = patternSearchAndReplace(CITY, result, list, event);
          break;
        case COUNTY:
          result = patternSearchAndReplace(COUNTY, result, list, event);
          break;
        case FIRSTNAME:
          result = patternSearchAndReplace(FIRSTNAME, result, list, event);
          break;
        case LASTNAME:
          result = patternSearchAndReplace(LASTNAME, result, list, event);
          break;
        case EVENT:
          result = patternSearchAndReplace(EVENT, result, list, event);
          break;
        case DEPARTURECITY:
          result = patternSearchAndReplace(DEPARTURECITY, result, list, event);
          break;
        case DESTINATIONCITY:
          result = patternSearchAndReplace(DESTINATIONCITY, result, list, event);
          break;
        case REWARDS:
          result = patternSearchAndReplace(REWARDS, result, list, event);
          break;
        case ADDRESS:
          result = patternSearchAndReplace(ADDRESS, result, list, event);
          break;
        case EMAIL:
          result = patternSearchAndReplace(EMAIL, result, list, event);
          break;
        case FLIGHTNUMBER:
          result = patternSearchAndReplace(FLIGHTNUMBER, result, list, event);
          break;
        default:
          throw new IllegalArgumentException("PlaceHolder Not Found");
      }
    }
    return true;

  }

  /**
   * The method is used to find the index of the title bar.
   * For example, it will fine the index of email at index 5, and set the index of "email" to be 5
   *
   * @return true if successfully, false if unexpected title or NA
   */
  private boolean localizeInfoIndex() {
    if (infoList.size() != 0) {
      List titleBar = infoList.get(0);
      for (int i = 0; i < titleBar.size(); i++) {
        String title = titleBar.get(i).toString();
        switch (title) {
          case FIRSTNAME:
            firstnameIndex = i;
            break;
          case LASTNAME:
            lastnameIndex = i;
            break;
          case REWARDS:
            rewardsIndex = i;
            break;
          case EMAIL:
            emailIndex = i;
            break;
          case CITY:
            cityIndex = i;
            break;
          case COUNTY:
            countyIndex = i;
            break;
          case STATE:
            stateIndex = i;
            break;
          case ADDRESS:
            addressIndex = i;
            break;
          case ZIP:
            zipIndex = i;
            break;
          case PHONE:
            phoneIndex = i;
            break;
          default:
            System.out.println("Can not read title bar information");
            return false;
        }
      }
      return true;
    }
    return false;
  }

  //"first_name","last_name","address","city","county","state","zip","phone","email","rewards"


  /**
   * his is the method to replace the information at specific spot
   *
   * @param key   keyword between [[ and ]]
   * @param text  email template
   * @param list  the list contains the costumer information such as email,first_name,last_name,phone...
   * @param event arrival or departure
   * @return the updated email after replacing, or null if not successfully
   */
  public String patternSearchAndReplace(String key, String text, List<String> list, String event) {
    Pattern searching = Pattern.compile("(\\[\\[" + key + "]])+");
    Matcher matcher = searching.matcher(text);
    switch (key) {
      case DATE:
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = dateFormat.format(today);
        return matcher.replaceAll(reportDate);
      case FIRSTNAME:
        return matcher.replaceAll(list.get(firstnameIndex));
      case LASTNAME:
        return matcher.replaceAll(list.get(lastnameIndex));
      case ADDRESS:
        return matcher.replaceAll(list.get(addressIndex));
      case CITY:
        return matcher.replaceAll(list.get(cityIndex));
      case COUNTY:
        return matcher.replaceAll(list.get(countyIndex));
      case STATE:
        return matcher.replaceAll(list.get(stateIndex));
      case ZIP:
        return matcher.replaceAll(list.get(zipIndex));
      case PHONE:
        return matcher.replaceAll(list.get(phoneIndex));
      case EVENT:
        return matcher.replaceAll(event);
      case DEPARTURECITY:
        return matcher.replaceAll(departureCity);
      case DESTINATIONCITY:
        return matcher.replaceAll(destinationCity);
      case REWARDS:
        return matcher.replaceAll(list.get(rewardsIndex));
      case EMAIL:
        return matcher.replaceAll(list.get(emailIndex));
      case FLIGHTNUMBER:
        return matcher.replaceAll(flightNumber);
      default:
        return null;
    }
  }

  public List<List> getInfoList() {
    return infoList;
  }

  public String getResult() {
    return result;
  }

  public void setDepartureCity(String departureCity) {
    this.departureCity = departureCity;
  }

  public void setDestinationCity(String destinationCity) {
    this.destinationCity = destinationCity;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }


  public void setTestEmail(String testEmail) {
    email = testEmail;
  }

  public int getFirstnameIndex() {
    return firstnameIndex;
  }

  public int getRewardsIndex() {
    return rewardsIndex;
  }

  public int getAddressIndex() {
    return addressIndex;
  }
}

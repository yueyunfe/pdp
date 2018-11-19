package edu.neu.ccs.cs5010.assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Child {

  private final String TWIX = "twix", SNICKERS = "snickers", MARS = "mars", KITKAT = "kit kat", WHOOPERS = "whoopers";
  private final String MILKYWAY = "milky way", TOBLERONE = "toblerone", CRUNCH = "crunch", BABYRUTH = "baby ruth", ALMONDJOY = "almond joy";
  private String childId;
  private String fileName;
  private ArrayList<String> candyList = new ArrayList<>();
  private ArrayList<String> sizeList = new ArrayList<>();
  private ArrayList<Candy> candies = new ArrayList<>();


  public Child(String fileName, String childId) {
    this.childId = childId;
    this.fileName = fileName;
  }


  public ArrayList<Candy> getCandies() {
    return candies;
  }

  /**
   * This is the method to read and pull the information from the csv file
   * and store the information
   *
   * @return true if read successfully
   */
  public boolean readCSV() {
    File file = createFile(fileName);
    if (file != null) {
      try {
        Scanner inputStream = new Scanner(file, "UTF-8");
        while (inputStream.hasNextLine()) {
          String line = inputStream.nextLine().trim().toLowerCase();
          String[] parts = line.split(",");
          if (parts.length >= 1) {
            for (int i = 0; i < parts.length; i++) {
              if (parts[i].contains("size")) {
                String candySize = parts[i].substring(0, parts[i].indexOf("size")).trim();
                sizeList.add(candySize);
                candyList.add(parts[i].substring(parts[i].indexOf("size") + 4).trim());
              } else {
                sizeList.add("regular");
                candyList.add(parts[i].trim());
              }
            }
          }

        }
        inputStream.close();
        createCandyObject();
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
   * Create the candy object based on the information get from
   * the csv file before
   */
  public void createCandyObject() {
    if (candyList.size() == sizeList.size() && !candyList.isEmpty()) {
      for (int i = 0; i < candyList.size(); i++) {
        switch (candyList.get(i)) {
          case TWIX:
            Twix twix = new Twix(sizeList.get(i));
            candies.add(twix);
            break;
          case SNICKERS:
            Snickers snickers = new Snickers(sizeList.get(i));
            candies.add(snickers);
            break;
          case MARS:
            Mars mars = new Mars(sizeList.get(i));
            candies.add(mars);
            break;
          case KITKAT:
            KitKat kitKat = new KitKat(sizeList.get(i));
            candies.add(kitKat);
            break;
          case WHOOPERS:
            Whoopers whoopers = new Whoopers(sizeList.get(i));
            candies.add(whoopers);
            break;
          case MILKYWAY:
            MilkyWay milkyWay = new MilkyWay(sizeList.get(i));
            candies.add(milkyWay);
            break;
          case TOBLERONE:
            Toblerone toblerone = new Toblerone(sizeList.get(i));
            candies.add(toblerone);
            break;
          case CRUNCH:
            Crunch crunch = new Crunch(sizeList.get(i));
            candies.add(crunch);
            break;
          case BABYRUTH:
            BabyRuth babyRuth = new BabyRuth(sizeList.get(i));
            candies.add(babyRuth);
            break;
          case ALMONDJOY:
            AlmondJoy almondJoy = new AlmondJoy(sizeList.get(i));
            candies.add(almondJoy);
            break;
          default:
            candies.add(null);
        }
      }
    }
  }

  public ArrayList<String> getCandyList() {
    return candyList;
  }

  public ArrayList<String> getSizeList() {
    return sizeList;
  }

  public String getChildId() {
    return childId;
  }
}

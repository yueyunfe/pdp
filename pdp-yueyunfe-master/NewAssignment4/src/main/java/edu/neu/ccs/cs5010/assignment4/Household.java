package edu.neu.ccs.cs5010.assignment4;

import java.util.ArrayList;

public class Household {
  protected final String TWIX = "twix", SNICKERS = "snickers", MARS = "mars", KITKAT = "kit kat", WHOOPERS = "whoopers";
  protected final String MILKYWAY = "milky way", TOBLERONE = "toblerone", CRUNCH = "crunch", BABYRUTH = "baby ruth", ALMONDJOY = "almond joy";

  protected ArrayList<String> superCandy = new ArrayList<>();
  protected ArrayList<String> kingCandy = new ArrayList<>();
  protected ArrayList<String> funCandy = new ArrayList<>();
  protected ArrayList<String> regularCandy = new ArrayList<>();

  protected String name;

  public ArrayList<String> getSuperCandy() {
    return superCandy;
  }

  public ArrayList<String> getKingCandy() {
    return kingCandy;
  }

  public ArrayList<String> getFunCandy() {
    return funCandy;
  }

  public ArrayList<String> getRegularCandy() {
    return regularCandy;
  }

  public String getName() {
    return name;
  }

  public Household() {

  }
}

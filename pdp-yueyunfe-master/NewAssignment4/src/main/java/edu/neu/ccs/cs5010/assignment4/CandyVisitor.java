package edu.neu.ccs.cs5010.assignment4;

import java.util.ArrayList;

public class CandyVisitor implements Visitor {

  private final String SUPER_SIZE = "super";
  private final String KING_SIZE = "king";
  private final String REGULAR_SIZE = "regular";
  private final String FUN_SIZE = "fun";
  private final String NOTAVAILABLE = "N/A";

  private Duplex duplex;
  private DetachedHouse detachedHouse;
  private Mansion mansion;
  private Townhome townhome;

  /**
   * Constructor to set household information ready
   */
  public CandyVisitor() {
    duplex = new Duplex();
    detachedHouse = new DetachedHouse();
    mansion = new Mansion();
    townhome = new Townhome();
  }

  /**
   * For specific size, check if the household candy list has the desired candy
   *
   * @param candy the candy to search for
   * @return size, candy, household
   */
  private String checkAvailability(Candy candy) {
    switch (candy.size) {
      case SUPER_SIZE:
        if (searchCandyName(duplex.getSuperCandy(), candy)) {
          return "Super Size," + candy.getName() + "," + duplex.getName();
        } else if (searchCandyName(detachedHouse.getSuperCandy(), candy)) {
          return "Super Size," + candy.getName() + "," + detachedHouse.getName();
        } else if (searchCandyName(mansion.getSuperCandy(), candy)) {
          return "Super Size," + candy.getName() + "," + mansion.getName();
        } else {
          return NOTAVAILABLE;
        }
      case KING_SIZE:
        if (searchCandyName(duplex.getKingCandy(), candy)) {
          return "King Size," + candy.getName() + "," + duplex.getName();
        } else if (searchCandyName(detachedHouse.getKingCandy(), candy)) {
          return "King Size," + candy.getName() + "," + detachedHouse.getName();
        } else if (searchCandyName(mansion.getKingCandy(), candy)) {
          return "King Size," + candy.getName() + "," + mansion.getName();
        } else {
          return NOTAVAILABLE;
        }
      case FUN_SIZE:
        if (searchCandyName(duplex.getFunCandy(), candy)) {
          return "Fun Size," + candy.getName() + "," + duplex.getName();
        } else if (searchCandyName(detachedHouse.getFunCandy(), candy)) {
          return "Fun Size," + candy.getName() + "," + detachedHouse.getName();
        } else if (searchCandyName(mansion.getFunCandy(), candy)) {
          return "Fun Size," + candy.getName() + "," + mansion.getName();
        } else {
          return NOTAVAILABLE;
        }
      case REGULAR_SIZE:
        if (searchCandyName(townhome.getRegularCandy(), candy)) {
          return "Regular Size," + candy.getName() + "," + townhome.getName();
        } else {
          return NOTAVAILABLE;
        }
      default:
        return null;
    }
  }

  /**
   * Method to Search for the candy in the list
   *
   * @param list  the container
   * @param candy the candy want
   * @return true if found
   */
  public boolean searchCandyName(ArrayList list, Candy candy) {
    if (list.contains(candy.getName().toLowerCase())) {
      return true;
    }
    return false;
  }

  @Override
  public String visit(Twix twix) {
    return checkAvailability(twix);
  }

  @Override
  public String visit(Snickers snickers) {

    return checkAvailability(snickers);
  }

  @Override
  public String visit(Mars mars) {
    return checkAvailability(mars);
  }

  @Override
  public String visit(KitKat kitKat) {
    return checkAvailability(kitKat);
  }

  @Override
  public String visit(Whoopers whoopers) {
    return checkAvailability(whoopers);
  }

  @Override
  public String visit(MilkyWay milkyWay) {
    return checkAvailability(milkyWay);
  }

  @Override
  public String visit(Toblerone toblerone) {
    return checkAvailability(toblerone);
  }

  @Override
  public String visit(Crunch crunch) {
    return checkAvailability(crunch);
  }

  @Override
  public String visit(BabyRuth babyRuth) {
    return checkAvailability(babyRuth);
  }

  @Override
  public String visit(AlmondJoy almondJoy) {
    return checkAvailability(almondJoy);
  }
}

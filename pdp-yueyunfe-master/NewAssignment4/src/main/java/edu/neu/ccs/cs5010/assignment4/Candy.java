package edu.neu.ccs.cs5010.assignment4;

public class Candy implements Visitable {

  protected final String TWIX = "twix", SNICKERS = "snickers", MARS = "mars", KITKAT = "kit kat", WHOOPERS = "whoopers";
  protected final String MILKYWAY = "milky way", TOBLERONE = "toblerone", CRUNCH = "crunch", BABYRUTH = "baby ruth", ALMONDJOY = "almond joy";

  protected String size;
  protected String name;


  public Candy(String size) {
    this.size = size;
  }

  public String getSize() {
    return size;
  }

  public String getName() {
    return name;
  }

  /**
   * Need to be override or the candy self returns null
   *
   * @param visitor
   * @return
   */
  @Override
  public String accept(Visitor visitor) {
    return null;
  }
}

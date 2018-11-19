package edu.neu.ccs.cs5010.assignment4;

public class Twix extends Candy {

  public Twix(String size) {
    super(size);
    name = TWIX;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

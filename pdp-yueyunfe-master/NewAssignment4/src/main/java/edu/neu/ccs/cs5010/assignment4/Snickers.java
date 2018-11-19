package edu.neu.ccs.cs5010.assignment4;

public class Snickers extends Candy {
  public Snickers(String size) {
    super(size);
    name = SNICKERS;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

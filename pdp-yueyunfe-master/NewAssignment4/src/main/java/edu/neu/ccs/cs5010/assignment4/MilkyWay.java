package edu.neu.ccs.cs5010.assignment4;

public class MilkyWay extends Candy {
  public MilkyWay(String size) {
    super(size);
    name = MILKYWAY;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

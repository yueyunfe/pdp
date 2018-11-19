package edu.neu.ccs.cs5010.assignment4;

public class Crunch extends Candy {
  public Crunch(String size) {
    super(size);
    name = CRUNCH;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

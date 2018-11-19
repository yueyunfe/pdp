package edu.neu.ccs.cs5010.assignment4;

public class Toblerone extends Candy {
  public Toblerone(String size) {
    super(size);
    name = TOBLERONE;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

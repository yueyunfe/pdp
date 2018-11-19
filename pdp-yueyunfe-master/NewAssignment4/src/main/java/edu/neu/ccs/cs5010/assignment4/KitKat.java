package edu.neu.ccs.cs5010.assignment4;

public class KitKat extends Candy {
  public KitKat(String size) {
    super(size);
    name = KITKAT;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

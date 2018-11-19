package edu.neu.ccs.cs5010.assignment4;

public class Whoopers extends Candy {
  public Whoopers(String size) {
    super(size);
    name = WHOOPERS;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

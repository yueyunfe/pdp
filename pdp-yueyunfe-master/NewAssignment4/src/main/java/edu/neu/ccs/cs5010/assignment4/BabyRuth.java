package edu.neu.ccs.cs5010.assignment4;

public class BabyRuth extends Candy {

  public BabyRuth(String size) {
    super(size);
    name = BABYRUTH;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

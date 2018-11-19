package edu.neu.ccs.cs5010.assignment4;

public class Mars extends Candy {
  public Mars(String size) {
    super(size);
    name = MARS;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);

  }
}

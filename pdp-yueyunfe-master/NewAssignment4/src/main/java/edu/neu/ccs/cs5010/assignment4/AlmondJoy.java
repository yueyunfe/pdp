package edu.neu.ccs.cs5010.assignment4;

public class AlmondJoy extends Candy {
  public AlmondJoy(String size) {
    super(size);
    name = ALMONDJOY;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);

  }
}

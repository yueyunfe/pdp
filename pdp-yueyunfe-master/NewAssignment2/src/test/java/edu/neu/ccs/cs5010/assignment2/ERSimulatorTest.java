package edu.neu.ccs.cs5010.assignment2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ERSimulatorTest {

  private ERSimulator erSimulator;

  @Test
  public void main() throws Exception {
    erSimulator.main(new String[]{"10", "2"});
  }

}
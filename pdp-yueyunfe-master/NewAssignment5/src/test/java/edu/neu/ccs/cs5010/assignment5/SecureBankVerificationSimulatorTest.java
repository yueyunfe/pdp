package edu.neu.ccs.cs5010.assignment5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SecureBankVerificationSimulatorTest {

  private SecureBankVerificationSimulator simulator;
  String[] args = new String[]{"100", "50", "0.5", "bank"};

  @Before
  public void setUp() throws Exception {
    simulator = new SecureBankVerificationSimulator();
  }

  @Test
  public void inputTest() throws Exception {
    assertTrue(simulator.processInput(args));

  }

  @Test
  public void integerTest() throws Exception {
    assertFalse(simulator.checkClientNumber("0"));
    assertTrue(simulator.checkClientNumber("5"));
    assertFalse(simulator.checkVerificationNumber("-1"));
  }

  @Test
  public void mainTest() throws Exception {
    simulator.main(args);
  }

}
package edu.neu.ccs.cs5010.assignment5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
  private Bank bank;

  @Before
  public void setUp() throws Exception {
    bank = new Bank(50, 40, 0.5, "reject");
  }

  @Test
  public void registerTest() throws Exception {
    bank.simulateTransaction();
    assertEquals(40, bank.getClients().size());
  }

  @Test
  public void encryptionTest() throws Exception {
    bank = new Bank(10, 10, 0, "reject");
    bank.simulateTransaction();
    bank.doEncryption();
    assertTrue(bank.getClients().get(0).isEncryption());
  }

  @Test
  public void decryptionTest() throws Exception {
    bank = new Bank(20, 20, 0.5, "reject");
    bank.simulateTransaction();
    bank.doEncryption();

    assertTrue(bank.doDecryption());
  }
}
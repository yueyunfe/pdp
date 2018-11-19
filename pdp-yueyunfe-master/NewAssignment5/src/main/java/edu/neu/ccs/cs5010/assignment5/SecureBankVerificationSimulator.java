package edu.neu.ccs.cs5010.assignment5;

public class SecureBankVerificationSimulator {

  private static final int MAX_CLIENT_NUMBER = 50000;
  private static final int MAX_VERIFICATION_NUMBER = 10000;
  private static int clientsNumber;
  private static int verificationNumber;
  private static double fractionOfInvalid;
  private static String outputFileName;

  public static void main(String[] args) {
    //enter and check for the user input
    if (!processInput(args)) {
      return;
    }

    Bank bank = new Bank(clientsNumber, verificationNumber, fractionOfInvalid, outputFileName);
    bank.simulateTransaction();
    bank.doEncryption();
    bank.doDecryption();

  }

  /**
   * Continually asking user for the input parameter and also check for the if the
   * input is valid
   *
   * @param args
   * @return true is valid
   */
  public static boolean processInput(String[] args) {
    System.out.println("Please give the number of clients in the bank");
    System.out.println("Please give the number of verification");
    System.out.println("Please give the fraction of invalid");
    System.out.println("Please give the file name for output");

    if (args.length != 4) {
      throw new IllegalArgumentException("4 arguments are needed");
    }
    if (!checkClientNumber(args[0])) {
      throw new IllegalArgumentException("Illegal number of clients");
    }

    clientsNumber = Integer.parseInt(args[0]);


    if (!checkVerificationNumber(args[1])) {
      throw new IllegalArgumentException("Illegal number of verification");
    }
    verificationNumber = Integer.parseInt(args[1]);


    if (!checkDouble(args[2])) {
      throw new IllegalArgumentException("Illegal number of verification");
    }
    fractionOfInvalid = Double.parseDouble(args[2]);


    if (args[3].equals("") || args[3] == null) {
      throw new IllegalArgumentException("Illegal name for output");
    }
    outputFileName = args[3];

    return true;
  }

  /**
   * Check if a string can be convert to an integer
   *
   * @param input string to convert
   * @return
   */
  public static boolean checkClientNumber(String input) {
    try {
      int number = Integer.parseInt(input);
      if (number <= 0 || number > MAX_CLIENT_NUMBER) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Check if a string can be convert to an integer
   *
   * @param input string to convert
   * @return
   */
  public static boolean checkVerificationNumber(String input) {
    try {
      int number = Integer.parseInt(input);
      if (number <= 0 || number > MAX_VERIFICATION_NUMBER) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Check if a string can be convert to an integer
   *
   * @param input string to convert
   * @return
   */
  public static boolean checkDouble(String input) {
    try {
      double number = Double.parseDouble(input);
      if (number <= 0) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

}

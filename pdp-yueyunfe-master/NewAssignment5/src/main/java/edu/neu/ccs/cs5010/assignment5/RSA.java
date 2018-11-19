package edu.neu.ccs.cs5010.assignment5;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {

  private BigInteger keySecond; //n, which is equals p*q, where p and q are two random prime number
  private BigInteger privateKey;
  private BigInteger publicKey;
  private BigInteger euler;
  private boolean withdraw = false;
  private int money;
  private static SecureRandom secureRandom = new SecureRandom();
  private final int MAXWITHDRAW = 3000;
  private final int MAXDEPOSIT = 2000;
  private BigInteger p1BigInteger, p2BigInteger, q1BigInteger, q2BigInteger;
  private int bitSize = 256;


  /**
   * Encrypt the message
   *
   * @param message
   * @param keyPrivate
   * @param secondKey
   * @return
   */
  public BigInteger encrypt(int message, BigInteger keyPrivate, BigInteger secondKey) {
    int lastDigit = message % 10;
    String number = String.valueOf(message);
    String restNumber = number.substring(0, number.length() - 1);
    money = Integer.parseInt(restNumber);
    if (lastDigit >= 5) {
      //withdraw
      withdraw = true;
      //over the limit for withdraw
      if (money <= 0 || money > MAXWITHDRAW) {
        return BigInteger.ZERO;
      }
    } else {
      //over the limit for deposit
      withdraw = false;
      if (money <= 0 || money > MAXDEPOSIT) {
        return BigInteger.ZERO;
      }

    }

    return BigInteger.valueOf(message).modPow(keyPrivate, secondKey);
  }

  //Claculate the decryption number
  public BigInteger decrypt(BigInteger encryptionNumber, BigInteger keyPublic, BigInteger secondKey) {

    return encryptionNumber.modPow(keyPublic, secondKey);
  }

  public boolean isWithdraw() {
    return withdraw;
  }

  public int getMoney() {
    return money;
  }

  public BigInteger getKeySecond() {
    return keySecond;
  }

  public BigInteger getprivateKey() {
    return privateKey;
  }

  public BigInteger getpublicKey() {
    return publicKey;
  }

  /**
   * Calculate the private and public key
   */
  public void calculateKey() {

    //generate 2 different prime
    p1BigInteger = generatePrime();
    p2BigInteger = generatePrime();


    while (p1BigInteger.equals(p2BigInteger)) {
      p2BigInteger = generatePrime();
    }


    keySecond = p1BigInteger.multiply(p2BigInteger); //n
    euler = p1BigInteger.subtract(BigInteger.ONE).multiply(p2BigInteger.subtract(BigInteger.ONE));

    //find private key
    privateKey = BigInteger.probablePrime(bitSize - 1, secureRandom); //e
    while (!privateKey.isProbablePrime(1)) {
      privateKey = BigInteger.probablePrime(bitSize - 1, secureRandom);
    }

    BigInteger gcd = gcd(privateKey, euler);

    q1BigInteger = q1BigInteger.multiply(BigInteger.ONE.divide(gcd));
    publicKey = q1BigInteger.mod(euler.abs());
    if (q1BigInteger.mod(euler.abs()).compareTo(BigInteger.ZERO) < 0) {
      publicKey = publicKey.add(euler.abs());
    }

  }

  /**
   * Generate the prime number
   *
   * @return prime number
   */
  private BigInteger generatePrime() {
    BigInteger bigInteger;
    bigInteger = BigInteger.probablePrime(bitSize, secureRandom);
    while ((!bigInteger.isProbablePrime(1))) {
      bigInteger = BigInteger.probablePrime(bitSize, secureRandom);

    }
    return bigInteger;
  }


  /**
   * Calculate the gcd
   *
   * @param bigInteger1
   * @param bigInteger2
   * @return gcd
   */
  private BigInteger gcd(BigInteger bigInteger1, BigInteger bigInteger2) {
    if (bigInteger2.intValue() == 0) {
      q1BigInteger = BigInteger.ONE;
      q2BigInteger = BigInteger.ZERO;
      return bigInteger1;
    }
    BigInteger ans = gcd(bigInteger2, bigInteger1.mod(bigInteger2));
    BigInteger temp = q1BigInteger;
    q1BigInteger = q2BigInteger;
    q2BigInteger = temp.subtract(bigInteger1.divide(bigInteger2).multiply(q2BigInteger));
    return ans;
  }

}

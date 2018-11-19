package edu.neu.ccs.cs5010.assignment5;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Client {

  private int clientId;
  private int deposit = 0;
  private int withdraw = 0;

  private BigInteger keySecond; //n, which is equals p*q, where p and q are two random prime number
  private BigInteger privateKey = BigInteger.ZERO;
  private BigInteger publicKey;
  private int message;
  private BigInteger digitalSignature;
  private boolean isEncrypt = false;
  private String date;
  private String time;


  /**
   * Constructor, and calculate the private and public key when the client was created
   *
   * @param clientId      client id
   * @param message message to encrypt
   * @param rsa     rsa class object
   */
  public Client(int clientId, int message, RSA rsa) {
    this.clientId = clientId;
    this.message = message;
    if (privateKey.equals(BigInteger.ZERO)) {
      rsa.calculateKey();
      publicKey = rsa.getpublicKey();
      privateKey = rsa.getprivateKey();
      keySecond = rsa.getKeySecond();
    }
  }

  /**
   * Work as the transaction begins, encrypt the message and record the date and time
   *
   * @param rsa rsa object
   * @return true if success
   */
  public boolean sendRequest(RSA rsa) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String format = dtf.format(now);
    String[] dateAndTime = format.split("\\s+");
    date = dateAndTime[0].trim();
    time = dateAndTime[1].trim();
    Random random = new Random();

    if (rsa == null) {
      return false;
    }
    digitalSignature = rsa.encrypt(message, privateKey, keySecond);
    if (rsa.isWithdraw()) {
      deposit = 0;
      withdraw = rsa.getMoney();
    } else {
      withdraw = 0;
      deposit = rsa.getMoney();
    }
    if (digitalSignature.equals(BigInteger.ZERO)) {
      digitalSignature = BigInteger.valueOf(random.nextInt());
      return false;
    }
    return true;
  }

  public BigInteger getDigitalSignature() {
    return digitalSignature;
  }

  public BigInteger getPublicKey() {
    return publicKey;
  }

  public void setEncrypt(boolean encrypt) {
    isEncrypt = encrypt;
  }

  public BigInteger getKeySecond() {
    return keySecond;
  }

  public boolean isEncryption() {
    return isEncrypt;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }


  public int getClientId() {
    return clientId;
  }

  public int getDeposit() {
    return deposit;
  }

  public int getWithdraw() {
    return withdraw;
  }

  public int getMessage() {
    return message;
  }

}

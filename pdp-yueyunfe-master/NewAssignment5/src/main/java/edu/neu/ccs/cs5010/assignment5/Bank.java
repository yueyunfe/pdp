package edu.neu.ccs.cs5010.assignment5;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

public class Bank {
  private RSA rsa;
  private int capacity;
  private int clientsNumber;
  private ArrayList<Client> clients;
  private ArrayList<Client> allValidClients = new ArrayList<>();
  private ArrayList<Client> allInvalidClients = new ArrayList<>();
  private double fractionOfInvalid;
  private PrintWriter outPutStream;
  private String outputFileName;
  private final int MAXMESSAGE = 40000;
  private final int MAXWITHDRAW = 3000;
  private final int MAXDEPOSIT = 2000;
  private String rejectedDeposit = "Rejected Deposit ids: ";
  private String rejectedWithdraw = "Rejected Withdraw ids: ";

  public Bank(int capacity, int requestNumber, double fractionOfInvalid, String fileName) {
    rsa = new RSA();
    this.capacity = capacity;
    clients = new ArrayList<>();
    this.fractionOfInvalid = fractionOfInvalid;
    this.clientsNumber = requestNumber;
    this.outputFileName = fileName;
    registerClients();
  }

  /**
   * Pick number of fail and success cases based on the ratio from input
   */
  public void simulateTransaction() {
    int invalidCases = (int) Math.round(fractionOfInvalid * clientsNumber);
    Random random = new Random();
    for (int i = 0; i < invalidCases; i++) {
      int index = random.nextInt(capacity - 1);
      Client client;
      client = allInvalidClients.get(index);
      clients.add(client);
    }
    for (int i = 0; i < (clientsNumber - invalidCases); i++) {
      int index = random.nextInt(capacity - 1);
      Client client;
      client = allValidClients.get(index);
      clients.add(client);
    }
  }

  public ArrayList<Client> getClients() {
    return clients;
  }

  /**
   * Pre-register 2 lists of clients, one list contains all the valid message cases
   * and the other one contains all the fail cases
   */
  public void registerClients() {
    Random random = new Random();
    int clientId = 0;
    while (true) {
      int message = random.nextInt(MAXMESSAGE) + 10;
      String number = String.valueOf(message);
      String restNumber = String.valueOf(message).substring(0, number.length() - 1);
      //fill the valid client list
      int lastDigit = message % 10;
      int money = Integer.parseInt(restNumber);
      Client client = new Client(clientId, message, rsa);
      if (lastDigit >= 5) {       //withdraw
        if (money <= MAXWITHDRAW) {
          //valid
          if (allValidClients.size() < capacity) {
            allValidClients.add(client);
          }

        } else {
          if (allInvalidClients.size() < capacity) {
            allInvalidClients.add(client);
          }
        }
      } else { //deposit
        if (money <= MAXDEPOSIT) {
          //valid
          if (allValidClients.size() < capacity) {
            allValidClients.add(client);
          }
        } else {
          if (allInvalidClients.size() < capacity) {
            allInvalidClients.add(client);
          }
        }
      }
      if (allInvalidClients.size() == capacity && allValidClients.size() == capacity) {
        break;
      }
      clientId++;
    }
  }

  /**
   * Do the encryption for every client who sent the request
   */
  public void doEncryption() {
    for (int i = 0; i < clients.size(); i++) {
      Client client = clients.get(i);
      if (!client.sendRequest(rsa)) {
        continue;
      }
      client.setEncrypt(true);
    }

  }

  /**
   * Do the decryption for every client from the bank side
   * And compare the message with the decryption,output the result
   */
  public boolean doDecryption() {
    if (!creatFolderAndFile()) {
      throw new IllegalArgumentException("Fail to open create the file");
    }

    for (int i = 0; i < clients.size(); i++) {
      Client client = clients.get(i);
      //this client was failed to encrypt due to incorrect message
      if (!client.isEncryption()) {
        outputResult(false, false, client, i);
        continue;
      }
      BigInteger publickey = client.getPublicKey();
      BigInteger digitalSignature = client.getDigitalSignature();
      BigInteger parameterN = client.getKeySecond();
      BigInteger decriptionNumber = rsa.decrypt(digitalSignature, publickey, parameterN);
      if (decriptionNumber.equals(BigInteger.valueOf(client.getMessage()))) {
        outputResult(true, true, client, i);
      } else {
        outputResult(true, false, client, i);
      }
    }
    outPutStream.close();
    System.out.println(findSamePublicKey() + " transactions with same public key");
    printTransactionInfo();
    System.out.println(rejectedDeposit);
    System.out.println(rejectedWithdraw);
    return true;

  }

  /**
   * Create the folder and files, including the header
   *
   * @return true if success
   */
  public boolean creatFolderAndFile() {
    File file = new File("Transaction");
    if (!file.exists()&&!file.mkdir()) {
        return false;
    }
    File transactionFile = new File(file.getPath() + "/" + outputFileName + ".csv");
    try {
      outPutStream = new PrintWriter(new OutputStreamWriter(new FileOutputStream(transactionFile), Charset.defaultCharset()));
      //give the title
      outPutStream.println("Transaction number,Date,Time,Client ID,Message,Digital signature,Verified,Transactions status");
    } catch (FileNotFoundException e) {
      return false;
    }
    return true;
  }

  /**
   * Print the message in a specific format
   *
   * @param isEncrypt success for encryption or not
   * @param isMatch   success for matching up
   * @param client    client object
   * @param count     used to form the transaction number
   */
  public void outputResult(boolean isEncrypt, boolean isMatch, Client client, int count) {
    String verified = isEncrypt ? "yes" : "no";
    String status;

    //encryption success,check for decryption
    if (verified.equals("yes")) {
      //decryption matches message
      if (isMatch) {
        if (client.getWithdraw() != 0) {
          status = "withdrawal accepted";
        } else {
          status = "deposit accepted";
        }
      } else {
        //fail to mach the result
        if (client.getWithdraw() != 0) {
          status = "withdrawal rejected";
          rejectedWithdraw += client.getClientId() + " ";
        } else {
          status = "deposit rejected";
          rejectedDeposit += client.getClientId() + " ";
        }
      }
    } else {
      //encryption fail, does not need to check for decryption
      if (client.getWithdraw() != 0) {
        status = "withdrawal rejected";
        rejectedWithdraw += client.getClientId() + " ";
      } else {
        status = "deposit rejected";
        rejectedDeposit += client.getClientId() + " ";
      }
    }
    outPutStream.println("1001-" + count + "," + client.getDate() + "," + client.getTime() + "," + client.getClientId() + "," + client.getMessage()
            + "," + client.getDigitalSignature() + "," + verified + "," + status);
  }

  public int findSamePublicKey() {
    int count = 0;
    for (int i = 0; i < clients.size(); i++) {
      BigInteger key = clients.get(i).getPublicKey();
      for (int j = i + 1; j < clients.size(); j++) {
        if (clients.get(j).getClientId() == clients.get(i).getClientId()) {
          continue;
        }
        BigInteger otherKey = clients.get(j).getPublicKey();
        if (key.equals(otherKey)) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Print the required output in the console
   */
  public void printTransactionInfo() {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    ValueComparator bvc = new ValueComparator(map);
    TreeMap<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(bvc);

    for (int i = 0; i < clients.size(); i++) {
      Client client = clients.get(i);
      int money;
      if (client.getWithdraw() == 0) {
        money = client.getDeposit();
      } else {
        money = client.getWithdraw();
      }
      if (map.containsKey(client.getClientId())) {
        if (map.get(client.getClientId()) < money) {
          map.put(client.getClientId(), money);
        }
      } else {
        map.put(client.getClientId(), money);
      }
    }
    sortedMap.putAll(map);
    int size = sortedMap.size();
    for (int j = 1; j < size; j++) {
      if (j == 11) {
        break;
      }

      System.out.println(j + ". Client " + j + ": " + sortedMap.pollFirstEntry().getValue() + "\n");
    }
  }

  public class ValueComparator implements Comparator<Integer> {
    Map<Integer, Integer> base;

    public ValueComparator(Map<Integer, Integer> base) {
      this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(Integer number1, Integer number2) {
      if (base.get(number1) >= base.get(number2)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys
    }
  }
}

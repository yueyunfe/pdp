package edu.neu.ccs.cs5010.assignment2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;

public class Hospital {


  private final int EXAMLOWEST = 1;
  private final int AGEMAX = 140;               //maximum age
  private final int LEVELMAX = 10;               //maximum level of severe
  private final int EXAMMAX = 10;               //max minute for exam
  private final int HIGHESTTEMP = 110;
  private final double LOWESTTEMP = 94.0;
  private final double HIGHESTBLOODPRESSURE = 170.0;
  private final double LOWESTBLOODPRESSURE = 80.0;
  private final double MILLISECTOMIN = 1000 * 60;
  private final int HIGHRATINGLEVEL = 60;
  private final int LOWRATINGLEVEL = 30;


  private int room;
  private int patientId = 0;
  private int roomId = 0;


  private MyPriorityQueue<Room> waitingRoom;
  private MyPriorityQueue<Patient> patients;
  private MyPriorityQueue<Patient> examed;
  private MyPriorityQueue<Room> examRoom;


  private int totalpatient = 0;   //number of patient
  private double highemergencytimeave;
  private double lowemergencytimeave;
  private int countHighEmergency = 0;
  private int countLowEmergency = 0;
  private double highEmergencyTime = 0;
  private double lowEmergencyTime = 0;
  private double time = 0;
  private int option;
  private int assignNumber = 0;

  private ArrayList<Patient> waitingPatientList = new ArrayList();
  private ArrayList<Patient> departurePatientList = new ArrayList();
  private ArrayList<Room> inuseRoomList = new ArrayList();
  private ArrayList<Room> roomList = new ArrayList();

  /**
   * constructor
   *
   * @param room   number of rooms
   * @param option option 1 for random, 2 for preset
   */
  public Hospital(int room, int option) {
    this.room = room;
    waitingRoom = new MyPriorityQueue<Room>();
    patients = new MyPriorityQueue<Patient>();
    examRoom = new MyPriorityQueue<Room>();
    examed = new MyPriorityQueue<Patient>();
    this.option = option;

    for (int i = 0; i < room; i++) {
      Room aroom = new Room(0, roomId);
      roomId++;
      waitingRoom.add(aroom);
    }
  }

  public MyPriorityQueue<Room> getWaitingRoom() {
    return waitingRoom;
  }

  /**
   * This works as the nurse register patient and give the patient information
   */
  public void registerPatient() {

    Random random = new Random();
    int age = random.nextInt(AGEMAX);
    String name = generateName(random);
    int level = random.nextInt(LEVELMAX) + 1;
    long duration = random.nextInt(EXAMMAX) + EXAMLOWEST;
    double temp = random.nextDouble() * (HIGHESTTEMP - LOWESTTEMP) + LOWESTTEMP;
    double bloodpressure = random.nextDouble() * (
            HIGHESTBLOODPRESSURE - LOWESTBLOODPRESSURE) + LOWESTBLOODPRESSURE;


    Patient patient = new Patient(age, patientId, name, level);
    patient.setArrivaltime(LocalDateTime.now());
    patient.setBloodpressure(bloodpressure);
    patient.setTemperature(temp);
    patient.setDuration(duration * 60 * 1000);   // in millisecond
    patients.add(patient);
    patientId++;
    totalpatient++;
  }


  /**
   * used to generate dummy name
   */

  public String generateName(Random random) {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    int count = 2;
    String name = "";
    while (count >= 0) {
      int index = random.nextInt(26);
      name += alphabet.charAt(index);
      count--;
    }
    return name;

  }

  /**
   * Preset mode setting
   *
   * @param roomNumber number of rooms provided
   */
  public void presetMode(int roomNumber) {
    int totalPatient = 150;
    int timeOfRotation = 5;
    for (int i = 0; i < totalPatient; i++) {
      registerPatient();
    }
    //preset for 5
    int count = 0;
    for (int i = 1; i <= timeOfRotation; i++) {
      if (room * i > 150) {
        break;
      }
      assignPatient();

      int examroomSize = examRoom.size();

      for (int j = 0; j < examroomSize; j++) {
        Patient patient = examRoom.get(j).getPatient();
        patient.setExamed(true);
        patient.setDeparturetime(LocalDateTime.ofInstant(Instant.ofEpochMilli(examRoom.get(j)
                .getEndingTime()), ZoneId.systemDefault()));
        examed.add(patient);
        waitingRoom.add(examRoom.get(j));
        count++;
      }
      examRoom.clear();
    }
    assignNumber = count;
  }

  /**
   * This assign the patient to the room
   */
  public boolean assignPatient() {
    if (waitingRoom.size() == 0 || patients.size() == 0) {
      return false; //no room provided
    }


    int size = waitingRoom.size();
    for (int i = 0; i < size; i++) {
      Room theroom = waitingRoom.peek();
      Patient thepatient = patients.peek();
      if (thepatient == null || theroom == null) {
        break;
      }
      theroom.addPatient(thepatient);
      patients.remove(thepatient);
      waitingRoom.remove(theroom);
      examRoom.add(theroom);
    }
    return true;
  }

  /**
   * check if the current room has done the treatment
   *
   * @param room the current room
   * @return true if it's done
   */
  public boolean doneExam(Room room) {

    Patient patient = room.getPatient();
    patient.setExamed(true);
    patient.setDeparturetime(LocalDateTime.ofInstant(Instant.ofEpochMilli
            (room.getEndingTime()), ZoneId.systemDefault()));
    examed.add(patient);
    if (examRoom.contains(room)) {
      examRoom.remove(room);
      if (!waitingRoom.contains(room)) {
        waitingRoom.add(room);
        return true;
      }
    }
    return false;
  }

  public MyPriorityQueue<Room> getExamRoom() {
    return examRoom;
  }

  public Room getLastInUseRoom() {
    return getTail(examRoom);
  }

  /**
   * Calculate the average waiting time for all the patients
   *
   * @return the average waiting time
   */
  public double averageTime() {
    if (totalpatient == 0) {
      return 0;
    }

    //check for the waiting patients
    if (patients.size() != 0) {
      int size = patients.size();
      for (int i = 0; i < size; i++) {
        Patient patient = patients.poll();
        waitingPatientList.add(patient);

        time += (patient.getWaiting()) / MILLISECTOMIN;
        calculateTimeForEmergencyPatient(patient);
      }
    }

    //check for the patients who is still in exam
    if (examRoom.size() != 0) {
      int size = examRoom.size();
      for (int i = 0; i < size; i++) {
        Room room = examRoom.poll();
        inuseRoomList.add(room);
        roomList.add(room);
        time += (room.getPatient().getWaiting()) / MILLISECTOMIN;
        calculateTimeForEmergencyPatient(room.getPatient());
      }
    }
    //check for the patients who have done the exam
    int cnt = examed.size();
    for (int i = 0; i < cnt; i++) {
      Patient patient = examed.poll();
      departurePatientList.add(patient);
      time += (patient.getWaiting()) / MILLISECTOMIN;
      calculateTimeForEmergencyPatient(patient);
    }

    if (waitingRoom.size() != 0) {
      int size = waitingRoom.size();
      for (int i = 0; i < size; i++) {
        Room room = waitingRoom.poll();
        roomList.add(room);
      }
    }

    highemergencytimeave = highEmergencyTime / countHighEmergency;
    lowemergencytimeave = lowEmergencyTime / countLowEmergency;
    return time / totalpatient;
  }

  /**
   * Calculate the waiting time for low and high emergency patients
   *
   * @param patient patient obj
   */
  public void calculateTimeForEmergencyPatient(Patient patient) {

    if (patient.getRating() >= HIGHRATINGLEVEL) {
      highEmergencyTime += (patient.getWaiting()) / MILLISECTOMIN;
      countHighEmergency++;
    } else if (patient.getRating() <= LOWRATINGLEVEL && patient.getRating() > 0) {
      lowEmergencyTime += (patient.getWaiting()) / MILLISECTOMIN;
      countLowEmergency++;
    }
  }

  /**
   * Used to calculate the average treatment time for patients who received the exam
   *
   * @return average treatment time
   */
  public double averageTreatmentTime() {
    if (departurePatientList.size() == 0 && inuseRoomList.size() == 0) {
      return -1;
    }
    long duration = 0;
    for (int i = 0; i < departurePatientList.size(); i++) {
      duration += departurePatientList.get(i).getDuration() / MILLISECTOMIN;
    }
    for (int i = 0; i < inuseRoomList.size(); i++) {
      duration += inuseRoomList.get(i).getPatient().getDuration() / MILLISECTOMIN;
    }
    return duration / (double) (departurePatientList.size() + inuseRoomList.size());
  }


  public double getHighemergencytimeave() {
    return highemergencytimeave;
  }

  public double getLowemergencytimeave() {
    return lowemergencytimeave;
  }

  /**
   * Find the room with specific id
   *
   * @param roomId id to looking for
   * @return the room object
   */
  public Room getRoomById(int roomId) {
    for (int i = 0; i < roomList.size(); i++) {
      Room room = roomList.get(i);
      if (room.getRoomId() == roomId) {
        return room;
      }
    }
    return null;
  }

  /**
   * Looking for the last room object in the queue
   *
   * @param roomsInuse the queue to looking for
   * @return the desired room object
   */
  public Room getTail(MyPriorityQueue<Room> roomsInuse) {
    int size = roomsInuse.size();
    ArrayList<Room> roomArrayList = new ArrayList();
    if (size > 1) {
      for (int i = 0; i < size - 1; i++) {
        Room theroom = roomsInuse.poll();
        roomArrayList.add(theroom);
      }
      Room neededroom = roomsInuse.peek();
      for (int i = 0; i < roomArrayList.size(); i++) {
        roomsInuse.add(roomArrayList.get(i));
      }
      return neededroom;
    } else if (size == 1) {
      return roomsInuse.peek();
    } else {
      return null;
    }
  }

  /**
   * Set all waiting patient time after 8 hours done
   */
  public void setPatientWaitingTime() {
    long frequency = 1000 * 60; //1min
    for (int i = 0; i < patients.size(); i++) {
      Patient patient = patients.get(i);
      if (patient != null) {
        if (option == 1) {
          patient.setWaiting(System.currentTimeMillis() - patients.get(i).getArrivaltime()
                  .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        } else {
          patient.setWaiting(System.currentTimeMillis() - patients.get(i).getArrivaltime()
                  .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + frequency * i);
        }

      }
    }

  }


  public int getAssignNumber() {
    return assignNumber;
  }
}

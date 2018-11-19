package edu.neu.ccs.cs5010.assignment2;

import java.time.ZoneId;

public class Room implements Comparable<Room> {


  private final double MAX_HOUR_IN_USE = 8 * 3600 * 1000;    //max hour for each room to use 8hours

  private long timeUse = 0;   //actual time for the room be used
  private double percentageBusy;
  private boolean inuse = false;
  private int roomId;
  private Patient patient;
  private long endingTime;
  private int numberPatient = 0;

  /**
   * Constructor
   *
   * @param timeUse estimated treatment time
   * @param roomId      room id
   */
  public Room(long timeUse, int roomId) {

    this.timeUse = timeUse;
    this.roomId = roomId;

  }

  public void setPercentageBusy(double percentageBusy) {
    this.percentageBusy = percentageBusy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Room)) {
      return false;
    }

    Room room = (Room) obj;

    return roomId == room.roomId;
  }

  @Override
  public int hashCode() {
    return roomId;
  }

  /**
   * This method is to add the patient to the room
   *
   * @param patient the patient object to add
   */
  public void addPatient(Patient patient) {
    this.patient = patient;
    timeUse += patient.getDuration();
    this.percentageBusy = timeUse * 100 / MAX_HOUR_IN_USE;
    endingTime = System.currentTimeMillis() + timeUse;
    patient.setWaiting(System.currentTimeMillis() -
            patient.getArrivaltime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    inuse = true;
    numberPatient++;
  }

  /**
   * Compare with the percentage time in order to store into the priority queue
   *
   * @param other other room to compare to
   * @return
   */
  @Override
  public int compareTo(Room other) {
    if (inuse == false) {
      if (other.roomId < 0) {
        return 0;
      }
      if (this.percentageBusy <= other.percentageBusy) {
        return -1;
      }
      return 1;
    } else {
      if (this.endingTime <= other.endingTime) {
        return -1;
      }
      return 1;
    }
  }


  public void setInuse(boolean inuse) {
    this.inuse = inuse;
  }

  public void setTimeUse(long timeUse) {
    this.timeUse = timeUse;
  }

  public long getTimeUse() {
    return timeUse;
  }

  public int getRoomId() {
    return roomId;
  }

  public Patient getPatient() {
    return patient;
  }

  public long getEndingTime() {
    return endingTime;
  }

  public int getNumberPatient() {
    return numberPatient;
  }

  public double getPercentageBusy() {
    return percentageBusy;
  }
}

package edu.neu.ccs.cs5010.assignment2;
///edu.neu.ccs.cs5010.assignment2.Patient individual class property


import java.time.LocalDateTime;

public class Patient implements Comparable<Patient> {

  private double temperature;
  private double bloodpressure;
  private int age;
  private int patientId;
  private LocalDateTime arrivaltime;      //time of arrival
  private double rating = -1;                  //emergency rating
  private LocalDateTime departuretime;    //time of departure
  private boolean examed = false;   // used to determine if the patient leaved
  private long duration;        //length of time to be treated in millisec
  private long waiting;           //length of time for waiting


  //different ages for each groups
  private final int BABY = 3;
  private final int OLDER = 80;
  private final int YOUNG = 20;
  private final int CHILD = 10;
  private final int OLD = 50;


  private final double FEVERTEMP = 99.5; //fever temperature of human in F
  private final double BADFEVERTEMP = 104.0; //worse fever temperature of human in F
  private final double HIGHBLOODPRESSURE = 140.0; //Highest blood pressure in mm HG
  private final int AGE_MAX = 140;               //maximum age
  private final int LEVEL_MAX = 10;               //maximum level of severe


  public double getRating() {
    if (rating == -1) {
      throw new IllegalArgumentException("Not enough info provided for rating");
    }
    return rating;
  }

  //default constructor
  public Patient() {
    throw new IllegalArgumentException("age,id, name needed");
  }

  //constructor
  //level is a level of severe ranging from 1-10 given by the nurse
  public Patient(int age, int patientId, String name, double level) {
    if (level < 1 || level > LEVEL_MAX) {
      throw new IllegalArgumentException("Please give a valid severe level range from 1-10");
    }
    if (age >= AGE_MAX || age < 0) {
      throw new IllegalArgumentException("Please give a valid age");
    }
    if (patientId < 0) {
      throw new IllegalArgumentException("Please give a valid id");
    }
    if (name == null) {
      throw new IllegalArgumentException("Please provide a name");
    }
    arrivaltime = LocalDateTime.now();
    this.age = age;
    this.patientId = patientId;
    calculateRating(level);
  }

  /**
   * This method used to calculate the rating
   *
   * @param level is a level of severe ranging from 1-10 given by the nurse
   */
  private void calculateRating(double level) {
    double coefficient = 1;
    if (this.age <= BABY || this.age > OLDER) {
      coefficient += 1;
    } else if ((this.age > BABY && this.age <= CHILD) || (this.age > OLD && this.age <= OLDER)) {
      coefficient += 0.8;
    } else if (this.age > CHILD && this.age <= YOUNG) {
      coefficient += 0.6;
    } else {
      coefficient += 0.5;
    }

    //checking the fever condition
    if (this.temperature - BADFEVERTEMP > 0) {
      coefficient += 1.5;  //bad fever
    } else {
      //minor fever
      if (this.temperature - FEVERTEMP > 0) {
        coefficient += 1;
      }
    }
    //Checking for blood pressure
    if (this.bloodpressure - HIGHBLOODPRESSURE > 0) {
      coefficient += 0.5;
    }

    //The maximum rating will be 100
    rating = coefficient * 15 + 4 * level;

  }

  public int getPatientId() {
    return patientId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Patient)) {
      return false;
    }

    Patient patient = (Patient) obj;

    return patientId == patient.patientId;
  }

  @Override
  public int hashCode() {
    return patientId;
  }

  public void setArrivaltime(LocalDateTime arrivaltime) {
    this.arrivaltime = arrivaltime;
  }

  //the lower the priority is
  //Wont be same priority because the arrival time will be different
  public int compareTo(Patient other) {
    //waiting
    if (!examed) {
      if (Double.compare(this.rating, other.getRating()) > 0) {
        return -1;
      } else if (Double.compare(this.rating, other.getRating()) == 0) {
        //compare the time then
        if (other.getPatientId() < 0) {
          return 0;
        }
        if (arrivalComparison(other)) {
          return -1;
        }
        return 1;
      }
      return 1;
    }
    //departure
    else {
      if (this.departuretime.isBefore(other.getDeparturetime()) ||
              this.departuretime.isEqual(other.getDeparturetime())) {
        return -1;
      }
      return 1;
    }
  }


  /**
   * This method compares the arrival of patient with the other
   *
   * @param other the other patient to compare
   * @return true if this patient arrived before the other one
   */
  protected boolean arrivalComparison(Patient other) {
    if (this.arrivaltime.isBefore(other.getArrivaltime())) {
      return true;
    }
    return false;
  }


  public LocalDateTime getArrivaltime() {
    return arrivaltime;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }


  public void setBloodpressure(double bloodpressure) {
    this.bloodpressure = bloodpressure;
  }

  public LocalDateTime getDeparturetime() {
    return departuretime;
  }

  public void setDeparturetime(LocalDateTime departuretime) {
    this.departuretime = departuretime;
  }

  public void setExamed(boolean examed) {
    this.examed = examed;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public long getWaiting() {
    return waiting;
  }

  public void setWaiting(long waiting) {
    this.waiting = waiting;
  }
}

package edu.neu.ccs.cs5010.assignment2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HospitalTest {

  private MyPriorityQueue myPriorityQueue;
  private Patient patient = null;
  private Patient patient2 = null;

  private Room room1 = null;
  private Room room2 = null;
  private MyPriorityQueue<Room> waiting_room;
  private MyPriorityQueue<Patient> patients;
  private MyPriorityQueue<Patient> examed;
  private MyPriorityQueue<Room> exam_room;

  private Hospital hospital = new Hospital(2, 2);

  @Before
  public void setUp() throws Exception {
    waiting_room = new MyPriorityQueue<Room>();
    patients = new MyPriorityQueue<Patient>();
    examed = new MyPriorityQueue<Patient>();
    exam_room = new MyPriorityQueue<Room>();

    room1 = new Room(2, 1);
    room2 = new Room(3, 2);
    room1.setInuse(true);
    room1.setTimeUse(1000);
    room1.getTimeUse();
    assertEquals(-1, room1.compareTo(room2));

    patient = new Patient(3, 2, "YYF", 3);
    patient2 = new Patient(3, 3, "NS", 2);
    patient.equals(patient2);
    patient.getTemperature();
    patient.getPatientId();
    patient.getDeparturetime();

    patient.setDuration(5 * 60 * 1000);
    patient2.setDuration(6 * 60 * 1000);

    room1.addPatient(patient2);
    room2.addPatient(patient);

    waiting_room.add(room1);
    waiting_room.add(room2);

    Room room3 = new Room(3, 3);
    Room room4 = new Room(4,4);
    room3.setPercentageBusy(100);
    room4.setPercentageBusy(150);
    room3.setInuse(false);
    room4.setInuse(false);
    exam_room.add(room4);
    exam_room.add(room3);
  }

  @Test
  public void registerPatient() throws Exception {
    assertEquals(2, waiting_room.peek().getRoomId());
  }

  @Test
  public void getTailRoom() throws Exception {
    assertEquals(0, hospital.getTail(hospital.getWaitingRoom()).getRoomId());
    assertFalse(hospital.doneExam(room1));
  }

}
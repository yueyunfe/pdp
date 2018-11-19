package edu.neu.ccs.cs5010.assignment2;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PatientTest {
  private static Patient patient = null;
  private static Patient patient2 = null;

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void Constructor() throws Exception {
    try {
      patient = new Patient();
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("age,id, name needed"));
    }

    //test wrong age
    try {
      patient = new Patient(-1, 1, "YYF", 2);
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Please give a valid age"));
    }

    //test wrong id
    try {
      patient = new Patient(2, -2, "YYF", 2);
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Please give a valid id"));
    }

    //test wrong name
    try {
      patient = new Patient(1, 1, null, 2);
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Please provide a name"));
    }

    //test wrong level
    try {
      patient = new Patient(1, 1, null, 11);
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Please give a valid severe level range from 1-10"));
    }


  }

  @Test
  public void compareTo() throws Exception {
    //diffrent level of pain should result in a different rating
    patient = new Patient(3, 2, "YYF", 3);
    patient2 = new Patient(3, 3, "NS", 2);
    assertEquals(-1, patient.compareTo(patient2));

    //diffrent age should result in a high different rating
    patient = new Patient(2, 2, "YYF", 3);
    patient2 = new Patient(4, 3, "NS", 3);
    assertEquals(-1, patient.compareTo(patient2));

    //diffrent arrival time different rating
    patient = new Patient(3, 2, "YYF", 3);
    patient2 = new Patient(3, 3, "NS", 3);
    patient.setArrivaltime(LocalDateTime.parse("2017-10-01T01:52:10.977"));
    assertEquals(-1, patient.compareTo(patient2));


  }

}
package edu.neu.ccs.cs5010.assignment3;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InfoFactoryTest {

  private static InfoFactory infoFactory = null;

  @BeforeClass
  public static void setupBeforeTests() throws Exception {
    infoFactory = new InfoFactory("debugg.csv", "email-template.txt");
  }


  @Test
  public void testReadFile() {
    assertEquals(true, infoFactory.readInfoFile());
    assertEquals(true, infoFactory.readTemplateFile());
  }

  @Test
  public void testLocalizeIndex() {
    infoFactory.readInfoFile();
    assertEquals(0, infoFactory.getFirstnameIndex());
    assertEquals(9, infoFactory.getRewardsIndex());
    assertEquals(2, infoFactory.getAddressIndex());

  }

  @Test
  public void testPatternSearchAndReplace() {
    infoFactory.setDestinationCity("12321");
    infoFactory.setDepartureCity("2221");
    infoFactory.setFlightNumber("x23");

    String template = "hellooooooo ezzz";

    //cases for no change
    String email = infoFactory.patternSearchAndReplace("email", template, infoFactory.getInfoList().get(1), "arrival");
    assertEquals(template, email);

    //cases for fail since nothing match email
    email = infoFactory.patternSearchAndReplace("emailss", template, infoFactory.getInfoList().get(1), "arrival");
    assertEquals(null, email);

    //cases to replace [[email]]
    template = "hellooooooo ezzz [[email]]";
    email = infoFactory.patternSearchAndReplace("email", template, infoFactory.getInfoList().get(1), "arrival");
    assertTrue(email.equals("hellooooooo ezzz " + "jbutt@gmail.com"));
  }

  @Test
  public void testReplace() {
    infoFactory.setDestinationCity("12321");
    infoFactory.setDepartureCity("2221");
    infoFactory.setFlightNumber("x23");
    assertEquals(true, infoFactory.replaceTemplatePlaceHolder(infoFactory.getInfoList().get(1), "arrival"));

    //test for a dummy template email
    infoFactory.setTestEmail(null);
    assertEquals(false, infoFactory.replaceTemplatePlaceHolder(infoFactory.getInfoList().get(1), "arrival"));

    //test for non-defined placeholder
    infoFactory.setTestEmail("hello [[world]]");
    try {
      infoFactory.replaceTemplatePlaceHolder(infoFactory.getInfoList().get(1), "arrival");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("PlaceHolder Not Found"));
    }
  }

}
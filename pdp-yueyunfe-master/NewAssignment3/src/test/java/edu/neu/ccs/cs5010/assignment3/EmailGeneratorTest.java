package edu.neu.ccs.cs5010.assignment3;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailGeneratorTest {

  @Test
  public void testMain() throws Exception {
    EmailGenerator emailGenerator = new EmailGenerator();
    String[] input = new String[]{"--email-template email-template.txt --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival"};
    emailGenerator.main(input);

    input = new String[]{"--email-template email-temple.txt --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival"};
    try {
      emailGenerator.main(input);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Fail"));
    }

    input = new String[]{"--email-template email-temple.txt --output-dir emails --csv-file Flight3FromVancouverToSttle.csv --event arrival"};
    try {
      emailGenerator.main(input);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Fail"));
    }
  }

  //--email-template email-template.txt --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival
  @Test
  public void checkInput() throws Exception {
    EmailGenerator emailGenerator = new EmailGenerator();
    //missing template
    String[] input = new String[]{"--email-template  --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival"};
    emailGenerator.checkInput(input);
    String error = emailGenerator.getError();
    assertTrue(error.contains("Missing template file"));

    //missing csv command
    input = new String[]{"--email-template email-template.txt --output-dir emails  Flight3FromVancouverToSeattle.csv --event arrival"};
    emailGenerator.checkInput(input);
    assertTrue(emailGenerator.getError().contains("Missing csv file command"));

    //wrong formart of txt file
    input = new String[]{"--email-template email-template.txz --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival"};
    emailGenerator.checkInput(input);
    error = emailGenerator.getError();
    assertTrue(error.contains("Wrong format of template file"));

    //wrong formart of txt file
    input = new String[]{"--email-template email-template.txt --output-dir emails --csv-file Flight3FromVancouverToSeattle.cs --event arrival"};
    emailGenerator.checkInput(input);
    assertTrue(emailGenerator.getError().contains("Wrong format of csv file"));

    //correct one
    input = new String[]{"--email-template email-template.txt --output-dir emails --csv-file Flight3FromVancouverToSeattle.csv --event arrival"};
    assertTrue(emailGenerator.checkInput(input));

    //correct one with mix order
    input = new String[]{"--output-dir emails --event arrival --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv"};
    assertTrue(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir emails --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv --event"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir emails --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv --event eas"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir emails --email-template email-template.txt --csv-file Flight3FromVancouverToSeattlecsv --event arrival"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir --event arrival --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir emails arrival --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"emails --event arrival --email-template email-template.txt --csv-file Flight3FromVancouverToSeattle.csv"};
    assertFalse(emailGenerator.checkInput(input));

    input = new String[]{"--output-dir emails --event arrival email-template.txt --csv-file Flight3FromVancouverToSeattle.csv"};
    assertFalse(emailGenerator.checkInput(input));

    assertFalse(emailGenerator.setFlightInfo("ToSee."));
    assertFalse(emailGenerator.setFlightInfo("FromToSee."));
    assertFalse(emailGenerator.setFlightInfo("FlightFromVToSee."));
    assertFalse(emailGenerator.setFlightInfo("Flight3FromVTo."));
    assertFalse(emailGenerator.setFlightInfo("Flight3FromToSee"));
    assertFalse(emailGenerator.setFlightInfo("Flight3FromVTo"));
  }

}
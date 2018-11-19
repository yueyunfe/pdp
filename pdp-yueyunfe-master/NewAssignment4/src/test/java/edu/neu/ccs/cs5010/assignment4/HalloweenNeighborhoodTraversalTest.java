package edu.neu.ccs.cs5010.assignment4;

import org.junit.Test;

import static org.junit.Assert.*;

public class HalloweenNeighborhoodTraversalTest {
  private String[] input = new String[]{""};
  private HalloweenNeighborhoodTraversal halloweenNeighborhoodTraversal;

  @org.junit.Before
  public void setUp() throws Exception {

    halloweenNeighborhoodTraversal = new HalloweenNeighborhoodTraversal();
  }

  @Test
  public void testMain() throws Exception {
    input = new String[]{"2 DreamCandy1.csv DreamCandy2.csv"};
    halloweenNeighborhoodTraversal.main(input);
  }

  @Test
  public void checkInput() throws Exception {
    //test for null case
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));
    //test for only 1 parameter
    input = new String[]{"1"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));
    input = new String[]{"DreamCandy1.csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    //wrong number
    input = new String[]{"-1 DreamCandy1.csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    //1 does not match 2 files
    input = new String[]{"1 DreamCandy1.csv DreamCandy2.csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    input = new String[]{"2 DreamCandy1.csv DreamCandy2csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    input = new String[]{"2 DreamCandy1.csv DreamCandy2.cs"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    input = new String[]{"2 DreamCandy1.csv .csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    //no dreamcandy
    input = new String[]{"2 DreamCandy1.csv xx2.csv"};
    assertFalse(halloweenNeighborhoodTraversal.processInput(input));

    input = new String[]{"2 DreamCandy1.csv DreamCandy2.csv"};
    assertTrue(halloweenNeighborhoodTraversal.processInput(input));

    input = new String[]{"1 DreamCandy1.csv"};
    assertTrue(halloweenNeighborhoodTraversal.processInput(input));
  }


}
package edu.neu.ccs.cs5010.assignment4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChildTest {

  private String[] args;
  private HalloweenNeighborhoodTraversal halloweenNeighborhoodTraversal;
  private Child child;

  @Before
  public void setUp() throws Exception {
    args = new String[]{"DreamCandy1.csv"};
    halloweenNeighborhoodTraversal.processInput(args);
    halloweenNeighborhoodTraversal = new HalloweenNeighborhoodTraversal();
    String input = "";
    for (int i = 0; i < args.length; i++) {
      input += args[i] + " ";
    }
    input = input.trim();
    child = new Child(input, "1");
  }

  @Test
  public void readCSV() throws Exception {
  }


  @Test
  public void createCandyObject() throws Exception {
    child.getCandyList().add("DDD");
    child.getCandyList().add("crunch");
    child.getCandyList().add("whoopers");
    child.getSizeList().add("King");
    child.getSizeList().add("regular");
    child.getSizeList().add("regular");

    child.createCandyObject();
    assertEquals(3, child.getCandies().size());
    assertEquals(null, child.getCandies().get(0));
    assertNotEquals(null, child.getCandies().get(1));
    assertEquals("1", child.getChildId());
    assertNotEquals(null, child.getCandies().get(2));

  }

}
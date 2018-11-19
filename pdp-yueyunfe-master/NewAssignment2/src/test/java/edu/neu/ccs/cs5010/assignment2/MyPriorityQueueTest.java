package edu.neu.ccs.cs5010.assignment2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyPriorityQueueTest {
  private MyPriorityQueue<Room> exam_room;
  private Patient patient = null;
  private Patient patient2 = null;

  private Room room1 = null;
  private Room room2 = null;
  private Room room3 = null;

  @Before
  public void setUp() throws Exception {

    exam_room = new MyPriorityQueue<Room>();

    room1 = new Room(2, 1);
    room2 = new Room(3, 2);
    room3 = new Room(4, 3);
  }

  @Test
  public void testPriorityQueue() throws Exception {
    //test insert and size()
    exam_room.insert(room1);
    assertEquals(1, exam_room.size());

    //test front
    Room roomFront = exam_room.front();
    assertEquals(1, roomFront.getRoomId());
    assertEquals(1, exam_room.size());

    //test remove
    Room room = exam_room.poll();
    assertEquals(0, exam_room.size());
    assertEquals(1, room.getRoomId());

    //test empty
    assertTrue(exam_room.isEmpty());

    //test ForwardTraversal and backward
    exam_room.insert(room1);
    exam_room.insert(room2);
    exam_room.insert(room3);

    assertEquals(room1, exam_room.testForwardTraversal().get(0));
    assertEquals(room3, exam_room.testReverseTraversal().get(0));
    exam_room.remove();


  }

}
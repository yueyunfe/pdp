package edu.neu.ccs.cs5010.assignment2;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


public class QueueTest {

  private static MyQueue queue;

  @BeforeClass
  public static void setupBeforeTest() throws Exception {
    queue = new MyQueue();

  }

  @Test
  public void testEnqueue() {
    queue.enqueue(5);
    queue.enqueue(6);
    assertEquals("first element shoud be 5", 5, queue.front());
    queue.dequeue();
    queue.dequeue();
  }

  @Test
  public void testFront() {
    queue.enqueue(3);
    queue.enqueue(7);
    assertEquals("the front element", 3, queue.front());
    queue.dequeue();
    queue.dequeue();
  }

  @Test
  public void testDequeue() {
    queue.enqueue(5);
    queue.enqueue(3);
    queue.enqueue(8);
    assertEquals("front", 5, queue.front());
    queue.dequeue();
    assertEquals("after dequeue, the front element should be 3", 3, queue.front());
    queue.dequeue();
    assertEquals("after dequeue, the front element should be 8", 8, queue.front());
    queue.dequeue();
  }

  /**
   * Test the isEmpty method
   */
  @Test
  public void testIsEmpty() {
    assertTrue("queue is empty", queue.isEmpty());
    queue.enqueue(5);
    assertFalse("queue is not empty", queue.isEmpty());
    queue.dequeue();
  }

}

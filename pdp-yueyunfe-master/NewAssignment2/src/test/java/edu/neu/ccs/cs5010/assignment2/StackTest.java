package edu.neu.ccs.cs5010.assignment2;

import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


public class StackTest {
  private static MyStack stack;

  @BeforeClass
  public static void setupBeforeTest() throws Exception {
    stack = new MyStack();
  }

  @Test
  public void testPush() {
    stack.push(3);
    stack.push(5);
    assertFalse("after push,the stack should not be empty ", stack.isEmpty());
    assertEquals("the top element", 5, stack.top());
    stack.pop();
    stack.pop();
  }

  @Test
  public void testTop() {
    stack.push(4);
    stack.push(6);
    stack.push(3);
    assertEquals("top element", 3, stack.top());
    stack.pop();
    assertEquals("top element", 6, stack.top());
    stack.pop();
    assertEquals("top element", 4, stack.top());
    stack.pop();
  }


  @Test
  public void testPop() {
    stack.push(5);
    stack.push(6);
    stack.pop();
    assertEquals("after pop,the top element", 5, stack.top());
    stack.pop();
    assertTrue("stack should be empty", stack.isEmpty());
  }


  @Test
  public void testIsEmpty() {
    assertTrue("stack should be empty", stack.isEmpty());
    stack.push(4);
    assertFalse("after push,stack should not be empty", stack.isEmpty());
    stack.pop();
    assertTrue("after pop,stack should not be empty", stack.isEmpty());
  }
}

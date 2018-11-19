package edu.neu.ccs.cs5010.assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyPriorityQueue<T extends Comparable<T>> implements IPriorityQueue<T> {


  private ArrayList<T> queueList;

  public MyPriorityQueue() {
    queueList = new ArrayList<>();
  }


  public int size() {
    return queueList.size();
  }

  public boolean contains(T type) {
    return queueList.contains(type);
  }

  /**
   * Retrieves and removes the head of this queue, or throw exception if this queue is empty.
   *
   * @return the object
   */
  //same as required remove method
  @Override
  public T poll() {
    if (queueList.size() == 0) {
      throw new IllegalArgumentException("No element to remove");
    }
    T result = queueList.get(0);
    queueList.remove(0);
    return result;
  }

  /**
   * Compare the obj for generic type
   *
   * @param other
   * @return
   */
  @Override
  public int compareTo(T other) {
    //last element
    T tailObj = queueList.get(queueList.size() - 1);
    int result = tailObj.compareTo(other);
    if (result < 0) {
      return -1;
    } else if (result == 0 && tailObj.equals(other)) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
   *
   * @return the object
   */
  public T peek() {
    if (queueList.size() == 0) {
      return null;
    }
    return queueList.get(0);
  }

  @Override
  //same as insert
  public void insert(T type) {
    if (type != null) {
      queueList.add(type);
      Collections.sort(queueList);
    }
  }

  @Override
  //same as insert
  public void add(T type) {
    if (type != null) {
      queueList.add(type);
      Collections.sort(queueList);
    }
  }

  @Override
  public T remove() {
    if (queueList.size() == 0) {
      throw new IllegalArgumentException("No element to remove");
    }
    T result = queueList.get(0);
    queueList.remove(0);
    return result;
  }


  @Override
  public void remove(T type) {
    if (queueList.size() == 0) {
      throw new IllegalArgumentException("No element to remove");
    }
    for (int i = 0; i < queueList.size(); i++) {
      if (queueList.get(i).equals(type)) {
        queueList.remove(i);
        return;
      }
    }
  }

  @Override
  public T front() {
    if (queueList.size() == 0) {
      throw new IllegalArgumentException("No front element");
    }
    return queueList.get(0);
  }

  public T get(int index) {
    if (index >= 0 && queueList.get(index) != null) {
      return queueList.get(index);
    }
    return null;
  }

  public void clear() {
    queueList.clear();
  }

  @Override
  public boolean isEmpty() {
    return queueList.isEmpty();
  }

  @Override
  public List testForwardTraversal() {
    List<T> listForward = new LinkedList<T>();
    for (int i = 0; i < queueList.size(); i++) {
      listForward.add(queueList.get(i));
    }
    if (listForward.size() != 0) {
      return listForward;
    }
    return null;
  }

  @Override
  public List testReverseTraversal() {
    List<T> listBackWard = new LinkedList<T>();
    for (int i = queueList.size() - 1; i >= 0; i--) {
      listBackWard.add(queueList.get(i));
    }
    if (listBackWard.size() != 0) {
      return listBackWard;
    }
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MyPriorityQueue)) {
      return false;
    }

    MyPriorityQueue<?> that = (MyPriorityQueue<?>) obj;

    return queueList.equals(that.queueList);
  }

  @Override
  public int hashCode() {
    return queueList.hashCode();
  }
}

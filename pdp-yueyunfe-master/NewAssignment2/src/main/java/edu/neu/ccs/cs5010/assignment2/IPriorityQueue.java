package edu.neu.ccs.cs5010.assignment2;

import java.util.List;

public interface IPriorityQueue<T> {
  void add(T type);

  void insert(T type);

  //poll is the same as required remove(), changed the name due to the same functionality to remove(T type)
  T poll();

  T remove();

  int compareTo(T other);

  void remove(T type);

  T front();

  boolean isEmpty();

  List testForwardTraversal();

  List testReverseTraversal();
}

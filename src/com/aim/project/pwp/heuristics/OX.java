package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.utilities.PermutationUtils;

import java.util.*;

public class OX extends XOHeuristic {

  public OX(Random oRandom) {
    super(oRandom);
  }

  @Override
  protected void crossoverAlgorithm(int[] parent1, int[] parent2, int numberOfDeliveryLocations) {
    // generates two random numbers and guarantees numbers 0 and (NumberOfDeliveryLocations + 1)
    // will not be chosen simultaneously
    int number1 = oRandom.nextInt(numberOfDeliveryLocations);
    int number2 = oRandom.nextInt(numberOfDeliveryLocations + 1);

    // makes the smaller the start and the larger the end
    int start = Math.min(number1, number2);
    int end = Math.max(number1, number2);

    // backs up the parents for later use
    int[] p1copy = parent1.clone();
    int[] p2copy = parent2.clone();

    // new children will override parent1 and parent2
    OXhelper(parent1, p2copy, start, end);
    OXhelper(parent2, p1copy, start, end);
  }

  private void OXhelper(int[] p1, int[] p2copy, int start, int end) {
    // gets TODO
    int[] inverseP2 = PermutationUtils.INSTANCE.inverse(p2copy);

    int totalLength = p1.length;
    int copyLength = end - start;

    // marks the locations already present in copy segment as -1
    for (int i = 0; i < copyLength; i++) {
      int locationInP1 = p1[start + i];
      int indexInP2 = inverseP2[locationInP1];
      p2copy[indexInP2] = -1;
    }

    // TODO
    int indexInP1 = end;
    for (int i = 0; i < totalLength; i++) {
      int locationInP2 = p2copy[(end + i) % totalLength];
      if (locationInP2 != -1) {
        p1[indexInP1++ % totalLength] = locationInP2;
      }
    }
  }
}

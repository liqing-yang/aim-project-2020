package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.utilities.ArrayUtils;

import java.util.Random;

public class CX extends XOHeuristic {

  public CX(Random oRandom) {
    super(oRandom);
  }

  @Override
  protected void crossoverAlgorithm(int[] parent1, int[] parent2, int numberOfDeliveryLocations) {
    // chooses a starting point randomly
    int index = oRandom.nextInt(numberOfDeliveryLocations);

    // if the locations at the index are the same, a cycle is already completed
    if (parent1[index] == parent2[index]) {
      return;
    }

    do {
      // backs up current index for later use
      int backup = index;

      // gets the location at current index in parent2
      int locationInP2 = parent2[index];

      // gets the index of the location in parent1;
      // index will be -1 if the location is not found, which means if the location is copied to
      // parent1 there will be no conflict and the cycle is completed.
      index = ArrayUtils.INSTANCE.find(parent1, locationInP2);

      // swaps the locations at the backup index
      ArrayUtils.INSTANCE.swapBetweenArray(parent1, parent2, backup);
    } while (index != -1); // cycle completed
  }
}

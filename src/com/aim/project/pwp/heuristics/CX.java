package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.utilities.ArrayUtils;

import java.util.Random;

public class CX extends XOHeuristic {

  public CX(Random oRandom) {
    super(oRandom);
  }

  @Override
  protected void crossoverAlgorithm(int[] child1, int[] child2, int numberOfDeliveryLocations) {
    int index = oRandom.nextInt(numberOfDeliveryLocations);

    if (child1[index] == child2[index]) {
    	return;
		}

    do {
      int backup = index;
      int locationInP2 = child2[index];
      index = ArrayUtils.INSTANCE.find(child1, locationInP2);
      ArrayUtils.INSTANCE.swapBetweenArray(child1, child2, backup);
    } while (index != -1);
  }
}

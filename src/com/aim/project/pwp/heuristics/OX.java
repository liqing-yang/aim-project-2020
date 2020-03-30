package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.utilities.PermutationUtils;

import java.util.*;

public class OX extends XOHeuristic {

  public OX(Random oRandom) {
    super(oRandom);
  }

  @Override
  protected void crossoverAlgorithm(int[] parent1, int[] parent2, int numberOfDeliveryLocations) {
    int number1 = oRandom.nextInt(numberOfDeliveryLocations);
    int number2 = oRandom.nextInt(numberOfDeliveryLocations + 1);

    int start = Math.min(number1, number2);
    int end = Math.max(number1, number2);

    int[] p1copy = parent1.clone();
    int[] p2copy = parent2.clone();

    OXhelper(parent1, p2copy, start, end);
    OXhelper(parent2, p1copy, start, end);
  }

  private void OXhelper(int[] p1, int[] p2copy, int start, int end) {
  	int[] inverseP2 = PermutationUtils.INSTANCE.inverse(p2copy);

  	int totalLength = p1.length;
  	int copyLength = end - start;

  	for (int i = 0; i < copyLength; i++) {
  		int locationInP1 = p1[start + i];
  		int indexInP2 = inverseP2[locationInP1];
  		p2copy[indexInP2] = -1;
		}

  	int indexInP1 = end;
  	for (int i = 0; i < totalLength; i++) {
  	  int locationInP2 = p2copy[(end + i) % totalLength];
  	  if (locationInP2 != -1) {
  	    p1[indexInP1++ % totalLength] = locationInP2;
      }
    }
	}
}

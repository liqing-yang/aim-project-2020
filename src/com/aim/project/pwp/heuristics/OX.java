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
  		if (p2copy[i] != -1) {
  			p1[(i + indexInP1++) % totalLength] = p2copy[i];
			}
		}
	}

  /*
  @Override
  protected void crossoverAlgorithm(int[] parent1, int[] parent2, int numberOfDeliveryLocations) {
     int number1 = oRandom.nextInt(numberOfDeliveryLocations);
     int number2 = oRandom.nextInt(numberOfDeliveryLocations + 1);

     int start = Math.min(number1, number2);
     int end = Math.max(number1, number2);

     int[] child1 = new int[numberOfDeliveryLocations];
     int[] child2 = new int[numberOfDeliveryLocations];

     System.arraycopy(parent1, start, child1, start, end - start);
     System.arraycopy(parent2, start, child2, start, end - start);

     int m = 0, n = 0;
     for (int i = 0; i < numberOfDeliveryLocations; i++) {
     	int currentLocationIndex = (i + end) % numberOfDeliveryLocations;

     	int currentLocationInP1 = parent1[currentLocationIndex];
     	int currentLocationInP2 = parent2[currentLocationIndex];

     	if (!ArrayUtils.INSTANCE.contains(child1, currentLocationInP2)) {
  			child1[(m++ + end) % numberOfDeliveryLocations] = currentLocationInP2;
  		}

     	if (!ArrayUtils.INSTANCE.contains(child2, currentLocationInP1)) {
     		child2[(n++ + end) % numberOfDeliveryLocations] = currentLocationInP1;
  		}
  	}

  	System.arraycopy(child1, 0, parent1, 0, numberOfDeliveryLocations);
    System.arraycopy(child2, 0, parent2, 0, numberOfDeliveryLocations);
  }
   */
}

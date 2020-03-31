package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.utilities.ArrayUtils;

public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

  public Reinsertion(Random oRandom) {
    super(oRandom);
  }

  @Override
  public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
    int times = getTimes(intensityOfMutation);

    int[] deliveryLocations = solution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = solution.getSolutionRepresentation().getNumberOfLocations();

    double cost = solution.getObjectiveFunctionValue();

    while (times > 0) {
      // randomly select a delivery location and find a different place for it
      int oldIndex = oRandom.nextInt(numberOfDeliveryLocations);
      int newIndex = oRandom.nextInt(numberOfDeliveryLocations);
      if (oldIndex == newIndex) {
        newIndex = newIndex + 1 == numberOfDeliveryLocations ? 0 : newIndex + 1;
      }

      cost -= getCostBtwPredAndSuccOf(deliveryLocations, oldIndex);
      cost -= oldIndex < newIndex ? getCostBtwSuccAnd(deliveryLocations, newIndex) : getCostBtwPredAnd(deliveryLocations, newIndex);

      int origin = deliveryLocations[oldIndex];

      // move elements between the original position and the new position
      int startPos = Math.min(oldIndex + 1, newIndex);
      int length = Math.abs(newIndex - oldIndex);
      int offset = Integer.compare(oldIndex, newIndex);
      ArrayUtils.INSTANCE.moveByOffset(deliveryLocations, startPos, length, offset);

      deliveryLocations[newIndex] = origin;

      cost += getCostBtwPredAndSuccOf(deliveryLocations, newIndex);
      cost += oldIndex < newIndex ? getCostBtwPredAnd(deliveryLocations, oldIndex) : getCostBtwSuccAnd(deliveryLocations, oldIndex);

      times--;
    }

    solution.setObjectiveFunctionValue(cost);
    return cost;
  }

  @Override
  public boolean isCrossover() {
    return false;
  }

  @Override
  public boolean usesIntensityOfMutation() {
    return true;
  }

  @Override
  public boolean usesDepthOfSearch() {
    return false;
  }
}

package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

  private final Random oRandom;

  public Reinsertion(Random oRandom) {
    super();
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
    int times = getTimes(intensityOfMutation);

    int[] deliveryLocations = solution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = solution.getSolutionRepresentation().getNumberOfLocations();

    double cost = solution.getObjectiveFunctionValue();

    while (times > 0) {
      // randomly select a delivery location and find a different place for it
      int originalIndex = oRandom.nextInt(numberOfDeliveryLocations);
      int reinsertedIndex = oRandom.nextInt(numberOfDeliveryLocations);
      if (originalIndex == reinsertedIndex) {
        reinsertedIndex = reinsertedIndex + 1 == numberOfDeliveryLocations ? 0 : reinsertedIndex + 1;
      }

      cost -= getCostBtwPredAndSuccOf(deliveryLocations, originalIndex);
      cost -= originalIndex < reinsertedIndex ? getCostBtwSuccAnd(deliveryLocations, reinsertedIndex) : getCostBtwPredAnd(deliveryLocations, reinsertedIndex);

      int origin = deliveryLocations[originalIndex];

      // move elements between the original position and the new position
      int startPos = Math.min(originalIndex + 1, reinsertedIndex);
      int length = Math.abs(reinsertedIndex - originalIndex);
      moveByOffset(deliveryLocations, startPos, length, Integer.compare(originalIndex, reinsertedIndex));

      deliveryLocations[reinsertedIndex] = origin;

      cost += getCostBtwPredAndSuccOf(deliveryLocations, reinsertedIndex);
      cost += originalIndex < reinsertedIndex ? getCostBtwPredAnd(deliveryLocations, originalIndex) : getCostBtwSuccAnd(deliveryLocations, originalIndex);

      times--;
    }

    solution.setObjectiveFunctionValue(cost);
    return cost;
  }

  /**
   * TODO
   *
   * @param array
   * @param startPos
   * @param length
   * @param offset
   */
  private void moveByOffset(int[] array, int startPos, int length, int offset) {
    System.arraycopy(array, startPos, array, startPos + offset, length);
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

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

    while (times > 0) {
      // randomly select a delivery location and find a different place for it
      int originIndex = oRandom.nextInt(numberOfDeliveryLocations);
      int reinsertedIndex = oRandom.nextInt(numberOfDeliveryLocations);
      if (originIndex == reinsertedIndex) {
        reinsertedIndex = reinsertedIndex + 1 == numberOfDeliveryLocations ? 0 : reinsertedIndex + 1;
      }

      int origin = deliveryLocations[originIndex];

      // move elements between the original position and the new position
      int startPos = Math.min(originIndex + 1, reinsertedIndex);
      int length = Math.abs(reinsertedIndex - originIndex);
      moveByOffset(deliveryLocations, startPos, length, Integer.compare(originIndex, reinsertedIndex));

      deliveryLocations[reinsertedIndex] = origin;

      times--;
    }

    double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(solution.getSolutionRepresentation());
    solution.setObjectiveFunctionValue(functionValue);

    return functionValue;
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

package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

public class InversionMutation extends HeuristicOperators implements HeuristicInterface {

  private final Random oRandom;

  public InversionMutation(Random oRandom) {
    super();
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dIntensityOfMutation);

    int[] deliveryLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = oSolution.getSolutionRepresentation().getNumberOfLocations();

    double cost = oSolution.getObjectiveFunctionValue();

    while (times > 0) {
      // randomly select two locations and the first location is visited before the second
      int first = oRandom.nextInt(numberOfDeliveryLocations);
      int second = oRandom.nextInt(numberOfDeliveryLocations);
      while (first > second) {
        second = oRandom.nextInt(numberOfDeliveryLocations);
      }

      cost -= getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);

      reverse(deliveryLocations, first, second);

      cost += getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);

      times--;
    }

    oSolution.setObjectiveFunctionValue(cost);
    return cost;
  }

  private void reverse(int[] array, int from, int to) {
    while (from < to) {
      swapLocations(array, from, to);
      from++;
      to--;
    }
  }

  @Override
  public boolean isCrossover() {
    return false;
  }

  @Override
  public boolean usesIntensityOfMutation() {
    return false;
  }

  @Override
  public boolean usesDepthOfSearch() {
    return true;
  }
}

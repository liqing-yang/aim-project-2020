package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

  private final Random oRandom;

  public AdjacentSwap(Random oRandom) {
    super();
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
    int times = (int) Math.pow(2, getTimes(intensityOfMutation));

    int[] deliveryLocations = solution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = solution.getSolutionRepresentation().getNumberOfLocations();

    double cost = solution.getObjectiveFunctionValue();

    while (times > 0) {
      int first = oRandom.nextInt(numberOfDeliveryLocations);
      int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;

      cost -= getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);
      if (first == numberOfDeliveryLocations - 1) {
        cost -= getCostBtwSuccAnd(deliveryLocations, first) + getCostBtwPredAnd(deliveryLocations, second);
      }
      
      swapLocations(deliveryLocations, first, second);

      cost += getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);
      if (first == numberOfDeliveryLocations - 1) {
        cost += getCostBtwSuccAnd(deliveryLocations, first) + getCostBtwPredAnd(deliveryLocations, second);
      }

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

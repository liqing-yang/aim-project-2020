package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

/**
 * @author Warren G. Jackson Performs adjacent swap, returning the first solution with strict
 *     improvement
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

  private final Random oRandom;

  public NextDescent(Random oRandom) {
    super();
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dDepthOfSearch);

    int[] deliveryLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = oSolution.getSolutionRepresentation().getNumberOfLocations();

    double cost = oSolution.getObjectiveFunctionValue();

    int start = oRandom.nextInt(numberOfDeliveryLocations);

    for (int i = 0; i < numberOfDeliveryLocations && times > 0; i++) {
      int first = (start + i) % numberOfDeliveryLocations;
      int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;

      double tempCost = cost;
      tempCost -= getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);
      if (first == numberOfDeliveryLocations - 1) {
        tempCost -= getCostBtwSuccAnd(deliveryLocations, first) + getCostBtwPredAnd(deliveryLocations, second);
      }

      swapLocations(deliveryLocations, first, second);

      tempCost += getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);
      if (first == numberOfDeliveryLocations - 1) {
        tempCost += getCostBtwSuccAnd(deliveryLocations, first) + getCostBtwPredAnd(deliveryLocations, second);
      }

      if (tempCost < cost) {
        cost = tempCost;
        times--;
      } else {
        swapLocations(deliveryLocations, first, second);
      }
    }

    oSolution.setObjectiveFunctionValue(cost);
    return cost;
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

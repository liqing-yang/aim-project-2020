package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.utilities.ArrayUtils;

/**
 * @author Warren G. Jackson Performs adjacent swap, returning the first solution with strict
 *     improvement
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

  public NextDescent(Random oRandom) {
    super(oRandom);
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dDepthOfSearch);

    int[] deliveryLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = oSolution.getSolutionRepresentation().getNumberOfLocations();

    double cost = oSolution.getObjectiveFunctionValue();

    int start = random.nextInt(numberOfDeliveryLocations);

    for (int i = 0; i < numberOfDeliveryLocations && times > 0; i++) {
      int first = (start + i) % numberOfDeliveryLocations;
      int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;

      double newCost = adjacentSwap(deliveryLocations, first, second, cost);

      if (newCost < cost) {
        cost = newCost;
        times--;
      } else {
        ArrayUtils.INSTANCE.swap(deliveryLocations, first, second);
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

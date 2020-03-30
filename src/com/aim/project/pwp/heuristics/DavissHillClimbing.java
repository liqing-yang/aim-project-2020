package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.utilities.RandomUtils;

/**
 * @author Warren G. Jackson Performs adjacent swap, returning the first solution with strict
 *     improvement
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {

  public DavissHillClimbing(Random oRandom) {
    super(oRandom);
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dDepthOfSearch);

    int[] deliveryLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = oSolution.getSolutionRepresentation().getNumberOfLocations();

    double cost = oSolution.getObjectiveFunctionValue();

    while (times > 0) {
      int[] perm = RandomUtils.INSTANCE.createRandomPermutation(numberOfDeliveryLocations, oRandom);

      for (int i = 0; i < numberOfDeliveryLocations; i++) {
        int first = perm[i];
        int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;

      	double newCost = adjacentSwap(deliveryLocations, first, second, cost);

      	if (newCost < cost) {
      		cost = newCost;
				} else {
      		swapLocations(deliveryLocations, first, second);
				}
			}

      times--;
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

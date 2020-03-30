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

  private final Random oRandom;

  public DavissHillClimbing(Random oRandom) {
    super();
    this.oRandom = oRandom;
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
      	int adjacent = perm[i] + 1 == numberOfDeliveryLocations ? 0 : perm[i] + 1;

      	double tempCost = cost;
        tempCost -= getCostBtwPredAnd(deliveryLocations, perm[i]) + getCostBtwSuccAnd(deliveryLocations, adjacent);
        if (perm[i] == numberOfDeliveryLocations - 1) {
          tempCost -= getCostBtwSuccAnd(deliveryLocations, perm[i]) + getCostBtwPredAnd(deliveryLocations, adjacent);
        }

      	swapLocations(deliveryLocations, perm[i], adjacent);

        tempCost += getCostBtwPredAnd(deliveryLocations, perm[i]) + getCostBtwSuccAnd(deliveryLocations, adjacent);
        if (perm[i] == numberOfDeliveryLocations - 1) {
          tempCost += getCostBtwSuccAnd(deliveryLocations, perm[i]) + getCostBtwPredAnd(deliveryLocations, adjacent);
        }

      	if (tempCost < cost) {
      		cost = tempCost;
				} else {
      		swapLocations(deliveryLocations, perm[i], adjacent);
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

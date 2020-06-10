package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.utilities.ArrayUtils;
import com.aim.project.pwp.utilities.PermutationUtils;

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
      // randomise the order in which the delivery locations are tried
      int[] perm = PermutationUtils.INSTANCE.createRandomPermutation(numberOfDeliveryLocations, random);

      for (int i = 0; i < numberOfDeliveryLocations; i++) {
        // chooses a location randomly and gets the location visited after it
        int first = perm[i];
        int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;

        // swap the locations and updates the cost using delta function
      	double newCost = adjacentSwap(deliveryLocations, first, second, cost);

      	if (newCost <= cost) {
      	  // accepts IE solutions
      		cost = newCost;
				} else {
      	  // rejects
          ArrayUtils.INSTANCE.swap(deliveryLocations, first, second);
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

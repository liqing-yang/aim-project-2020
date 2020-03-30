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

    double bestEval = oSolution.getObjectiveFunctionValue();

    while (times > 0) {
      int[] perm = RandomUtils.INSTANCE.createRandomPermutation(numberOfDeliveryLocations, oRandom);

      for (int i = 0; i < numberOfDeliveryLocations; i++) {
      	int adjacent = perm[i] + 1 == numberOfDeliveryLocations ? 0 : perm[i] + 1;
      	swapLocations(deliveryLocations, perm[i], adjacent);

      	double tmpEval = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());

      	if (tmpEval < bestEval) {
      		bestEval = tmpEval;
				} else {
      		swapLocations(deliveryLocations, perm[i], adjacent);
				}
			}

      times--;
    }

    oSolution.setObjectiveFunctionValue(bestEval);
    return bestEval;
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

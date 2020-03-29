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

    double bestEval = oSolution.getObjectiveFunctionValue();

    int start = oRandom.nextInt(numberOfDeliveryLocations);

    for (int i = 0; i < numberOfDeliveryLocations && times > 0; i++) {
      int first = (start + i) % numberOfDeliveryLocations;
      int second = first + 1 == numberOfDeliveryLocations ? 0 : first + 1;
      swapLocations(deliveryLocations, first, second);

      double tmpEval = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());

      if (tmpEval < bestEval) {
        bestEval = tmpEval;
        times--;
      } else {
        swapLocations(deliveryLocations, first, second);
      }
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

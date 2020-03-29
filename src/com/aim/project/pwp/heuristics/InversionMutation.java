package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

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

    while (times > 0) {
      // randomly select two locations and the first location is visited before the second
      int first = oRandom.nextInt(numberOfDeliveryLocations);
      int second = oRandom.nextInt(numberOfDeliveryLocations);
      while (first > second) {
        second = oRandom.nextInt(numberOfDeliveryLocations);
      }

      reverse(deliveryLocations, first, second);

      times--;
    }

    double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
    oSolution.setObjectiveFunctionValue(functionValue);

    return functionValue;
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

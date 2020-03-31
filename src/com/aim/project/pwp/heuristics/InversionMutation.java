package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.utilities.ArrayUtils;

public class InversionMutation extends HeuristicOperators implements HeuristicInterface {

  public InversionMutation(Random oRandom) {
    super(oRandom);
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dIntensityOfMutation);

    int[] deliveryLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
    int numberOfDeliveryLocations = oSolution.getSolutionRepresentation().getNumberOfLocations();

    double cost = oSolution.getObjectiveFunctionValue();

    while (times > 0) {
      // generates two different random numbers
      int number1 = oRandom.nextInt(numberOfDeliveryLocations);
      int number2 = oRandom.nextInt(numberOfDeliveryLocations);
      while (number1 == number2) {
        number2 = oRandom.nextInt(numberOfDeliveryLocations);
      }

      // makes the smaller the start and the larger the end
      int start = Math.min(number1, number2);
      int end = Math.max(number1, number2);

      // TODO
      cost -= getCostBtwPredAnd(deliveryLocations, start) + getCostBtwSuccAnd(deliveryLocations, end);

      ArrayUtils.INSTANCE.reverse(deliveryLocations, start, end);

      cost += getCostBtwPredAnd(deliveryLocations, start) + getCostBtwSuccAnd(deliveryLocations, end);

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

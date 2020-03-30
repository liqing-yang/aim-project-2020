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
      // randomly select two locations and the first location is visited before the second
      int number1 = oRandom.nextInt(numberOfDeliveryLocations);
      int number2 = oRandom.nextInt(numberOfDeliveryLocations);
      while (number1 == number2) {
        number2 = oRandom.nextInt(numberOfDeliveryLocations);
      }

      int first = Math.min(number1, number2);
      int second = Math.max(number1, number2);

      cost -= getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);

      ArrayUtils.INSTANCE.reverse(deliveryLocations, first, second);

      cost += getCostBtwPredAnd(deliveryLocations, first) + getCostBtwSuccAnd(deliveryLocations, second);

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

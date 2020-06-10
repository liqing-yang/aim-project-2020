package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;
import com.aim.project.pwp.utilities.PermutationUtils;

import java.util.*;

public class OX extends HeuristicOperators implements XOHeuristicInterface {
  public OX(Random random) {
    super(random);
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    return oSolution.getObjectiveFunctionValue();
  }

  @Override
  public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2, PWPSolutionInterface c, double dDepthOfSearch, double dIntensityOfMutation) {
    int times = getTimes(dIntensityOfMutation);

    int[] child1 = p1.getSolutionRepresentation().getSolutionRepresentation().clone();
    int[] child2 = p2.getSolutionRepresentation().getSolutionRepresentation().clone();

    int numberOfDeliveryLocations = p1.getSolutionRepresentation().getNumberOfLocations();

    while (times > 0) {
      // generates two random numbers and guarantees numbers 0 and (NumberOfDeliveryLocations + 1)
      // will not be chosen simultaneously
      int number1 = random.nextInt(numberOfDeliveryLocations);
      int number2 = random.nextInt(numberOfDeliveryLocations + 1);

      // makes the smaller the start and the larger the end
      int start = Math.min(number1, number2);
      int end = Math.max(number1, number2);

      // backs up the parents for later use
      int[] p1copy = child1.clone();
      int[] p2copy = child2.clone();

      // new children will override child1 and child2
      OXhelper(child1, p2copy, start, end);
      OXhelper(child2, p1copy, start, end);

      times--;
    }

    // chooses a child to be the candidate randomly
    int[] candidate = random.nextDouble() >= 0.5 ? child1 : child2;
    c.getSolutionRepresentation().setSolutionRepresentation(candidate);

    // calculates the new objective function value
    double functionValue = this.objectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
    c.setObjectiveFunctionValue(functionValue);

    return functionValue;
  }

  /**
   * This function learns from travelingSalesmanProblem/TSP.java#ox in chesc.jar, which uses inversion to improve the
   * efficiency of the algorithm.
   */
  private void OXhelper(int[] p1, int[] p2copy, int start, int end) {
    int[] inverseP2 = PermutationUtils.INSTANCE.inverse(p2copy);

    int totalLength = p1.length;
    int copyLength = end - start;

    // marks the locations already present in copy segment as -1
    for (int i = 0; i < copyLength; i++) {
      int locationInP1 = p1[start + i];
      int indexInP2 = inverseP2[locationInP1];
      p2copy[indexInP2] = -1;
    }

    int indexInP1 = end;
    for (int i = 0; i < totalLength; i++) {
      int locationInP2 = p2copy[(end + i) % totalLength];
      if (locationInP2 != -1) {
        p1[indexInP1++ % totalLength] = locationInP2;
      }
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
    return false;
  }
}

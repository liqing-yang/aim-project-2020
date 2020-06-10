package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;
import com.aim.project.pwp.utilities.ArrayUtils;

import java.util.Random;

public class CX extends HeuristicOperators implements XOHeuristicInterface {

  public CX(Random random) {
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
      // chooses a starting point randomly
      int index = random.nextInt(numberOfDeliveryLocations);

      // if the locations at the index are the same, a cycle is already completed
      if (child1[index] != child2[index]) {
        do {
          // backs up current index for later use
          int backup = index;

          // gets the location at current index in child2
          int locationInC2 = child2[index];

          // gets the index of the location in child1;
          // index will be -1 if the location is not found, which means if the location is copied to
          // child1 there will be no conflict and the cycle is completed.
          index = ArrayUtils.INSTANCE.find(child1, locationInC2);

          // swaps the locations at the backup index
          ArrayUtils.INSTANCE.swapBetweenArray(child1, child2, backup);
        } while (index != -1); // cycle completed
      }

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

  @Override
  public boolean isCrossover() {
    return true;
  }

  @Override
  public boolean usesIntensityOfMutation() {
    return true;
  }

  @Override
  public boolean usesDepthOfSearch() {
    return false;
  }
}

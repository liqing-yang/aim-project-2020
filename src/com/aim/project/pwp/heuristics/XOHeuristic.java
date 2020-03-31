package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

import java.util.*;

public abstract class XOHeuristic implements XOHeuristicInterface {

  protected final Random oRandom;

  protected ObjectiveFunctionInterface oObjectiveFunction;

  public XOHeuristic(Random oRandom) {
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
    return oSolution.getObjectiveFunctionValue();
  }

  @Override
  public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2, PWPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
    int times = 1 + (int) (intensityOfMutation * 10 / 2);

    int[] child1 = p1.getSolutionRepresentation().getSolutionRepresentation().clone();
    int[] child2 = p2.getSolutionRepresentation().getSolutionRepresentation().clone();

    int numberOfDeliveryLocations = p1.getSolutionRepresentation().getNumberOfLocations();

    while (times > 0) {
      // runs the corresponding crossover algorithm
      crossoverAlgorithm(child1, child2, numberOfDeliveryLocations);
      times--;
    }

    // chooses a child to be the candidate randomly
    int[] candidate = oRandom.nextDouble() >= 0.5 ? child1 : child2;
    c.getSolutionRepresentation().setSolutionRepresentation(candidate);

    // calculates the new objective function value
    double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
    c.setObjectiveFunctionValue(functionValue);

    return functionValue;
  }

  protected abstract void crossoverAlgorithm(int[] child1, int[] child2, int numberOfDeliveryLocations);

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

  @Override
  public void setObjectiveFunction(ObjectiveFunctionInterface f) {
    this.oObjectiveFunction = f;
  }
}

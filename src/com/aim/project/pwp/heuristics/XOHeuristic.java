package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

import java.util.*;
import java.util.stream.Collectors;

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

    int[] deliveryLocationsP1 = p1.getSolutionRepresentation().getSolutionRepresentation();
    int[] deliveryLocationsP2 = p2.getSolutionRepresentation().getSolutionRepresentation();

    int numberOfDeliveryLocations = c.getSolutionRepresentation().getNumberOfLocations();

    int[] child1 = deliveryLocationsP1.clone();
    int[] child2 = deliveryLocationsP2.clone();

    while (times > 0) {
      crossoverAlgorithm(child1, child2, numberOfDeliveryLocations);
      times--;
    }

    int[] candidate = oRandom.nextDouble() >= 0.5 ? child1 : child2;
    c.getSolutionRepresentation().setSolutionRepresentation(candidate);

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

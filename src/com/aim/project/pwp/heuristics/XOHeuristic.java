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

    List<Integer> parent1 = Arrays.stream(deliveryLocationsP1).boxed().collect(Collectors.toList());
    List<Integer> parent2 = Arrays.stream(deliveryLocationsP2).boxed().collect(Collectors.toList());

    while (times > 0) {
      crossoverAlgorithm(parent1, parent2, numberOfDeliveryLocations);
      times--;
    }

    List<Integer> candidate = oRandom.nextDouble() >= 0.5 ? parent1 : parent2;

    int[] newRepresentation = candidate.stream().mapToInt(i -> i).toArray();
    c.getSolutionRepresentation().setSolutionRepresentation(newRepresentation);

    double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
    c.setObjectiveFunctionValue(functionValue);

    return functionValue;
  }

  protected abstract void crossoverAlgorithm(List<Integer> parent1, List<Integer> parent2, int numberOfDeliveryLocations);

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

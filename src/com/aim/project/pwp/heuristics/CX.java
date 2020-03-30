package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

public class CX implements XOHeuristicInterface {

  private final Random oRandom;

  private ObjectiveFunctionInterface oObjectiveFunction;

  public CX(Random oRandom) {
    this.oRandom = oRandom;
  }

  @Override
  public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
    return solution.getObjectiveFunctionValue();
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
      List<Integer> cycleIndices = new ArrayList<>();

      int index = oRandom.nextInt(numberOfDeliveryLocations);

      do {
        cycleIndices.add(index);
        int locationInP2 = parent2.get(index);
        index = parent1.indexOf(locationInP2);
      } while (index != cycleIndices.get(0));

      for (int cycleIndex : cycleIndices) {
				swap(parent1, parent2, cycleIndex);
			}

      times--;
    }

    List<Integer> candidate = oRandom.nextDouble() >= 0.5 ? parent1 : parent2;

    int[] newRepresentation = candidate.stream().mapToInt(i -> i).toArray();
    c.getSolutionRepresentation().setSolutionRepresentation(newRepresentation);

    double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
    c.setObjectiveFunctionValue(functionValue);

    return c.getObjectiveFunctionValue();
  }

  private void swap(List<Integer> l1, List<Integer> l2, int index) {
  	Integer temp = l1.get(index);
  	l1.set(index, l2.get(index));
  	l2.set(index, temp);
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

  @Override
  public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
    this.oObjectiveFunction = oObjectiveFunction;
  }
}

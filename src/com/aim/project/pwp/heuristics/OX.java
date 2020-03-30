package com.aim.project.pwp.heuristics;

import java.util.*;
import java.util.stream.Collectors;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

public class OX implements XOHeuristicInterface {

	private final Random oRandom;

	private ObjectiveFunctionInterface oObjectiveFunction;

	public OX(Random oRandom) {
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
			// avoid choose the cut points at the start and end simultaneously
			int number1 = oRandom.nextInt(numberOfDeliveryLocations);
			int number2 = oRandom.nextInt(numberOfDeliveryLocations + 1);

			int start = Math.min(number1, number2);
			int end = Math.max(number1, number2);

			List<Integer> child1 = new ArrayList<>(parent1.subList(start, end));
			List<Integer> child2 = new ArrayList<>(parent2.subList(start, end));

			for (int i = 0; i < numberOfDeliveryLocations; i++) {
				int currentLocationIndex = (i + end) % numberOfDeliveryLocations;

				int currentLocationInP1 = parent1.get(currentLocationIndex);
				int currentLocationInP2 = parent2.get(currentLocationIndex);

				if (!child1.contains(currentLocationInP2)) {
					child1.add(currentLocationInP2);
				}

				if (!child2.contains(currentLocationInP1)) {
					child2.add(currentLocationInP1);
				}
			}

			Collections.rotate(child1, start);
			Collections.rotate(child2, start);

			Collections.copy(parent1, child1);
			Collections.copy(parent2, child2);

			times--;
		}

		List<Integer> candidate = oRandom.nextDouble() >= 0.5 ? parent1 : parent2;

		int[] newRepresentation = candidate.stream().mapToInt(i -> i).toArray();
		c.getSolutionRepresentation().setSolutionRepresentation(newRepresentation);

		double functionValue = this.oObjectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
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

	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.oObjectiveFunction = f;
	}
}

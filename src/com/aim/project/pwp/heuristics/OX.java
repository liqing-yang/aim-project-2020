package com.aim.project.pwp.heuristics;

import java.util.*;
import java.util.stream.Collectors;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

public class OX extends XOHeuristic {

	public OX(Random oRandom) {
		super(oRandom);
	}

	@Override
	protected void crossoverAlgorithm(List<Integer> parent1, List<Integer> parent2, int numberOfDeliveryLocations) {
		// avoid choosing the cut points at the start and end simultaneously
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
	}
}

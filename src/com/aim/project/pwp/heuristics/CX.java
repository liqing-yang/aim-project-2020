package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

public class CX extends XOHeuristic {

	public CX(Random oRandom) {
		super(oRandom);
	}

	@Override
	protected void crossoverAlgorithm(List<Integer> parent1, List<Integer> parent2, int numberOfDeliveryLocations) {
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
	}

	private void swap(List<Integer> l1, List<Integer> l2, int index) {
  	Integer temp = l1.get(index);
  	l1.set(index, l2.get(index));
  	l2.set(index, temp);
	}
}

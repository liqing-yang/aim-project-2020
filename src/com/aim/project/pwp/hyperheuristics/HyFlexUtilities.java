package com.aim.project.pwp.hyperheuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class HyFlexUtilities {

	public static int[] getHeuristicSetOfTypes(ProblemDomain problem, HeuristicType type, HeuristicType... types) {
		
		ArrayList<HeuristicType> h_types = new ArrayList<>();
		h_types.add(type);
		
		// add any optional heuristic types
		Arrays.stream(types).forEach( h_types::add );
		
		// create an array of all the heuristic IDs of the above-mentioned heuristic types.
		return h_types.stream()
					  .map( problem::getHeuristicsOfType )
					  .flatMapToInt( IntStream::of )
					  .toArray();
	}

	public static double applyHeuristicPair(ProblemDomain problem, HeuristicPair heuristicPair) {
		int first = heuristicPair.getFirst();
		int second = heuristicPair.getLast();

		if (first < 5) {
			problem.applyHeuristic(first, 0, 1);
		} else {
			problem.initialiseSolution(2);
			problem.applyHeuristic(first, 0, 2, 1);
		}

		return problem.applyHeuristic(second, 1, 1);
	}
}

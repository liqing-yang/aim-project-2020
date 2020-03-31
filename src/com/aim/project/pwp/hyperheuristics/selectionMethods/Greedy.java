package com.aim.project.pwp.hyperheuristics.selectionMethods;

import AbstractClasses.ProblemDomain;
import com.aim.project.pwp.hyperheuristics.HeuristicPair;
import com.aim.project.pwp.hyperheuristics.HyFlexUtilities;

import java.util.Random;

public class Greedy extends SelectionMethod {

  private ProblemDomain problem;

  public Greedy(HeuristicPair[] heuristicPairs, Random oRandom, ProblemDomain problem) {
    super(heuristicPairs, oRandom);

    this.problem = problem;
  }

  @Override
  public HeuristicPair selectHeuristics() {
    HeuristicPair bestPair = null;
    double bestCost = Double.MAX_VALUE;

    for (HeuristicPair pair : heuristicPairs) {
      double temp = calculateCost(pair);
      if (temp < bestCost) {
        bestPair = pair;
        bestCost = temp;
      }
    }
    return bestPair;
  }

  private double calculateCost(HeuristicPair heuristicPair) {
    return HyFlexUtilities.applyHeuristicPair(problem, heuristicPair);
  }
}

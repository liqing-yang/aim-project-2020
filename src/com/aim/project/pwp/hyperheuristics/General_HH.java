package com.aim.project.pwp.hyperheuristics;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.hyperheuristics.acceptanceMethods.*;
import com.aim.project.pwp.hyperheuristics.selectionMethods.*;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import java.util.Random;

public class General_HH extends HyperHeuristic {

  public General_HH(long seed) {
    super(seed);
  }

  @Override
  protected void solve(ProblemDomain problem) {
    int currentIndex = 0, candidateIndex = 1;

    problem.setMemorySize(3);
    problem.initialiseSolution(currentIndex);

    problem.setIntensityOfMutation(0.2);
    problem.setDepthOfSearch(0.2);

    double currentCost = problem.getFunctionValue(currentIndex);

    HeuristicPair[] heuristicPairs = generateHeuristicPairs(problem);

    SelectionInterface[] selections =
        new SelectionInterface[] {
          new SimpleRandom(heuristicPairs, rng),
          new RandomPermutationDescent(heuristicPairs, rng),
          new ReinforcementLearning(heuristicPairs, 5, 1, 10, rng),
          new Greedy(heuristicPairs, rng, problem)
        };
    SelectionInterface selection = selections[2];

    AcceptanceInterface[] acceptances = new AcceptanceInterface[] {new AllMoves(), new OnlyImproving(), new EqualAndImproving()};
    AcceptanceInterface acceptance = acceptances[1];

    long iteration = 0;
    System.out.println("Iteration\tf(s)\tf(s')\tAccept");

    while (!hasTimeExpired()) {
      HeuristicPair heuristicPair = selection.selectHeuristics();

      double candidateCost = HyFlexUtilities.applyHeuristicPair(problem, heuristicPair);

      boolean accept = acceptance.isAccepted(currentCost, candidateCost);
      selection.updateAcceptedLast(accept);
      if (accept) {
        problem.copySolution(candidateIndex, currentIndex);
        currentCost = candidateCost;
      }

      iteration++;
    }

    PWPSolutionInterface solution = ((AIM_PWP) problem).getBestSolution();
    SolutionPrinter printer = new SolutionPrinter("general_hh_out.csv");
    printer.printSolution(((AIM_PWP) problem).oInstance.getSolutionAsListOfLocations(solution));
    System.out.println(String.format("Total iterations = %d", iteration));
  }

  @Override
  public String toString() {
    return "slyly's HH";
  }

  public HeuristicPair[] generateHeuristicPairs(ProblemDomain problem) {
    int[] heuristicsUseIOM = problem.getHeuristicsThatUseIntensityOfMutation();
    int[] heuristicsUseDOS = problem.getHeuristicsThatUseDepthOfSearch();

    int pairsNumber = heuristicsUseIOM.length * heuristicsUseDOS.length;
    HeuristicPair[] heuristicPairs = new HeuristicPair[pairsNumber];

    int i = 0;
    for (int IOMs : heuristicsUseIOM) {
      for (int DOSs : heuristicsUseDOS) {
        heuristicPairs[i++] = new HeuristicPair(IOMs, DOSs);
      }
    }
    return heuristicPairs;
  }
}

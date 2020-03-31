package com.aim.project.pwp.hyperheuristics.selectionMethods;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;
import com.aim.project.pwp.utilities.PermutationUtils;

import java.util.Random;

public class RandomPermutationDescent extends SelectionMethod {

  private int[] permutation;
  private int currentIndex;

  private HeuristicPair currentHeuristicPair;

  public RandomPermutationDescent(HeuristicPair[] heuristicPairs, Random oRandom) {
    super(heuristicPairs, oRandom);

    generateNewPermutation();
    this.currentIndex = 0;
    this.currentHeuristicPair = this.heuristicPairs[permutation[0]];
  }

  @Override
  public HeuristicPair selectHeuristics() {
    if (!this.lastAccepted) {
      currentIndex = (currentIndex + 1) % permutation.length;
      if (currentIndex == 0) {
        generateNewPermutation();
      }
      currentHeuristicPair = heuristicPairs[permutation[currentIndex]];
    }
    return currentHeuristicPair;
  }

  private void generateNewPermutation() {
    this.permutation = PermutationUtils.INSTANCE.createRandomPermutation(this.pairsNumber, oRandom);
  }
}

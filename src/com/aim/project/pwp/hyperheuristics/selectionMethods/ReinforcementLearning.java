package com.aim.project.pwp.hyperheuristics.selectionMethods;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;

import java.util.Arrays;
import java.util.Random;

public class ReinforcementLearning extends SelectionMethod {

  private int[] heuristicScores;

  private final int lowerBound;
  private final int upperBound;

  private int lastAppliedPairIndex;

  public ReinforcementLearning(HeuristicPair[] heuristicPairs, int defaultScore, int lowerBound, int upperBound, Random oRandom) {
    super(heuristicPairs, oRandom);

    this.lowerBound = lowerBound;
    this.upperBound = upperBound;

    this.heuristicScores = new int[this.pairsNumber];
    Arrays.fill(heuristicScores, defaultScore);
  }

  @Override
  public HeuristicPair selectHeuristics() {
    int randomValue = oRandom.nextInt(getTotalScore());
    int cumulativeValue = 0;

    for (int i = 0; i < this.pairsNumber; i++) {
      int score = heuristicScores[i];
      if (randomValue < cumulativeValue + score) {
        lastAppliedPairIndex = i;
        return heuristicPairs[i];
      }
      cumulativeValue += score;
    }

    return null;
  }

  private int getScore(int index) {
    return heuristicScores[index];
  }

  private void incrementScore(int index) {
    heuristicScores[index] = Math.min(getScore(index) + 1, upperBound);
  }

  private void decrementScore(int index) {
    heuristicScores[index] = Math.max(getScore(index) - 1, lowerBound);
  }

  @Override
  public void updateAcceptedLast(boolean ifAcceptedLast) {
    super.updateAcceptedLast(ifAcceptedLast);

    if (this.lastAccepted) {
      incrementScore(lastAppliedPairIndex);
    } else {
      decrementScore(lastAppliedPairIndex);
    }
  }

  private int getTotalScore() {
    int sum = 0;
    for (int score : heuristicScores) {
      sum += score;
    }
    return sum;
  }
}

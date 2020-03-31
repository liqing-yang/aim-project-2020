package com.aim.project.pwp.hyperheuristics.selectionMethods;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;

import java.util.Random;

public class SimpleRandom extends SelectionMethod {

  public SimpleRandom(HeuristicPair[] heuristicPairs, Random oRandom) {
    super(heuristicPairs, oRandom);
  }

  @Override
  public HeuristicPair selectHeuristics() {
    return heuristicPairs[oRandom.nextInt(this.pairsNumber)];
  }
}

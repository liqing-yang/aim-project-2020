package com.aim.project.pwp.hyperheuristics.selectionMethods;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;

import java.util.Random;

public abstract class SelectionMethod implements SelectionInterface {

  protected Random oRandom;

  protected HeuristicPair[] heuristicPairs;
  protected int pairsNumber;

  protected boolean lastAccepted;

  public SelectionMethod(HeuristicPair[] heuristicPairs, Random oRandom) {
    this.heuristicPairs = heuristicPairs;
    this.pairsNumber = heuristicPairs.length;
    this.oRandom = oRandom;
    this.lastAccepted = true;
  }

  public void updateAcceptedLast(boolean ifAcceptedLast) {
    this.lastAccepted = ifAcceptedLast;
  }
}

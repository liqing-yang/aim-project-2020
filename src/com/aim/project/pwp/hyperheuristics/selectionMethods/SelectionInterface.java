package com.aim.project.pwp.hyperheuristics.selectionMethods;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;

public interface SelectionInterface {
  public HeuristicPair selectHeuristics();

  public void updateAcceptedLast(boolean ifAcceptedLast);
}

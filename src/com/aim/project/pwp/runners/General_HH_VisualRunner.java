package com.aim.project.pwp.runners;

import AbstractClasses.HyperHeuristic;
import com.aim.project.pwp.hyperheuristics.General_HH;

public class General_HH_VisualRunner extends HH_Runner_Visual {

  @Override
  protected HyperHeuristic getHyperHeuristic(long seed) {
    return new General_HH(seed);
  }

  public static void main(String[] args) {
    HH_Runner_Visual runner = new General_HH_VisualRunner();
    runner.run();
  }
}

package com.aim.project.pwp.hyperheuristics.acceptanceMethods;

public class OnlyImproving implements AcceptanceInterface {

  @Override
  public boolean isAccepted(double oldCost, double newCost) {
    return newCost < oldCost;
  }
}

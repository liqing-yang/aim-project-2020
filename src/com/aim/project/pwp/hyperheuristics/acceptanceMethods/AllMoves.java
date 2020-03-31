package com.aim.project.pwp.hyperheuristics.acceptanceMethods;

public class AllMoves implements AcceptanceInterface {

  @Override
  public boolean isAccepted(double oldCost, double newCost) {
    return true;
  }
}

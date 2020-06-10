package com.aim.project.pwp.hyperheuristics.acceptanceMethods;

public interface AcceptanceInterface {

  public boolean isAccepted(double oldCost, double newCost);
}

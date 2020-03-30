package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;

public class HeuristicOperators {

  protected ObjectiveFunctionInterface oObjectiveFunction;

  public HeuristicOperators() {}

  public void setObjectiveFunction(ObjectiveFunctionInterface f) {
    this.oObjectiveFunction = f;
  }

  protected int getTimes(double DOSorIOM) {
    return 1 + (int) (DOSorIOM * 10 / 2);
  }

  protected void swapLocations(int[] array, int iIndexA, int iIndexB) {
    int temp = array[iIndexA];
    array[iIndexA] = array[iIndexB];
    array[iIndexB] = temp;
  }

  protected double getCostBtwPredAnd(int[] locations, int index) {
    if (index == 0) {
      return oObjectiveFunction.getCostBetweenDepotAnd(locations[index]);
    } else {
      return oObjectiveFunction.getCost(locations[index], locations[index - 1]);
    }
  }

  protected double getCostBtwSuccAnd(int[] locations, int index) {
    if (index == locations.length - 1) {
      return oObjectiveFunction.getCostBetweenHomeAnd(locations[index]);
    } else {
      return oObjectiveFunction.getCost(locations[index], locations[index + 1]);
    }
  }

  protected double getCostBtwPredAndSuccOf(int[] locations, int index) {
    return getCostBtwPredAnd(locations, index) + getCostBtwSuccAnd(locations, index);
  }
}

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

  /**
   * TODO implement any common functionality here so that your heuristics can reuse them!
   *
   * <p>E.g. you may want to implement the swapping of two delivery locations here!
   */

  protected void swapLocations(int[] array, int iIndexA, int iIndexB) {
    int temp = array[iIndexA];
    array[iIndexA] = array[iIndexB];
    array[iIndexB] = temp;
	}
}

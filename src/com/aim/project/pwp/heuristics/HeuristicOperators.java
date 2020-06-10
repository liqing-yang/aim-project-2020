package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.utilities.ArrayUtils;

import java.util.Random;

public class HeuristicOperators {

  protected final Random random;

  protected ObjectiveFunctionInterface objectiveFunction;

  public HeuristicOperators(Random random) {
    this.random = random;
  }

  public void setObjectiveFunction(ObjectiveFunctionInterface f) {
    this.objectiveFunction = f;
  }

  protected double adjacentSwap(int[] locations, int iIndexA, int iIndexB, double cost) {
    int numberOfDeliveryLocations = locations.length;

    cost -= getCostBtwPredAnd(locations, iIndexA) + getCostBtwSuccAnd(locations, iIndexB);
    if (iIndexA == numberOfDeliveryLocations - 1) {
      cost -= getCostBtwSuccAnd(locations, iIndexA) + getCostBtwPredAnd(locations, iIndexB);
    }

    ArrayUtils.INSTANCE.swap(locations, iIndexA, iIndexB);

    cost += getCostBtwPredAnd(locations, iIndexA) + getCostBtwSuccAnd(locations, iIndexB);
    if (iIndexA == numberOfDeliveryLocations - 1) {
      cost += getCostBtwSuccAnd(locations, iIndexA) + getCostBtwPredAnd(locations, iIndexB);
    }

    return cost;
  }

  protected int getTimes(double DOSorIOM) {
    return 1 + (int) (DOSorIOM * 10 / 2);
  }

  protected double getCostBtwPredAnd(int[] locations, int index) {
    if (index == 0) {
      return objectiveFunction.getCostBetweenDepotAnd(locations[index]);
    } else {
      return objectiveFunction.getCost(locations[index], locations[index - 1]);
    }
  }

  protected double getCostBtwSuccAnd(int[] locations, int index) {
    if (index == locations.length - 1) {
      return objectiveFunction.getCostBetweenHomeAnd(locations[index]);
    } else {
      return objectiveFunction.getCost(locations[index], locations[index + 1]);
    }
  }

  protected double getCostBtwBothPredAndSuccOf(int[] locations, int index) {
    return getCostBtwPredAnd(locations, index) + getCostBtwSuccAnd(locations, index);
  }
}

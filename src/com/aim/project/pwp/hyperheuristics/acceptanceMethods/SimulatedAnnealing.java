package com.aim.project.pwp.hyperheuristics.acceptanceMethods;

import java.util.Random;

public class SimulatedAnnealing implements AcceptanceInterface{

  private Random random;

  private double currentTemperature;
  private double alpha = 0.93;

  public SimulatedAnnealing(Random random) {
    this.random = random;
  }

  @Override
  public boolean isAccepted(double oldCost, double newCost) {
    double delta = newCost - oldCost;
    double probability = boltzmannProbability(delta);

    advanceTemperature();

    return delta < 0 || random.nextDouble() < probability;
  }

  private double boltzmannProbability(double delta) {
    double exp = - delta / currentTemperature;
    return Math.exp(exp);
  }

  private void advanceTemperature() {
    this.currentTemperature *= this.alpha;
  }
}

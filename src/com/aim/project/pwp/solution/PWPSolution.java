package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPSolution implements PWPSolutionInterface {

  private SolutionRepresentationInterface oRepresentation;

  private double dObjectiveFunctionValue;

  public PWPSolution(
      SolutionRepresentationInterface representation, double objectiveFunctionValue) {
    this.oRepresentation = representation;
    this.dObjectiveFunctionValue = objectiveFunctionValue;
  }

  @Override
  public double getObjectiveFunctionValue() {
    return dObjectiveFunctionValue;
  }

  @Override
  public void setObjectiveFunctionValue(double objectiveFunctionValue) {
    this.dObjectiveFunctionValue = objectiveFunctionValue;
  }

  @Override
  public SolutionRepresentationInterface getSolutionRepresentation() {
    return this.oRepresentation;
  }

  @Override
  public PWPSolutionInterface clone() {
    return new PWPSolution(oRepresentation.clone(), dObjectiveFunctionValue);
  }

  /** @return The total number of locations in this instance (includes DEPOT and HOME). */
  @Override
  public int getNumberOfLocations() {
    return oRepresentation.getNumberOfLocations() + 2;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DEPOT -> ");

    int[] representation = this.oRepresentation.getSolutionRepresentation();
    for (int value : representation) {
      builder.append(value).append(" -> ");
    }

    builder.append("HOME");
    return builder.toString();
  }
}

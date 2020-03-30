package com.aim.project.pwp;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {

  private final PWPInstanceInterface oInstance;

  public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
    this.oInstance = oInstance;
  }

  @Override
  public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
    int[] deliveryLocations = oSolution.getSolutionRepresentation();
    int numberOfDeliveryLocations = deliveryLocations.length;

    double res = getCostBetweenDepotAnd(deliveryLocations[0]);
    for (int i = 0; i < numberOfDeliveryLocations - 1; i++) {
      res += getCost(deliveryLocations[i], deliveryLocations[i + 1]);
    }
    res += getCostBetweenHomeAnd(deliveryLocations[numberOfDeliveryLocations - 1]);

    return res;
  }

  @Override
  public double getCost(int iLocationA, int iLocationB) {
  	Location locationA = this.oInstance.getLocationForDelivery(iLocationA);
  	Location locationB = this.oInstance.getLocationForDelivery(iLocationB);
  	return getCostBetweenLocations(locationA, locationB);
	}

  @Override
  public double getCostBetweenDepotAnd(int iLocation) {
    Location depot = this.oInstance.getPostalDepot();
    Location delivery = this.oInstance.getLocationForDelivery(iLocation);
    return getCostBetweenLocations(depot, delivery);
  }

  @Override
  public double getCostBetweenHomeAnd(int iLocation) {
    Location home = this.oInstance.getHomeAddress();
    Location delivery = this.oInstance.getLocationForDelivery(iLocation);
    return getCostBetweenLocations(home, delivery);
  }

  private double getCostBetweenLocations(Location locationA, Location locationB) {
  	double dx = locationA.getX() - locationB.getX();
  	double dy = locationA.getY() - locationB.getY();
    return Math.sqrt(dx * dx + dy * dy);
  }
}

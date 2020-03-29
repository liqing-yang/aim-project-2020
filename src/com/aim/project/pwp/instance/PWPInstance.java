package com.aim.project.pwp.instance;

import java.util.*;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;
import com.aim.project.pwp.solution.PWPSolution;
import com.aim.project.pwp.utilities.RandomUtils;

public class PWPInstance implements PWPInstanceInterface {

  private final Location[] aoLocations;

  private final Location oPostalDepotLocation;

  private final Location oHomeAddressLocation;

  private final int iNumberOfLocations;

  private final Random oRandom;

  private ObjectiveFunctionInterface oObjectiveFunction = null;

  /**
   * @param numberOfLocations The TOTAL number of locations (including DEPOT and HOME).
   * @param aoLocations The delivery locations.
   * @param oPostalDepotLocation The DEPOT location.
   * @param oHomeAddressLocation The HOME location.
   * @param random The random number generator to use.
   */
  public PWPInstance(
      int numberOfLocations,
      Location[] aoLocations,
      Location oPostalDepotLocation,
      Location oHomeAddressLocation,
      Random random) {

    this.iNumberOfLocations = numberOfLocations;
    this.oRandom = random;
    this.aoLocations = aoLocations;
    this.oPostalDepotLocation = oPostalDepotLocation;
    this.oHomeAddressLocation = oHomeAddressLocation;
  }

  @Override
  public PWPSolution createSolution(InitialisationMode mode) {
    // create random permutation
    int[] permutation = RandomUtils.INSTANCE.createRandomPermutation(iNumberOfLocations - 2, oRandom);

    // create new solution
    SolutionRepresentation representation = new SolutionRepresentation(permutation);
    double functionValue = getPWPObjectiveFunction().getObjectiveFunctionValue(representation);

    return new PWPSolution(representation, functionValue);
  }

  @Override
  public ObjectiveFunctionInterface getPWPObjectiveFunction() {

    if (oObjectiveFunction == null) {
      this.oObjectiveFunction = new PWPObjectiveFunction(this);
    }

    return oObjectiveFunction;
  }

  @Override
  public int getNumberOfLocations() {

    return iNumberOfLocations;
  }

  @Override
  public Location getLocationForDelivery(int deliveryId) {

    return aoLocations[deliveryId];
  }

  @Override
  public Location getPostalDepot() {

    return this.oPostalDepotLocation;
  }

  @Override
  public Location getHomeAddress() {

    return this.oHomeAddressLocation;
  }

  @Override
  public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution) {
    ArrayList<Location> locations = new ArrayList<>();

    locations.add(oPostalDepotLocation);
    locations.addAll(Arrays.asList(aoLocations));
    locations.add(oHomeAddressLocation);

    return locations;
  }
}

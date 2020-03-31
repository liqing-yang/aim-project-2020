package com.aim.project.pwp;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.heuristics.*;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;
import com.aim.project.pwp.interfaces.*;

import AbstractClasses.ProblemDomain;

public class AIM_PWP extends ProblemDomain implements Visualisable {

  private String[] instanceFiles = {
    "square",
    "libraries-15",
    "carparks-40",
    "tramstops-85",
    "trafficsignals-446",
    "streetlights-35714"
  };

  private PWPSolutionInterface[] aoMemoryOfSolutions;

  public PWPSolutionInterface oBestSolution;

  public PWPInstanceInterface oInstance;

  private HeuristicInterface[] aoHeuristics;

  private ObjectiveFunctionInterface oObjectiveFunction;

  private final long seed;

  private final int NUMBER_OF_HEURISTICS = 7;
  private final int[] mutations = new int[] {0, 1, 2};
  private final int[] localSearches = new int[] {3, 4};
  private final int[] crossovers = new int[] {5, 6};
  private final int[] heuristicsUseDOS = new int[] {3, 4};
  private final int[] heuristicsUseIOM = new int[] {0, 1, 2, 5, 6};

  /**
   * TODO - set default memory size and create the array of low-level heuristics
   *
   * @param seed
   */
  public AIM_PWP(long seed) {
    super(seed); // set default memory size, rng, DOS and IOM
    this.seed = seed;

    aoHeuristics =
        new HeuristicInterface[] {
          new InversionMutation(rng),
          new AdjacentSwap(rng),
          new Reinsertion(rng),
          new NextDescent(rng),
          new DavissHillClimbing(rng),
          new OX(rng),
          new CX(rng)
        };
  }

  public PWPSolutionInterface getSolution(int index) {
    return this.aoMemoryOfSolutions[index];
  }

  public PWPSolutionInterface getBestSolution() {
    return this.oBestSolution;
  }

  @Override
  public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
    this.copySolution(currentIndex, candidateIndex);

    this.aoHeuristics[hIndex].apply(getSolution(candidateIndex), this.depthOfSearch, this.intensityOfMutation);

    verifyBestSolution(candidateIndex);

    return getFunctionValue(candidateIndex);
  }

  @Override
  public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
    if (!isCrossover(hIndex)) {
      return applyHeuristic(hIndex, parent1Index, candidateIndex);
    }

    this.copySolution(parent1Index, candidateIndex);

    XOHeuristicInterface XOHeuristic = (XOHeuristicInterface) this.aoHeuristics[hIndex];
    XOHeuristic.apply(
        getSolution(parent1Index),
        getSolution(parent2Index),
        getSolution(candidateIndex),
        this.depthOfSearch,
        this.intensityOfMutation);

    return getFunctionValue(candidateIndex);
  }

  private boolean isCrossover(int hIndex) {
    return Arrays.binarySearch(crossovers, hIndex) >= 0;
  }

  @Override
  public String bestSolutionToString() {
    return this.oBestSolution.toString();
  }

  /** Checks if the two solutions at the index {@code indexA} and {@code indexB} are the same. */
  @Override
  public boolean compareSolutions(int indexA, int indexB) {
    int[] r1 = this.aoMemoryOfSolutions[indexA].getSolutionRepresentation().getSolutionRepresentation();
    int[] r2 = this.aoMemoryOfSolutions[indexB].getSolutionRepresentation().getSolutionRepresentation();

    for (int i = 0; i < r1.length; i++) {
      if (r1[i] != r2[i]) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void copySolution(int source, int destination) {
    this.aoMemoryOfSolutions[destination] = this.aoMemoryOfSolutions[source].clone();
  }

  @Override
  public double getBestSolutionValue() {
    return this.oBestSolution.getObjectiveFunctionValue();
  }

  @Override
  public double getFunctionValue(int index) {
    return this.aoMemoryOfSolutions[index].getObjectiveFunctionValue();
  }

  @Override
  public int[] getHeuristicsOfType(HeuristicType type) {
    switch (type) {
      case MUTATION:
        return this.mutations;
      case LOCAL_SEARCH:
        return this.localSearches;
      case CROSSOVER:
        return this.crossovers;
      default:
        return null;
    }
  }

  @Override
  public int[] getHeuristicsThatUseDepthOfSearch() {
    return heuristicsUseDOS;
  }

  @Override
  public int[] getHeuristicsThatUseIntensityOfMutation() {
    return heuristicsUseIOM;
  }

  @Override
  public int getNumberOfHeuristics() {
    return NUMBER_OF_HEURISTICS;
  }

  @Override
  public int getNumberOfInstances() {
    return instanceFiles.length;
  }

  /**
   * This function initialises a solution at index {@code index} and tries to update the best
   * solution.
   */
  @Override
  public void initialiseSolution(int index) {
    this.aoMemoryOfSolutions[index] = oInstance.createSolution(InitialisationMode.RANDOM);
    this.verifyBestSolution(index);
  }

  /**
   * TODO
   *
   * <p>implement the instance reader that this method uses to correctly read in the PWP instance,
   * and set up the objective function.
   *
   * @param instanceId
   */
  @Override
  public void loadInstance(int instanceId) {

    String SEP = FileSystems.getDefault().getSeparator();
    String instanceName = "instances" + SEP + "pwp" + SEP + instanceFiles[instanceId] + ".pwp";

    Path path = Paths.get(instanceName);
    Random random = new Random(seed);
    PWPInstanceReader oPwpReader = new PWPInstanceReader();
    oInstance = oPwpReader.readPWPInstance(path, random);

    oObjectiveFunction = oInstance.getPWPObjectiveFunction();

    for (HeuristicInterface h : aoHeuristics) {
      h.setObjectiveFunction(oObjectiveFunction);
    }
  }

  /**
   * The function sets a new solution memory size.
   *
   * <p>If the memory size is INCREASED, then the existing solutions would be copied to the new
   * memory at the same indices. If the memory size is DECREASED, then the first {@code size}
   * solutions are copied to the new memory.
   *
   * @param size The size of new memory.
   */
  @Override
  public void setMemorySize(int size) {
    PWPSolutionInterface[] tempMemory = new PWPSolutionInterface[size];
    if (this.aoMemoryOfSolutions != null) {
      int srcPos = 0, destPos = 0;
      int bound = Math.min(size, aoMemoryOfSolutions.length);
      System.arraycopy(this.aoMemoryOfSolutions, srcPos, tempMemory, destPos, bound);
    }
    this.aoMemoryOfSolutions = tempMemory;
  }

  @Override
  public String solutionToString(int index) {
    return this.aoMemoryOfSolutions[index].toString();
  }

  @Override
  public String toString() {
    return "slyly2's G52AIM PWP";
  }

  /**
   * TODO
   *
   * @param index The index
   */
  private void verifyBestSolution(int index) {
    if (this.oBestSolution == null || getFunctionValue(index) < getBestSolutionValue()) {
      this.oBestSolution = getSolution(index);
    }
  }

  @Override
  public PWPInstanceInterface getLoadedInstance() {
    return this.oInstance;
  }

  @Override
  public Location[] getRouteOrderedByLocations() {
    int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
    Location[] route =
        Arrays.stream(city_ids)
            .boxed()
            .map(getLoadedInstance()::getLocationForDelivery)
            .toArray(Location[]::new);
    return route;
  }
}

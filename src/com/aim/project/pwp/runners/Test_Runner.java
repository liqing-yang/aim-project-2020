package com.aim.project.pwp.runners;

import AbstractClasses.HyperHeuristic;
import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.hyperheuristics.General_HH;
import com.aim.project.pwp.hyperheuristics.SR_IE_HH;

public class Test_Runner {

  final TestFrameConfig config;

  final int TOTAL_RUNS;
  final int[] INSTANCE_IDs;
  final long RUN_TIME;
  final long[] SEEDS;

  public Test_Runner(TestFrameConfig config) {
    this.config = config;

    this.TOTAL_RUNS = config.getTotalRuns();
    this.INSTANCE_IDs = config.getInstanceIDs();
    this.SEEDS = config.getSeeds();
    this.RUN_TIME = config.getRunTime();
  }

  public void runTests() {

    String hyperHeuristicName;

    for (int instanceID : INSTANCE_IDs) {

      for (int run = 0; run < TOTAL_RUNS; ++run) {

        long seed = SEEDS[run];

        HyperHeuristic hh = null;
        switch (config.MODE) {
          case SR_IE:
            hh = new SR_IE_HH(seed);
            break;
          case CUSTOM:
            hh = new General_HH(seed);
            break;
          default:
            System.err.println("Invalid Mode");
            System.exit(0);
        }

        AIM_PWP problem = new AIM_PWP(seed);
        problem.loadInstance(instanceID);

        hh.setTimeLimit(RUN_TIME);
        hh.loadProblemDomain(problem);
        hh.run();

        hyperHeuristicName = hh.toString();
        System.out.print("Instance ID: " + instanceID);
        System.out.print("; Trial: " + run);
        System.out.print("; HH: " + hyperHeuristicName);
        System.out.println("; f(s_{best}) = " + hh.getBestSolutionValue());
      }
    }
  }

  public static void main(String[] args) {
    new Test_Runner(new TestFrameConfig()).runTests();
  }
}

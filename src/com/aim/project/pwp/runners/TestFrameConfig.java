package com.aim.project.pwp.runners;

import java.util.Random;

public class TestFrameConfig {

  public enum Mode {
    SR_IE,
    CUSTOM;
  }

  protected final Mode MODE = Mode.CUSTOM;

  protected final long[] SEEDS;

  protected final int TOTAL_RUNS = 11;
//  protected final int TOTAL_RUNS = 5;

  protected final long MILLISECONDS_IN_TEN_MINUTES = 600_000;

  protected final long RUN_TIME_IN_SECONDS = 60;

  protected final int[] INSTANCE_IDs = {2, 3, 4};
//  protected final int[] INSTANCE_IDs = {3};

  public TestFrameConfig() {
    Random random = new Random(20000907L);
    SEEDS = new long[TOTAL_RUNS];

    for (int i = 0; i < TOTAL_RUNS; ++i) {
      SEEDS[i] = random.nextLong();
    }
  }

  public int getTotalRuns() {
    return this.TOTAL_RUNS;
  }

  public long[] getSeeds() {
    return this.SEEDS;
  }

  public int[] getInstanceIDs() {
    return this.INSTANCE_IDs;
  }

  public long getRunTime() {
    return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
  }
}

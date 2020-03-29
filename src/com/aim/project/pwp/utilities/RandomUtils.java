package com.aim.project.pwp.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum RandomUtils {
  INSTANCE;

  public int[] createRandomPermutation(int n, Random oRandom) {
    List<Integer> permutation = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      permutation.add(i);
    }
    Collections.shuffle(permutation, oRandom);
    return permutation.stream().mapToInt(i -> i).toArray();
  }
}

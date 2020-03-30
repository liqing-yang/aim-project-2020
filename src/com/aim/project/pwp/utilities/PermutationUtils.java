package com.aim.project.pwp.utilities;

public enum PermutationUtils {
  INSTANCE;

  public int[] inverse(int[] permutation) {
    int length = permutation.length;
    int[] res = new int[length];

    for (int i = 0; i < length; i++) {
      res[permutation[i]] = i;
    }

    return res;
  }
}

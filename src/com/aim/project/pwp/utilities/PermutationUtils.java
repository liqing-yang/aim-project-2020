package com.aim.project.pwp.utilities;

import java.util.Random;

/**
 * The {@code PermutationUtils} class contains utilities about permutation.
 *
 * <p><b>Note:</b> this class is a singleton with {@link Enum}.
 */
public enum PermutationUtils {
  INSTANCE;

  /**
   * Creates a new permutation of 0 to (N - 1) randomly.
   *
   * @param N The bound (exclude) of the array.
   * @param oRandom The random number generator.
   * @return The random permutation.
   */
  public int[] createRandomPermutation(int N, Random oRandom) {
    int[] permutation = new int[N];
    for (int i = 0; i < N; i++) {
      permutation[i] = i;
    }

    for (int i = 0; i < N; i++) {
      int index = oRandom.nextInt(N);
      int temp = permutation[i];
      permutation[i] = permutation[index];
      permutation[index] = temp;
    }

    return permutation;
  }

  /**
   * Return the inversion of the permutation.
   *
   * <p>Reminder: An inverse permutation is a permutation in which each number and the number of the
   * place which it occupies are exchanged.
   *
   * @param permutation The original permutation.
   * @return The inverse permutation.
   */
  public int[] inverse(int[] permutation) {
    int length = permutation.length;
    int[] res = new int[length];

    for (int i = 0; i < length; i++) {
      res[permutation[i]] = i;
    }

    return res;
  }
}

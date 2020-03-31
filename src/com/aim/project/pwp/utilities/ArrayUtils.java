package com.aim.project.pwp.utilities;

/**
 * The {@code ArrayUtils} class contains utilities about array.
 *
 * <p><b>Note:</b> this class is a singleton with {@link Enum}.
 */
public enum ArrayUtils {
  INSTANCE;

  /**
   * TODO
   *
   * @param array The search space.
   * @param target The target TODO
   * @return {@code true} if the array contains the target; {@code false} otherwise.
   */
  public boolean contains(int[] array, int target) {
    return find(array, target) >= 0;
  }

  /**
   * TODO
   *
   * @param array The search space.
   * @param target The target needs to be found.
   * @return The index of the target if the array contains the target; -1 otherwise.
   */
  public int find(int[] array, int target) {
    int length = array.length;
    for (int i = 0; i < length; i++) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  public void swapBetweenArray(int[] array1, int[] array2, int index) {
    int temp = array1[index];
    array1[index] = array2[index];
    array2[index] = temp;
  }

  public void swap(int[] array, int index1, int index2) {
    int temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }

  public void reverse(int[] array, int from, int to) {
    while (from < to) {
      swap(array, from, to);
      from++;
      to--;
    }
  }

  public void moveByOffset(int[] array, int startPos, int length, int offset) {
    System.arraycopy(array, startPos, array, startPos + offset, length);
  }
}

package com.aim.project.pwp.utilities;

public enum ArrayUtils {
  INSTANCE;

  public boolean contains(int[] array, int target) {
    return find(array, target) >= 0;
  }

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
}

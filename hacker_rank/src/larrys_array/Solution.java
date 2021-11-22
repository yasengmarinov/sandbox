package larrys_array;

import java.util.Scanner;

public class Solution {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int t = scanner.nextInt();

    for (int i = 0; i < t; i++) {
      runInstance(scanner);
    }
  }

  private static void runInstance(Scanner scanner) {
    int n = scanner.nextInt();
    int[] array = new int[n];

    for (int i = 0; i < n; i++) {
      array[i] = scanner.nextInt();
    }

    int i = 0;
    while (swapsPossible(array, i)) {
      if (array[i] > array[i + 1]) {
        swap(array, i);
        i = 0;
      } else {
        i++;
      }
    }

    if (array[array.length - 2] < array[array.length - 1]) {
      System.out.println("YES");
    } else {
      System.out.println("NO");
    }
  }

  private static boolean swapsPossible(int[] array, int i) {
    if (i > array.length - 2) return false;
    if (i == array.length - 2 && array[i + 1] > array[i - 1]) return false;

    return true;
  }

  private static void swap(int[] array, int i) {
    if (i == array.length - 2) {
      i--;
    }

    int a = array[i];
    int b = array[i + 1];
    int c = array[i + 2];

    array[i] = b;
    array[i + 1] = c;
    array[i + 2] = a;
  }
}

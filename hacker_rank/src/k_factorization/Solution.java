package k_factorization;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int k = scanner.nextInt();

    int[] multipliers = new int[k];
    for (int i = 0; i < k; i++) {
      multipliers[i] = scanner.nextInt();
    }
    Arrays.sort(multipliers);

    List<Integer> result = findSmallestPath(n, multipliers);
    if (result == null) {
      System.out.println(-1);
    } else {
      for (Integer number : result) {
        System.out.print(number + " ");
      }
    }
  }

  private static List<Integer> findSmallestPath(int n, int[] multipliers) {
    return getNextMultiplier(n, multipliers);
  }

  private static List<Integer> getNextMultiplier(int n, int[] multipliers) {
    if (n == 1) {
      List toReturn = new LinkedList();
      toReturn.add(1);
      return toReturn;
    }

    for (int i = multipliers.length - 1; i >= 0; i--) {
      if (n >= multipliers[i] && n % multipliers[i] == 0) {
        List<Integer> toReturn = getNextMultiplier(n / multipliers[i], multipliers);
        if (toReturn != null) {
          toReturn.add(n);
          return toReturn;
        }
      }
    }

    return null;
  }
}

package sherlock_and_minmax;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

  public static final int PERCENT_OF_BORDERS = 20;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];

    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    Arrays.sort(a);

    int p = scanner.nextInt();
    int q = scanner.nextInt();

    int currentMiniMax;
    int longestDistance;

    // Calculate the value for the first possible offer
    currentMiniMax = calculateDistance(a, p);
    longestDistance = p;

    for (int i = 0; i < a.length - 1; i++) {
      int med = (int) Math.floor((a[i] + a[i + 1]) / 2);
      if (med >= p && med <= q) {
        int offerMiniMax = med - a[i];
        if (offerMiniMax > currentMiniMax) {
          currentMiniMax = offerMiniMax;
          longestDistance = med;
        }
      }
    }

    // If all medians are not in the range then the searched value should be p or q
    int offerMiniMax = calculateDistance(a, q);
    if (offerMiniMax > currentMiniMax) {
      currentMiniMax = offerMiniMax;
      longestDistance = q;
    }

    System.out.println(longestDistance);
  }

  private static int calculateDistance(int[] a, int p) {
    int minDistance = -1;
    if (p <= a[0]) {
      minDistance = a[0] - p;
    } else if (p >= a[a.length - 1]) {
      minDistance = p - a[a.length - 1];
    } else {
      for (int i = 0; i < a.length - 1; i++) {
        if (a[i] <= p && p <= a[i + 1]) {
          minDistance = Math.min(p - a[i], a[i + 1] - p);
          break;
        }
      }
    }

    return minDistance;
  }
}

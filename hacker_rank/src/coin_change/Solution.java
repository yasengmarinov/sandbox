package coin_change;

import java.util.Scanner;

public class Solution {

  static long getWays(int n, int[] c) {
    // Rows - number of available coins, columns - change amount
    long[][] solutionMatrix = new long[c.length + 1][n + 1];

    for (int i = 0; i < solutionMatrix.length; i++) {
      solutionMatrix[i][0] = 1;
    }
    for (int i = 1; i < solutionMatrix[0].length; i++) {
      solutionMatrix[0][i] = 0;
    }
    for (int coin = 1; coin < solutionMatrix.length; coin++) {
      for (int change = 1; change < solutionMatrix[0].length; change++) {
        solutionMatrix[coin][change] = solutionMatrix[coin - 1][change];
        if (change >= c[coin - 1]) {
          solutionMatrix[coin][change] += solutionMatrix[coin][change - c[coin - 1]];
        }
      }
    }
    return solutionMatrix[solutionMatrix.length - 1][solutionMatrix[0].length - 1];
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int m = in.nextInt();
    int[] c = new int[m];
    for (int c_i = 0; c_i < m; c_i++) {
      c[c_i] = in.nextInt();
    }
    // Print the number of ways of making change for 'n' units using coins having the values given
    // by 'c'
    long ways = getWays(n, c);
    System.out.println(ways);
  }
}

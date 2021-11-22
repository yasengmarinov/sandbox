package brear_and_steady_gene;

import java.io.IOException;

/**
 * @author Yasen Marinov
 * @since 31/07/2021
 */
class Result {

  /*
   * Complete the 'steadyGene' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts STRING gene as parameter.
   */

  public static int steadyGene(String gene) {
    // Write your code here

    int[] geneCount = new int[4];
    short[] geneArray = new short[gene.length()];

    for (int i = 0; i < gene.length(); i++) {
      char c = gene.charAt(i);
      switch (c) {
        case 'C':
          geneCount[0]++;
          geneArray[i] = 0;
          break;
        case 'G':
          geneCount[1]++;
          geneArray[i] = 1;
          break;
        case 'A':
          geneCount[2]++;
          geneArray[i] = 2;
          break;
        case 'T':
          geneCount[3]++;
          geneArray[i] = 3;
          break;
      }
    }

    int expectedNumPerGene = gene.length() / 4;

    if (isValid(geneCount, expectedNumPerGene)) {
      return 0;
    }

    int start = 0;
    int end = 0;
    int minStrLength = Integer.MAX_VALUE;

    while (start < geneArray.length && end < geneArray.length) {
      if (isValid(geneCount, expectedNumPerGene)) {
        minStrLength = Math.min(end - start, minStrLength);
        geneCount[geneArray[start++]]++;
      } else {
        geneCount[geneArray[end++]]--;
      }
    }
    return minStrLength;
  }

  private static boolean isValid(int[] geneCount, int expectedNumPerGene) {
    for (int cnt : geneCount) {
      if (cnt > expectedNumPerGene) {
        return false;
      }
    }
    return true;
  }
}

public class Solution {

  public static void main(String[] args) throws IOException {
    System.out.println(Result.steadyGene("TGATGCCGTCCCCTCAACTTGAGTGCTCCTAATGCGTTGC"));
  }
}

package short_palindrome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

/**
 * @author Yasen Marinov
 * @since 11/08/2021
 */
public class Solution {

  /*
   * Complete the 'shortPalindrome' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts STRING s as parameter.
   */

  public static int shortPalindrome(String string) {
    int lettersCount = 26;

    int[] c1 = new int[lettersCount];
    int[][] c2 = new int[lettersCount][lettersCount];
    int[][][] c3 = new int[lettersCount][lettersCount][lettersCount];
    int[][][][] c4 = new int[lettersCount][lettersCount][lettersCount][lettersCount];

    int mod = (int) Math.pow(10, 9) + 7;

    for (char start : string.toCharArray()) {
      int idx = start - 'a';

      for (int i = 0; i < lettersCount; i++) {
        c4[idx][i][i][idx] += c3[idx][i][i];
        c4[idx][i][i][idx] = c4[idx][i][i][idx] % mod;

        c3[i][idx][idx] += c2[i][idx];
        c3[i][idx][idx] = c3[i][idx][idx] % mod;

        c2[i][idx] += c1[i];
        c2[i][idx] = c2[i][idx] % mod;
      }

      c1[idx]++;
    }

    int res = 0;

    for (int i = 0; i < lettersCount; i++) {
      for (int j = 0; j < lettersCount; j++) {
        for (int k = 0; k < lettersCount; k++) {
          for (int l = 0; l < lettersCount; l++) {
            res += c4[i][j][k][l];
            res = res % mod;
          }
        }
      }
    }

    return res;
  }
  //  public static int shortPalindrome(String string) {
  //
  //    int[] letterCount = new int['z' - 'a' + 1];
  //
  //    int res = 0;
  //    int mod = (int) Math.pow(10, 9) + 7;
  //
  //    for (int i = 0; i < string.length() - 3; i++) {
  //      int letterStart = string.charAt(i);
  //
  //      for (int j = i + 1; j < string.length(); j++) {
  //        if (j >= i + 3 && string.charAt(j) == letterStart) {
  //          res += findParis(letterCount);
  //          res = res % mod;
  //        }
  //        letterCount[string.charAt(j) % 'a']++;
  //      }
  //
  //      for (int j = 0; j < letterCount.length; j++) {
  //        letterCount[j] = 0;
  //      }
  //    }
  //    return res;
  //  }
  //
  //  private static int findParis(int[] letterCount) {
  //    int res = 0;
  //    for (int count : letterCount) {
  //      res += count * (count - 1) / 2;
  //    }
  //
  //    return res;
  //  }

  //  public static int shortPalindrome(String s) {
  //    // Write your code here
  //
  //    Map<Character, SortedSet<Integer>> charSet = new HashMap<>();
  //    for (int i = 0; i < s.length(); i++) {
  //      charSet.computeIfAbsent(s.charAt(i), key -> new TreeSet<>()).add(i);
  //    }
  //
  //    int res = 0;
  //
  //    int mod = (int) Math.pow(10, 9) + 7;
  //    int[][] cache = new int[s.length()][s.length()];
  //    for (int i = 0; i < s.length(); i++) {
  //      for (int j = 0; j < s.length(); j++) {
  //        cache[i][j] = -1;
  //      }
  //    }
  //
  //    for (int a = 0; a < s.length() - 2; a++) {
  //      char aChar = s.charAt(a);
  //      SortedSet<Integer> aPalindroms = charSet.get(aChar);
  //      for (int d : aPalindroms) {
  //        if (a >= d) {
  //          continue;
  //        }
  //        int cachedResult = 0;
  //        if (cache[a + 1][d] > 0) {
  //          cachedResult = cache[a + 1][d];
  //        } else {
  //          for (SortedSet<Integer> set : charSet.values()) {
  //            int palBCount = set.subSet(a + 1, d).size();
  //            cachedResult += palBCount * (palBCount - 1) / 2;
  //          }
  //        }
  //        res += cachedResult;
  //        res = res % mod;
  //      }
  //    }
  //
  //    return res;
  //  }

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new StringReader("kkkkkkz"));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String s = bufferedReader.readLine();

    int result = shortPalindrome(s);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

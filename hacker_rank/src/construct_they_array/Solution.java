package construct_they_array;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

/**
 * @author Yasen Marinov
 * @since 10/10/2021
 */
public class Solution {

  public static long countArray(int n, int k, int x) {
    // Return the number of ways to fill in the array.
    long[][] results = new long[2][k + 1];
    results[1][1] = 1;

    int mod = 1000000007;
    for (int i = 2; i <= n; i++) {
      int current = i % 2;
      int prev = (i + 1) % 2;
      results[current][1] = results[prev][2] * (k - 1) % mod;
      results[current][2] = (results[prev][1] + results[prev][2] * (k - 2)) % mod;
    }

    return results[n % 2][x >= 2 ? 2 : 1];
  }

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new StringReader("17048 14319 1"));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int k = Integer.parseInt(firstMultipleInput[1]);

    int x = Integer.parseInt(firstMultipleInput[2]);

    long answer = countArray(n, k, x);

    bufferedWriter.write(String.valueOf(answer));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

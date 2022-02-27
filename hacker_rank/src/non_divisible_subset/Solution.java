package non_divisible_subset;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;
/**
 * @author Yasen Marinov
 * @since 27/12/2021
 */
public class Solution {

  /*
   * Complete the 'nonDivisibleSubset' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER k
   *  2. INTEGER_ARRAY s
   */

  public static int nonDivisibleSubset(int k, List<Integer> s) {
    // Write your code here
    int[] sizes = new int[k];

    for (Integer integer : s) {
      sizes[integer % k]++;
    }

    int subsetSize = 0;

    for (int i = 0; i <= k / 2; i++) {
      if (i == 0 || (k % 2 == 0 && i == k / 2)) {
        if (sizes[i] != 0) {
          subsetSize++;
        }
        continue;
      }

      subsetSize += Math.max(sizes[i], sizes[k - i]);
    }

    return subsetSize;
  }

  public static void main(String[] args) throws IOException {
    String input = "4 3\n" + "1 7 2 4";

    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int k = Integer.parseInt(firstMultipleInput[1]);

    List<Integer> s =
        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

    int result = nonDivisibleSubset(k, s);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

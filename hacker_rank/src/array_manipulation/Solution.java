package array_manipulation;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 21/12/2021
 */
public class Solution {

  /*
   * Complete the 'arrayManipulation' function below.
   *
   * The function is expected to return a LONG_INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER n
   *  2. 2D_INTEGER_ARRAY queries
   */

  public static long arrayManipulation(int n, List<List<Integer>> queries) {
    // Write your code here
    int[] array = new int[n + 1];
    for (List<Integer> query : queries) {
      int p = query.get(0);
      int q = query.get(1);
      int k = query.get(2);

      array[p] += query.get(2);
      if (q < array.length - 1) {
        array[q + 1] -= k;
      }
    }

    long max = 0;
    long current = 0;

    for (int i = 0; i < array.length; i++) {
      current += array[i];
      max = Math.max(max, current);
    }

    return max;
  }

  public static void main(String[] args) throws IOException {
    String input = "5 3\n" + "1 2 100\n" + "2 5 100\n" + "3 4 100";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int m = Integer.parseInt(firstMultipleInput[1]);

    List<List<Integer>> queries = new ArrayList<>();

    IntStream.range(0, m)
        .forEach(
            i -> {
              try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList()));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    long result = arrayManipulation(n, queries);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

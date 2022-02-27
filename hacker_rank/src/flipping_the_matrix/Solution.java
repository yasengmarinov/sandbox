package flipping_the_matrix;

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
 * @since 08/12/2021
 */
public class Solution {

  /*
   * Complete the 'flippingMatrix' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts 2D_INTEGER_ARRAY matrix as parameter.
   */

  public static int flippingMatrix(List<List<Integer>> matrix) {
    // Write your code here

    int[][] mat = new int[matrix.size()][matrix.size()];
    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < matrix.size(); j++) {
        mat[i][j] = matrix.get(i).get(j);
      }
    }

    int sum = 0;

    for (int i = 0; i < mat.length / 2; i++) {
      for (int j = 0; j < mat.length / 2; j++) {
        int bestOffer = Integer.MIN_VALUE;
        bestOffer = Math.max(bestOffer, mat[i][j]);
        bestOffer = Math.max(bestOffer, mat[i][mat.length - 1 - j]);
        bestOffer = Math.max(bestOffer, mat[mat.length - 1 - i][j]);
        bestOffer = Math.max(bestOffer, mat[mat.length - 1 - i][mat.length - 1 - j]);

        sum += bestOffer;
      }
    }

    return sum;
  }

  public static void main(String[] args) throws IOException {
    String input =
        "1\n" + "2\n" + "112 42 83 119\n" + "56 125 56 49\n" + "15 78 101 43\n" + "62 98 114 108";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int q = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, q)
        .forEach(
            qItr -> {
              try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<List<Integer>> matrix = new ArrayList<>();

                IntStream.range(0, 2 * n)
                    .forEach(
                        i -> {
                          try {
                            matrix.add(
                                Stream.of(
                                        bufferedReader
                                            .readLine()
                                            .replaceAll("\\s+$", "")
                                            .split(" "))
                                    .map(Integer::parseInt)
                                    .collect(toList()));
                          } catch (IOException ex) {
                            throw new RuntimeException(ex);
                          }
                        });

                int result = flippingMatrix(matrix);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();
    bufferedWriter.close();
  }
}

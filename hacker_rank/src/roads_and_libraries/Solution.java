package roads_and_libraries;

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
 * @since 28/12/2021
 */
public class Solution {

  /*
   * Complete the 'roadsAndLibraries' function below.
   *
   * The function is expected to return a LONG_INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER n
   *  2. INTEGER c_lib
   *  3. INTEGER c_road
   *  4. 2D_INTEGER_ARRAY cities
   */

  public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
    // Write your code here

    if (c_lib <= c_road) {
      return (long) c_lib * n;
    }

    int[] clusters = new int[n + 1];
    int clusterSize = n;
    for (int i = 1; i < clusters.length; i++) {
      clusters[i] = i;
    }

    for (List<Integer> city : cities) {
      int cityA = city.get(0);
      int cityB = city.get(1);

      int rootA = getRoot(clusters, cityA);
      int rootB = getRoot(clusters, cityB);

      if (rootA != rootB) {
        clusters[rootA] = rootB;
        clusterSize--;
      }
    }

    return (long) clusterSize * c_lib + (long) (n - clusterSize) * c_road;
  }

  private static int getRoot(int[] clusters, int city) {
    int offer = clusters[city];

    while (offer != clusters[offer]) {
      offer = clusters[offer];
    }

    return offer;
  }

  public static void main(String[] args) throws IOException {
    String input = "1\n" + "6 6 2 5\n" + "1 3\n" + "3 4\n" + "2 4\n" + "1 2\n" + "2 3\n" + "5 6";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int q = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, q)
        .forEach(
            qItr -> {
              try {
                String[] firstMultipleInput =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int c_lib = Integer.parseInt(firstMultipleInput[2]);

                int c_road = Integer.parseInt(firstMultipleInput[3]);

                List<List<Integer>> cities = new ArrayList<>();

                IntStream.range(0, m)
                    .forEach(
                        i -> {
                          try {
                            cities.add(
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

                long result = roadsAndLibraries(n, c_lib, c_road, cities);

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

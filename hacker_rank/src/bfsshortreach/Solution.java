package bfsshortreach;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 30/01/2022
 */
public class Solution {

  /*
   * Complete the 'bfs' function below.
   *
   * The function is expected to return an INTEGER_ARRAY.
   * The function accepts following parameters:
   *  1. INTEGER n
   *  2. INTEGER m
   *  3. 2D_INTEGER_ARRAY edges
   *  4. INTEGER s
   */

  public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
    // Write your code here

    int[] distances = new int[n + 1];
    for (int i = 0; i < distances.length; i++) {
      distances[i] = -1;
    }

    Queue<Integer> queue = new LinkedList<>();
    queue.add(s);
    distances[s] = 0;

    while (!queue.isEmpty()) {
      int current = queue.poll();

      Iterator<List<Integer>> iterator = edges.iterator();

      while (iterator.hasNext()) {
        List<Integer> edge = iterator.next();
        Integer connected = null;

        if (edge.get(0) == current) {
          connected = edge.get(1);
        } else if (edge.get(1) == current) {
          connected = edge.get(0);
        }
        if (connected == null) {
          continue;
        }
        queue.add(connected);
        iterator.remove();

        if (distances[connected] == -1) {
          distances[connected] = distances[current] + 6;
        } else {
          distances[connected] = Math.min(distances[connected], distances[current] + 6);
        }
      }
    }

    List<Integer> result = new ArrayList<>(n - 1);
    for (int i = 1; i < distances.length; i++) {
      if (i != s) {
        result.add(distances[i]);
      }
    }

    return result;
  }

  public static void main(String[] args) throws IOException {
    String input = "2\n" + "4 2\n" + "1 2\n" + "1 3\n" + "1\n" + "3 1\n" + "2 3\n" + "2";
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

                List<List<Integer>> edges = new ArrayList<>();

                IntStream.range(0, m)
                    .forEach(
                        i -> {
                          try {
                            edges.add(
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

                int s = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> result = bfs(n, m, edges, s);

                bufferedWriter.write(
                    result.stream().map(Object::toString).collect(joining(" ")) + "\n");
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();
    bufferedWriter.close();
  }
}

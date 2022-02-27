package primsmstsub;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 30/12/2021
 */
public class Solution {

  /*
   * Complete the 'prims' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER n
   *  2. 2D_INTEGER_ARRAY edges
   *  3. INTEGER start
   */

  public static int prims(int n, List<List<Integer>> edges, int start) {
    // Write your code here

    List<int[]> sortedEdges = new ArrayList<>();

    int maxEdge = 0;
    for (List<Integer> edge : edges) {
      sortedEdges.add(
          new int[] {edge.get(0).intValue(), edge.get(1).intValue(), edge.get(2).intValue()});
      maxEdge = Math.max(maxEdge, edge.get(0));
      maxEdge = Math.max(maxEdge, edge.get(1));
    }

    Collections.sort(sortedEdges, Comparator.comparingInt(edge -> edge[2]));

    boolean[] visited = new boolean[maxEdge + 1];

    int weight = 0;

    visited[start] = true;
    while (!sortedEdges.isEmpty()) {
      Iterator<int[]> iterator = sortedEdges.iterator();

      while (iterator.hasNext()) {
        int[] offer = iterator.next();
        if (visited[offer[0]] && visited[offer[1]]) {
          iterator.remove();
          break;
        } else if (visited[offer[0]] || visited[offer[1]]) {
          weight += offer[2];
          visited[offer[0]] = true;
          visited[offer[1]] = true;
          iterator.remove();
          break;
        }
      }
    }

    return weight;
  }

  public static void main(String[] args) throws IOException {
//    String input =
//        "5 6\n" + "1 2 3\n" + "1 3 4\n" + "4 2 6\n" + "5 2 2\n" + "2 3 5\n" + "3 5 7\n" + "1";
//    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
        BufferedReader bufferedReader = new BufferedReader(new
            FileReader("src/primsmstsub/input.txt"));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int m = Integer.parseInt(firstMultipleInput[1]);

    List<List<Integer>> edges = new ArrayList<>();

    IntStream.range(0, m)
        .forEach(
            i -> {
              try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::valueOf)
                        .collect(toList()));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    int start = Integer.parseInt(bufferedReader.readLine().trim());

    int result = prims(n, edges, start);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

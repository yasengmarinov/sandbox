package kruskalmstrsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Yasen Marinov
 * @since 01/08/2021
 */
public class Solution {

  public static int kruskals(
      int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {

    int[][] vertices = buildVertices(gFrom, gTo, gWeight);

    int[] sets = new int[gNodes + 1];
    int[] setWeights = new int[gNodes + 1];

    for (int i = 1; i < gNodes + 1; i++) {
      sets[i] = i;
      setWeights[i] = 1;
    }

    int totalWeight = 0;
    for (int i = 0; i < vertices.length; i++) {
      int from = vertices[i][0];
      int to = vertices[i][1];

      int fromSet = setOf(sets, from);
      int toSet = setOf(sets, to);

      if (fromSet != toSet) {
        int biggerSet;
        int smallerSet;
        totalWeight += vertices[i][2];

        if (setWeights[fromSet] <= setWeights[toSet]) {
          smallerSet = fromSet;
          biggerSet = toSet;
        } else {
          smallerSet = toSet;
          biggerSet = fromSet;
        }

        sets[smallerSet] = toSet;
        setWeights[smallerSet] += setWeights[biggerSet];
      }
    }

    return totalWeight;
  }

  private static int setOf(int[] sets, int v) {
    int current = v;
    while (sets[current] != current) {
      current = sets[current];
    }

    return current;
  }

  private static int[][] buildVertices(
      List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {
    int[][] vertices = new int[gFrom.size()][];
    for (int i = 0; i < gFrom.size(); i++) {
      vertices[i] = new int[] {gFrom.get(i), gTo.get(i), gWeight.get(i)};
    }
    Arrays.sort(vertices, (Comparator.comparingInt(o -> o[2])));

    return vertices;
  }

  public static void main(String[] args) throws IOException {
    String input =
        "5 7\n"
            + "1 2 20\n"
            + "1 3 50\n"
            + "1 4 70\n"
            + "1 5 90\n"
            + "2 3 30\n"
            + "3 4 40\n"
            + "4 5 60";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

    String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int gNodes = Integer.parseInt(gNodesEdges[0]);
    int gEdges = Integer.parseInt(gNodesEdges[1]);

    List<Integer> gFrom = new ArrayList<>();
    List<Integer> gTo = new ArrayList<>();
    List<Integer> gWeight = new ArrayList<>();

    IntStream.range(0, gEdges)
        .forEach(
            i -> {
              try {
                String[] gFromToWeight =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                gFrom.add(Integer.parseInt(gFromToWeight[0]));
                gTo.add(Integer.parseInt(gFromToWeight[1]));
                gWeight.add(Integer.parseInt(gFromToWeight[2]));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    int res = kruskals(gNodes, gFrom, gTo, gWeight);
    // Write your code here.

    System.out.println(res);
    bufferedReader.close();
  }
}

package toll_cost_digits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Yasen Marinov
 * @since 13/02/2022
 */
public class Solution {
  public static void main(String[] args) throws IOException {
    String input = "3 3\n" + "1 3 602\n" + "1 2 256\n" + "2 3 411";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

    String[] roadNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int roadNodes = Integer.parseInt(roadNodesEdges[0]);
    int roadEdges = Integer.parseInt(roadNodesEdges[1]);

    List<Integer> roadFrom = new ArrayList<>();
    List<Integer> roadTo = new ArrayList<>();
    List<Integer> roadWeight = new ArrayList<>();

    IntStream.range(0, roadEdges)
        .forEach(
            i -> {
              try {
                String[] roadFromToWeight =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                roadFrom.add(Integer.parseInt(roadFromToWeight[0]));
                roadTo.add(Integer.parseInt(roadFromToWeight[1]));
                roadWeight.add(Integer.parseInt(roadFromToWeight[2]));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();

    printTollTakes(roadFrom, roadTo, roadWeight);
  }

  private static void printTollTakes(
      List<Integer> roadFrom, List<Integer> roadTo, List<Integer> roadWeight) {

    Map<Integer, Map<Integer, Integer>> roadMap = new HashMap<>();

    for (int i = 0; i < roadFrom.size(); i++) {
      int from = roadFrom.get(i);
      int to = roadTo.get(i);
      int weight = roadWeight.get(i);
      roadMap.computeIfAbsent(from, ignore -> new HashMap<>()).put(to, weight);
      roadMap.computeIfAbsent(to, ignore -> new HashMap<>()).put(from, 1000 - weight);
    }

    return;
  }
}

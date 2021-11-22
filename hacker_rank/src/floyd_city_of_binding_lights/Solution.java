package floyd_city_of_binding_lights;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Yasen Marinov
 * @since 09/08/2021
 */
public class Solution {

  static class GraphSearcher {

    int[][] matrix;

    public GraphSearcher(
        int roadNodes, List<Integer> roadFrom, List<Integer> roadTo, List<Integer> weight) {
      int matrixSize = roadNodes + 1;
      matrix = new int[matrixSize][matrixSize];
      for (int i = 0; i < matrixSize; i++) {
        for (int j = 0; j < matrixSize; j++) {
          matrix[i][j] = -1;
        }
      }
      for (int i = 1; i < matrixSize; i++) {
        matrix[i][i] = 0;
      }
      for (int i = 0; i < roadFrom.size(); i++) {
        matrix[roadFrom.get(i)][roadTo.get(i)] = weight.get(i);
      }
      for (int k = 1; k < matrixSize; k++) {
        for (int i = 1; i < matrixSize; i++) {
          for (int j = 1; j < matrixSize; j++) {
            if ((matrix[i][k] + matrix[k][j] < matrix[i][j] || matrix[i][j] < 0)
                && matrix[i][k] > 0
                && matrix[k][j] > 0) {
              matrix[i][j] = matrix[i][k] + matrix[k][j];
            }
          }
        }
      }
    }

    public int search(int x, int y) {
      return matrix[x][y];
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader =
        new BufferedReader(new FileReader("src/floyd_city_of_binding_lights/input00.txt"));

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

    GraphSearcher graphSearcher = new GraphSearcher(roadNodes, roadFrom, roadTo, roadWeight);

    int q = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, q)
        .forEach(
            qItr -> {
              try {
                String[] firstMultipleInput =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int x = Integer.parseInt(firstMultipleInput[0]);

                int y = Integer.parseInt(firstMultipleInput[1]);
                System.out.println(graphSearcher.search(x, y));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();
  }
}

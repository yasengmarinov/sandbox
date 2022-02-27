package connected_cell_in_a_grid;

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

  public static int connectedCell(List<List<Integer>> matrix) {
    // Write your code here

    int n = matrix.size();
    int m = matrix.get(0).size();

    int[][] grid = new int[n][m];
    boolean[][] visited = new boolean[n][m];
    int[][] root = new int[n][m];
    int[][] size = new int[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        grid[i][j] = matrix.get(i).get(j);
        visited[i][j] = false;
        root[i][j] = root(i, j);
        size[i][j] = 1;
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        traverse(grid, visited, root, size, i, j);
      }
    }

    int max = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        max = Math.max(max, size[i][j]);
      }
    }

    return max;
  }

  private static void traverse(
      int[][] grid, boolean[][] visited, int[][] root, int[][] size, int i, int j) {
    if (grid[i][j] == 1 && !visited[i][j]) {
      visited[i][j] = true;

      visitNeighbour(grid, visited, root, size, i, j, i - 1, j - 1);
      visitNeighbour(grid, visited, root, size, i, j, i, j - 1);
      visitNeighbour(grid, visited, root, size, i, j, i + 1, j - 1);

      visitNeighbour(grid, visited, root, size, i, j, i - 1, j);
      visitNeighbour(grid, visited, root, size, i, j, i + 1, j);

      visitNeighbour(grid, visited, root, size, i, j, i - 1, j + 1);
      visitNeighbour(grid, visited, root, size, i, j, i, j + 1);
      visitNeighbour(grid, visited, root, size, i, j, i + 1, j + 1);
    }
  }

  private static void visitNeighbour(
      int[][] grid,
      boolean[][] visited,
      int[][] root,
      int[][] size,
      int i,
      int j,
      int nextI,
      int nextJ) {
    if (nextI >= 0
        && nextJ >= 0
        && nextI < grid.length
        && nextJ < grid[0].length
        && !visited[nextI][nextJ]
        && grid[nextI][nextJ] == 1
        && root[i][j] != root[nextI][nextJ]) {
      int[] firstRoot = findRoot(root, i, j);
      int[] secondRoot = findRoot(root, nextI, nextJ);

      if (size[firstRoot[0]][firstRoot[1]] <= size[secondRoot[0]][secondRoot[1]]) {
        root[firstRoot[0]][firstRoot[1]] = root(secondRoot[0], secondRoot[1]);
        size[secondRoot[0]][secondRoot[1]] += size[firstRoot[0]][firstRoot[1]];
      } else {
        root[secondRoot[0]][secondRoot[1]] = root(firstRoot[0], firstRoot[1]);
        size[firstRoot[0]][firstRoot[1]] += size[secondRoot[0]][secondRoot[1]];
      }

      traverse(grid, visited, root, size, nextI, nextJ);
    }
  }

  private static int[] findRoot(int[][] root, int i, int j) {
    int offerI = i;
    int offerJ = j;

    int currentRoot = root[offerI][offerJ];
    while (currentRoot != root(offerI, offerJ)) {
      offerI = getIOfRoot(currentRoot);
      offerJ = getJOfRoot(currentRoot);
    }

    return new int[] {offerI, offerJ};
  }

  private static int getIOfRoot(int root) {
    return root / 100;
  }

  private static int getJOfRoot(int root) {
    return root % 100;
  }

  private static int root(int i, int j) {
    return i * 100 + j;
  }

  public static void main(String[] args) throws IOException {
    String input =
        "8\n"
            + "9\n"
            + "0 1 0 0 0 0 1 1 0\n"
            + "1 1 0 0 1 0 0 0 1\n"
            + "0 0 0 0 1 0 1 0 0\n"
            + "0 1 1 1 0 1 0 1 1\n"
            + "0 1 1 1 0 0 1 1 0\n"
            + "0 1 0 1 1 0 1 1 0\n"
            + "0 1 0 0 1 1 0 1 1\n"
            + "1 0 1 1 1 1 0 0 0";

    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.parseInt(bufferedReader.readLine().trim());

    int m = Integer.parseInt(bufferedReader.readLine().trim());

    List<List<Integer>> matrix = new ArrayList<>();

    IntStream.range(0, n)
        .forEach(
            i -> {
              try {
                matrix.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList()));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    int result = connectedCell(matrix);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

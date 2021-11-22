package bomber_man;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Solution {

  // Complete the bomberMan function below.
  static String[] bomberMan(int n, String[] grid) {
    if (n == 1) return grid;
    if (n % 2 == 0) {
      String row = "";
      for (int i = 0; i < grid[0].length(); i++) {
        row += "O";
      }
      String[] result = new String[grid.length];
      for (int i = 0; i < grid.length; i++) {
        result[i] = row;
      }

      return result;
    }

    int[][] field = new int[grid.length][grid[0].length()];
    for (int r = 0; r < grid.length; r++) {
      for (int i = 0; i < grid[r].length(); i++) {
        field[r][i] = grid[r].charAt(i) == '.' ? 0 : 2;
      }
    }

    for (int i = 1; i < n; i++) {
      passOneSecond(field, i % 2 == 1);
    }

    String[] result = new String[grid.length];

    for (int i = 0; i < result.length; i++) {
      result[i] = "";
      for (int j = 0; j < field[0].length; j++) {
        result[i] += field[i][j] == 0 ? '.' : '0';
      }
    }

    return result;
  }

  private static void passOneSecond(int[][] field, boolean plant) {
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[0].length; j++) {
        if (plant) {
          if (field[i][j] == 0) {
            field[i][j] = 3;
          } else {
            field[i][j]--;
          }
        } else {
          if (field[i][j] == 1) {
            detonateIfPossible(field, i, j, false);
            detonateIfPossible(field, i + 1, j, true);
            detonateIfPossible(field, i - 1, j, false);
            detonateIfPossible(field, i, j + 1, true);
            detonateIfPossible(field, i, j - 1, false);
          } else if (field[i][j] > 0) {
            field[i][j]--;
          }
        }
      }
    }
  }

  private static void detonateIfPossible(int[][] field, int i, int j, boolean remote) {
    if (i >= 0 && i < field.length && j >= 0 && j < field[0].length) {
      if (remote && field[i][j] == 1) return;
      field[i][j] = 0;
    }
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] rcn = scanner.nextLine().split(" ");

    int r = Integer.parseInt(rcn[0]);

    int c = Integer.parseInt(rcn[1]);

    int n = Integer.parseInt(rcn[2]);

    String[] grid = new String[r];

    for (int i = 0; i < r; i++) {
      String gridItem = scanner.nextLine();
      grid[i] = gridItem;
    }

    String[] result = bomberMan(n, grid);

    for (int i = 0; i < result.length; i++) {
      bufferedWriter.write(result[i]);

      if (i != result.length - 1) {
        bufferedWriter.write("\n");
      }
    }

    bufferedWriter.newLine();

    bufferedWriter.close();

    scanner.close();
  }
}

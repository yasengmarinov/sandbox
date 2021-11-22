package board_cutting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {

  private static final int MODULE = (int) Math.pow(10, 9) + 7;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int q = scanner.nextInt();

    for (int i = 0; i < q; i++) {
      runInstance(scanner);
    }
  }

  private static void runInstance(Scanner scanner) {
    int m = scanner.nextInt();
    int n = scanner.nextInt();

    List<Integer> y = new LinkedList<>();
    List<Integer> x = new LinkedList<>();

    for (int i = 0; i < m - 1; i++) {
      y.add(scanner.nextInt());
    }
    for (int i = 0; i < n - 1; i++) {
      x.add(scanner.nextInt());
    }
    Collections.sort(y);
    Collections.sort(x);
    Collections.reverse(y);
    Collections.reverse(x);

    int horizontalPieces = 1;
    int verticalPieces = 1;
    long cost = 0;

    while (!x.isEmpty() || !y.isEmpty()) {
      if (x.isEmpty()) {
        cost = (cost + (long) y.remove(0) * horizontalPieces) % MODULE;
        verticalPieces++;
      } else if (y.isEmpty()) {
        cost = (cost + (long) x.remove(0) * verticalPieces) % MODULE;
        horizontalPieces++;
      } else if (x.get(0) > y.get(0)) {
        cost = (cost + (long) x.remove(0) * verticalPieces) % MODULE;
        horizontalPieces++;
      } else {
        cost = (cost + (long) y.remove(0) * horizontalPieces) % MODULE;
        verticalPieces++;
      }
    }
    System.out.println(cost);
  }
}

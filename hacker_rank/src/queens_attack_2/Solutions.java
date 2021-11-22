package queens_attack_2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solutions {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int k = in.nextInt();

    Set<Coords> obstacles = new HashSet<>(k);

    int rQueen = in.nextInt();
    int cQueen = in.nextInt();
    for (int a0 = 0; a0 < k; a0++) {
      int rObstacle = in.nextInt();
      int cObstacle = in.nextInt();
      // your code goes here
      obstacles.add(new Coords(rObstacle, cObstacle));
    }

    long available = 0;

    // Calculate up
    for (int i = rQueen + 1; i <= n; i++) {
      if (!obstacles.contains(new Coords((int) i, (int) cQueen))) {
        available++;
      } else {
        break;
      }
    }

    // Calculate down
    for (int i = rQueen - 1; i >= 1; i--) {
      if (!obstacles.contains(new Coords((int) i, (int) cQueen))) {
        available++;
      } else {
        break;
      }
    }

    // Calculate right
    for (int i = cQueen + 1; i <= n; i++) {
      if (!obstacles.contains(new Coords((int) rQueen, (int) i))) {
        available++;
      } else {
        break;
      }
    }

    // Calculate left
    for (int i = cQueen - 1; i >= 1; i--) {
      if (!obstacles.contains(new Coords((int) rQueen, (int) i))) {
        available++;
      } else {
        break;
      }
    }

    // Calculate TopRight
    for (int s = 1; s < n; s++) {
      if (!obstacles.contains(new Coords(rQueen + s, cQueen + s))
          && rQueen + s <= n
          && cQueen + s <= n) {
        available++;
      } else {
        break;
      }
    }

    // Calculate TopLeft
    for (int s = 1; s < n; s++) {
      if (!obstacles.contains(new Coords(rQueen + s, cQueen - s))
          && rQueen + s <= n
          && cQueen - s >= 1) {
        available++;
      } else {
        break;
      }
    }

    // Calculate BottomRight
    for (int s = 1; s < n; s++) {
      if (!obstacles.contains(new Coords(rQueen - s, cQueen + s))
          && rQueen - s >= 1
          && cQueen + s <= n) {
        available++;
      } else {
        break;
      }
    }

    // Calculate BottomLeft
    for (int s = 1; s < n; s++) {
      if (!obstacles.contains(new Coords(rQueen - s, cQueen - s))
          && rQueen - s >= 1
          && cQueen - s >= 1) {
        available++;
      } else {
        break;
      }
    }

    System.out.println(available);
  }

  public static class Coords {
    int r;
    int c;

    public Coords(int r, int c) {
      this.r = r;
      this.c = c;
    }

    public int getR() {
      return r;
    }

    public int getC() {
      return c;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Coords coords = (Coords) o;

      if (r != coords.r) return false;
      return c == coords.c;
    }

    @Override
    public int hashCode() {
      int result = (int) r;
      result = 31 * result + (int) c;
      return result;
    }
  }
}

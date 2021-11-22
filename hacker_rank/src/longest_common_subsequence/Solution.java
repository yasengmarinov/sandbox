package longest_common_subsequence;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 16/08/2021
 */
public class Solution {

  public static List<Integer> longestCommonSubsequence(List<Integer> a, List<Integer> b) {
    // Write your code here

    int[][] mat = new int[a.size()][b.size()];
    for (int i = 0; i < a.size(); i++) {
      for (int j = 0; j < b.size(); j++) {
        if (a.get(i).equals(b.get(j))) {
          mat[i][j] = getFromMatrix(i - 1, j - 1, mat) + 1;
        } else {
          mat[i][j] = Math.max(getFromMatrix(i - 1, j, mat), getFromMatrix(i, j - 1, mat));
        }
      }
    }

    List<Integer> result = new LinkedList<>();

    int i = a.size() - 1;
    int j = b.size() - 1;

    while (result.size() < mat[a.size() - 1][b.size() - 1]) {
      if (mat[i][j] > getFromMatrix(i - 1, j, mat) && mat[i][j] > getFromMatrix(i, j - 1, mat)) {
        result.add(0, a.get(i));
        i--;
        j--;
      } else if (mat[i][j] == getFromMatrix(i, j - 1, mat)) {
        j--;
      } else {
        i--;
      }
    }

    return result;
  }

  private static int getFromMatrix(int a, int b, int[][] mat) {
    if (a < 0 || b < 0) {
      return 0;
    }
    return mat[a][b];
  }

  public static void main(String[] args) throws IOException {
    String input =
        "99 100\n"
            + "697 953 900 438 899 593 591 963 31 160 894 493 782 445 326 452 988 657 7 544 768 398 791 650 818 12 347 928 828 336 692 339 130 837 548 487 989 333 875 711 957 341 821 319 338 328 234 7 670 328 451 200 685 699 855 668 609 322 752 386 81 635 952 618 133 73 548 163 221 105 773 398 639 579 660 746 718 918 224 984 265 242 506 762 734 98 324 100 896 346 344 27 420 353 532 105 914 130 695\n"
            + "438 591 768 160 777 894 782 398 445 306 326 282 452 607 241 513 185 7 544 12 347 828 336 83 924 143 692 339 130 515 837 466 989 875 711 957 338 266 305 480 328 28 7 670 328 699 849 668 609 979 100 322 283 386 655 263 826 169 635 952 618 73 238 897 221 863 34 372 732 398 579 666 545 660 794 746 526 718 918 403 615 946 224 822 242 506 734 324 100 55 346 704 24 650 678 532 914 130 423 998\n";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int m = Integer.parseInt(firstMultipleInput[1]);

    List<Integer> a =
        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::valueOf)
            .collect(toList());

    List<Integer> b =
        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::valueOf)
            .collect(toList());

    List<Integer> result = longestCommonSubsequence(a, b);

    bufferedWriter.write(result.stream().map(Object::toString).collect(joining(" ")) + "\n");

    bufferedReader.close();
    bufferedWriter.close();
  }
}

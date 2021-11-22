package longest_increasing_subsequent;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Yasen Marinov
 * @since 18/08/2021
 */
public class Solution {

  public static int longestIncreasingSubsequence(List<Integer> arr) {
    // Write your code here
    Instant start = Instant.now();

    int[] sizes = new int[arr.size()];
    int idx = -1;

    for (int integer : arr) {
      if (idx == -1) {
        sizes[++idx] = integer;
        continue;
      }

      int last = sizes[idx];

      if (integer == last) {
        continue;
      } else if (integer > last) {
        sizes[++idx] = integer;
      } else {
        int offer = findFirstBigger(sizes, integer, 0, idx);
        if (offer >= 0) {
          if (offer > 0 && sizes[offer - 1] == integer) {
            continue;
          }
          sizes[offer] = integer;
        }
      }
    }

    System.out.println(Duration.between(start, Instant.now()).toMillis());

    return idx + 1;
  }

  private static int findFirstBigger(int[] sizes, int integer, int start, int end) {
    if (start == end) {
      if (sizes[start] > integer) {
        return start;
      } else {
        return -1;
      }
    }
    int idx = (end + start) / 2;
    if (sizes[idx] <= integer) {
      return findFirstBigger(sizes, integer, idx + 1, end);
    } else {
      return findFirstBigger(sizes, integer, start, idx);
    }
  }

  public static void main(String[] args) throws IOException {
    String input = "6\n" + "2\n" + "4\n" + "3\n" + "7\n" + "4\n" + "5";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.parseInt(bufferedReader.readLine().trim());

    List<Integer> arr =
        IntStream.range(0, n)
            .mapToObj(
                i -> {
                  try {
                    return bufferedReader.readLine().replaceAll("\\s+$", "");
                  } catch (IOException ex) {
                    throw new RuntimeException(ex);
                  }
                })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

    int result = longestIncreasingSubsequence(arr);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

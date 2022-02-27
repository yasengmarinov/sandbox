package insertion_sort;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 29/12/2021
 */
public class Solution {

  /*
   * Complete the 'insertionSort' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts INTEGER_ARRAY arr as parameter.
   */

  public static long insertionSort(List<Integer> arr) {
    int[] array = new int[arr.size()];
    int cnt = 0;
    for (Integer integer : arr) {
      array[cnt++] = integer;
    }

    return sort(array, 0, array.length);
  }

  private static long sort(int[] array, int start, int end) {
    if (start + 1 == end || start == end) {
      return 0;
    }

    int middle = (start + end) / 2;
    long left = sort(array, start, middle);
    long right = sort(array, middle, end);
    long merge = merge(array, start, end, middle);

    return left + right + merge;
  }

  private static long merge(int[] array, int start, int end, int middle) {
    int[] tmp = new int[end - start];
    int idx = 0;

    int lftIdx = start;
    int righIfx = middle;

    long cnt = 0;
    while (idx < end - start) {
      if (righIfx == end || lftIdx < middle && array[lftIdx] <= array[righIfx]) {
        tmp[idx++] = array[lftIdx++];
      } else {
        tmp[idx++] = array[righIfx++];
        cnt += middle - lftIdx;
      }
    }

    for (int i = 0; i < tmp.length; i++) {
      array[start + i] = tmp[i];
    }

    return cnt;
  }

  public static void main(String[] args) throws IOException {
    String input = "1  \n" + "5  \n" + "2 1 3 1 2";

    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t)
        .forEach(
            tItr -> {
              try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> arr =
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                long result = insertionSort(arr);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();
    bufferedWriter.close();
  }
}

package journey_to_the_moon;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 07/08/2021
 */
public class Solution {
  public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
    // Write your code here
    long result = 0;

    int[] nationsOffer = new int[n];
    int[] nationsSize = new int[n];

    for (int i = 0; i < n; i++) {
      nationsOffer[i] = i;
      nationsSize[i] = 1;
    }
    for (List<Integer> pair : astronaut) {
      int firstNation = nationOf(pair.get(0), nationsOffer);
      int secondNation = nationOf(pair.get(1), nationsOffer);

      if (firstNation == secondNation) {
        continue;
      }
      int smallerNation;
      int biggerNation;
      if (nationsSize[firstNation] <= nationsSize[secondNation]) {
        smallerNation = firstNation;
        biggerNation = secondNation;
      } else {
        smallerNation = secondNation;
        biggerNation = firstNation;
      }
      nationsOffer[smallerNation] = biggerNation;
      nationsSize[biggerNation] = nationsSize[smallerNation] += nationsSize[biggerNation];
    }
    for (int i = 0; i < n; i++) {
      if (nationsOffer[i] != i) {
        nationsSize[i] = 0;
      }
    }
    int[] actualNations = Arrays.stream(nationsSize).filter(num -> num > 0).toArray();

    for (int i = 0; i < actualNations.length - 1; i++) {
      for (int j = i + 1; j < actualNations.length; j++) {
        result += actualNations[i] * actualNations[j];
      }
    }

    return result;
  }

  private static int nationOf(int astr, int[] nations) {
    int offer = astr;
    while (offer != nations[offer]) {
      offer = nations[offer];
    }
    return offer;
  }

  public static void main(String[] args) throws IOException {
    String input = "100000 2\n" + "1 2\n" + "3 4";
    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

    int n = Integer.parseInt(firstMultipleInput[0]);

    int p = Integer.parseInt(firstMultipleInput[1]);

    List<List<Integer>> astronaut = new ArrayList<>();

    IntStream.range(0, p)
        .forEach(
            i -> {
              try {
                astronaut.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList()));
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    long result = journeyToMoon(n, astronaut);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

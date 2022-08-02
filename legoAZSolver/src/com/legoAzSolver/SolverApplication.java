package com.legoAzSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yasen Marinov
 * @since 02/08/2022
 */
public class SolverApplication {

  static String DICTIONARY = "dictionary50k.txt";
  static int WORD_MIN_LENGTH = 3;
  static int ALLOWED_LEFTOVERS = 2;

  public static void main(String... args) throws IOException {
    List<String> wordsRaw = loadDictionary();

    char[] letters = new char['z' - 'a' + 1];
    for(int i = 0; i < letters.length; i++) {
      letters[i] = (char) ('a' + i);
    }

    Solver solver = new Solver(wordsRaw.toArray(new String[]{}), letters, WORD_MIN_LENGTH, ALLOWED_LEFTOVERS);

    Instant start = Instant.now();

    System.out.println("Start searching");

    String[] solution = solver.solve();

    System.out.println("Completed in " + Duration.between(start, Instant.now()).toMillis());

    if(solution != null) {
      System.out.println(Arrays.asList(solution));
    } else {
      System.out.println(":(");
    }
  }

  private static List<String> loadDictionary() throws IOException {
    List<String> wordsRaw = Files.readAllLines(Path.of("resources", DICTIONARY));
    return wordsRaw;
  }

}

package com.legoAzSolver;

import static com.legoAzSolver.Helper.index;
import static com.legoAzSolver.Helper.isGoodWord;
import static com.legoAzSolver.Helper.letters;
import static com.legoAzSolver.Helper.lettersContain;
import static com.legoAzSolver.Helper.numOfLetters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yasen Marinov
 * @since 02/08/2022
 */
public class Solver {

  private String[] rawWords;
  private char[] letters;
  private int wordMinLength;
  private int allowedLeftovers;

  public Solver(String[] words, char[] letters, int wordMinLength, int allowedLeftovers) {
    this.rawWords = words;
    this.letters = letters;
    this.wordMinLength = wordMinLength;
    this.allowedLeftovers = allowedLeftovers;
  }

  public String[] solve() {
    String[] words = filterWords(rawWords);

    int[] indexedWords = new int[words.length];
    for(int i = 0; i < words.length; i++) {
      indexedWords[i] = index(words[i]);
    }
    return solveInner(0, indexedWords, words, index(letters), new HashSet<>());
  }

  private String[] filterWords(String[] rawWords) {
    List<String> words = Stream.of(rawWords)
        .map((word) -> word.toLowerCase())
        .filter((word) -> isGoodWord(word))
        .filter((word) -> word.length() >= wordMinLength)
        .collect(Collectors.toList());

    Collections.shuffle(words);

    return words.toArray(String[]::new);
  }

  private String[] solveInner(int startIdx, int[] words, String[] rawWords, int letters, HashSet<Integer> seen) {
    int lettersCnt = numOfLetters(letters);

    if (lettersCnt <= allowedLeftovers && !letters(letters).contains('q')) {
      System.out.println("Left: " + letters(letters));
      return new String[]{};
    }
    if (startIdx >= words.length) {
      return null;
    }

    for(int i = startIdx; i < words.length; i++) {
      if (lettersContain(letters, words[i])) {
        int subLetters = letters & ~words[i];

        if (seen.add(subLetters)) {
          String[] solution = solveInner(i + 1, words, rawWords, subLetters, seen);

          if (solution != null) {
            String[] aggregatedSolution = Arrays.copyOf(solution, solution.length + 1);
            aggregatedSolution[aggregatedSolution.length - 1] = rawWords[i];

            return aggregatedSolution;
          }
        }
      }
      if (startIdx == 0) {
        System.out.println("Completed: " + i);
      }
    }

    return null;
  }

}

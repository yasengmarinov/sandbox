package the_story_of_a_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
/**
 * @author Yasen Marinov
 * @since 04/08/2021
 */
public class Solution {
  /*
   * Complete the 'storyOfATree' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts following parameters:
   *  1. INTEGER n
   *  2. 2D_INTEGER_ARRAY edges
   *  3. INTEGER k
   *  4. 2D_INTEGER_ARRAY guesses
   */

  public static String storyOfATree(
      int n, List<List<Integer>> edges, int k, List<List<Integer>> guesses) {
    // Write your code here

    SortedMap<Integer, List<List<Integer>>> sortedByFirst = new TreeMap<>();
    SortedMap<Integer, List<List<Integer>>> sortedBySecond = new TreeMap<>();

    for (List<Integer> edge : edges) {
      sortedByFirst.computeIfAbsent(edge.get(0), (key) -> new LinkedList<>()).add(edge);
      sortedBySecond.computeIfAbsent(edge.get(1), (key) -> new LinkedList<>()).add(edge);
    }

    Set<Long> guessesSet = new HashSet<>();
    for (List<Integer> guess : guesses) {
      guessesSet.add(nodeHash(guess));
    }

    int[] successGuessesPerRoute = new int[n + 1];
    LinkedList<int[]> rootQueue = new LinkedList<>();

    int rootId = 1;
    Set<Integer> visited = new HashSet<>();
    LinkedList<Integer> queue = new LinkedList<>();
    int properGuesses = 0;
    queue.add(rootId);

    while (visited.size() != n && !queue.isEmpty()) {
      int nodeId = queue.pollFirst();
      visited.add(nodeId);
      int[] children = findChildren(nodeId, sortedByFirst, sortedBySecond, visited);

      for (int child : children) {
        if (guessesSet.contains(nodeHash(nodeId, child))) {
          properGuesses++;
        }
        queue.add(child);
        rootQueue.add(new int[] {nodeId, child});
      }
    }

    successGuessesPerRoute[rootId] = properGuesses;

    while (!rootQueue.isEmpty()) {
      int[] pair = rootQueue.pollFirst();
      int oldRoot = pair[0];
      int newRoot = pair[1];

      successGuessesPerRoute[newRoot] = successGuessesPerRoute[oldRoot];
      if (guessesSet.contains(nodeHash(oldRoot, newRoot))) {
        successGuessesPerRoute[newRoot]--;
      }
      if (guessesSet.contains(nodeHash(newRoot, oldRoot))) {
        successGuessesPerRoute[newRoot]++;
      }
    }

    int total = n;
    int positives =
        Math.toIntExact(Arrays.stream(successGuessesPerRoute).filter(guess -> guess >= k).count());
    if (positives == 0) {
      return "0/1";
    }
    for (int i = positives; i > 1; i--) {
      if (positives % i == 0 && total % i == 0) {
        total = total / i;
        positives = positives / i;
      }
    }

    return positives + "/" + total;
  }

  private static Long nodeHash(List<Integer> guess) {
    return nodeHash(guess.get(0), guess.get(1));
  }

  private static Long nodeHash(Integer o1, Integer o2) {
    long hash = o1;
    hash = hash << 32;
    hash += o2;
    return hash;
  }

  private static int[] findChildren(
      int node,
      SortedMap<Integer, List<List<Integer>>> sortedByFirst,
      SortedMap<Integer, List<List<Integer>>> sortedBySecond,
      Set<Integer> visited) {

    List<Integer> children = new LinkedList<>();
    sortedByFirst
        .getOrDefault(node, Collections.emptyList())
        .forEach(edge -> children.add(edge.get(1)));
    sortedBySecond
        .getOrDefault(node, Collections.emptyList())
        .forEach(edge -> children.add(edge.get(0)));

    return children.stream()
        .filter(Predicate.not(visited::contains))
        .mapToInt(Integer::intValue)
        .toArray();
  }

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader =
        new BufferedReader(new FileReader("/home/yasen/Downloads/input1.txt"));
    BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());

    int q = Integer.parseInt(bufferedReader.readLine().trim());

    for (int qItr = 0; qItr < q; qItr++) {
      int n = Integer.parseInt(bufferedReader.readLine().trim());

      List<List<Integer>> edges = new ArrayList<>();

      for (int i = 0; i < n - 1; i++) {
        String[] edgesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> edgesRowItems = new ArrayList<>();

        for (int j = 0; j < 2; j++) {
          int edgesItem = Integer.parseInt(edgesRowTempItems[j]);
          edgesRowItems.add(edgesItem);
        }

        edges.add(edgesRowItems);
      }

      String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

      int g = Integer.parseInt(firstMultipleInput[0]);

      int k = Integer.parseInt(firstMultipleInput[1]);

      List<List<Integer>> guesses = new ArrayList<>();

      for (int i = 0; i < g; i++) {
        String[] guessesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> guessesRowItems = new ArrayList<>();

        for (int j = 0; j < 2; j++) {
          int guessesItem = Integer.parseInt(guessesRowTempItems[j]);
          guessesRowItems.add(guessesItem);
        }

        guesses.add(guessesRowItems);
      }

      String result = storyOfATree(n, edges, k, guesses);

      System.out.println(result);

      bufferedWriter.write(result);
      bufferedWriter.newLine();
    }

    bufferedReader.close();
    bufferedWriter.close();
  }
}

package arithmetic_expressions;

import java.util.Scanner;

public class Solution {

  public static final int MODULE = 101;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int n = scanner.nextInt();
    int[] numbers = new int[n];

    for (int i = 0; i < n; i++) {
      numbers[i] = scanner.nextInt();
    }

    System.out.println(findSolution(numbers));
  }

  private static String findSolution(int[] numbers) {
    String toReturn = numbers[0] + processNumber(1, numbers, numbers[0]);
    return toReturn;
  }

  private static String processNumber(int index, int[] numbers, int sumSoFar) {
    if (index >= numbers.length) {
      if (sumSoFar % MODULE == 0) {
        return "";
      } else {
        return null;
      }
    }
    String offer;

    offer = processNumber(index + 1, numbers, (sumSoFar * numbers[index]) % MODULE);
    if (offer != null) {
      StringBuilder builder = new StringBuilder();
      builder.append("*");
      builder.append(numbers[index]);
      builder.append(offer);
      return builder.toString();
    }
    offer = processNumber(index + 1, numbers, (sumSoFar + numbers[index]) % MODULE);
    if (offer != null) {
      StringBuilder builder = new StringBuilder();
      builder.append("+");
      builder.append(numbers[index]);
      builder.append(offer);
      return builder.toString();
    }
    offer = processNumber(index + 1, numbers, (sumSoFar - numbers[index]) % MODULE);
    if (offer != null) {
      StringBuilder builder = new StringBuilder();
      builder.append("-");
      builder.append(numbers[index]);
      builder.append(offer);
      return builder.toString();
    }

    return null;
  }
}

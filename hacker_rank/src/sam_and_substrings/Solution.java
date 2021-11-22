package sam_and_substrings;

import java.util.Scanner;

public class Solution {

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    long module = (long) Math.pow(10, 9) + 7;

    String array = scanner.next();
    int[] numbers = array.chars().map(Character::getNumericValue).toArray();
    long result = 0;
    long f = 1;
    for (int i = numbers.length - 1; i >= 0; i--) {
      result = (result + numbers[i] * f * (i + 1)) % module;
      f = (f * 10 + 1) % module;
    }

    System.out.println(result);
  }
}

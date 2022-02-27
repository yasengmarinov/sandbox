package counting_strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.stream.IntStream;
/**
 * @author Yasen Marinov
 * @since 13/02/2022
 */
public class Solution {

  /*
   * Complete the 'countStrings' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. STRING r
   *  2. INTEGER l
   */

  public static int countStrings(String r, int l) {
    // Write your code here
    Regex reg = parseRegex(r, 0, r.length());
    //    return countStrings(r, l, 0, r.length());

    return reg.evaluate(l);
  }

  static Regex parseRegex(String string, int start, int end) {
    if (string.charAt(start) == '(') {
      start = start + 1;
      end = end - 1;
    }
    if (end - start == 1) {
      return new Regex(string.charAt(start));
    }
    if (end - start == 2) {
      return new Regex(string.substring(start, end));
    }

    int leftStart = start;
    int leftEnd;
    if (string.charAt(leftStart) == '(') {
      leftEnd = findNextClosingBracket(string, leftStart);
    } else {
      // this must be either a or b
      leftEnd = leftStart;
    }

    int rightStart = leftEnd + 1;

    boolean orOperator = false;
    if (string.charAt(leftEnd + 1) == '|') {
      orOperator = true;
      rightStart++;
    }
    return new Regex(
        parseRegex(string, leftStart, leftEnd + 1),
        parseRegex(string, rightStart, end),
        orOperator);
  }

  private static int findNextClosingBracket(String string, int leftStart) {
    int brackets = 1;
    int idx = leftStart;
    while (brackets > 0) {
      idx++;
      if (string.charAt(idx) == '(') {
        brackets++;
      } else if (string.charAt(idx) == ')') {
        brackets--;
      }
    }
    return idx;
  }

  static class Regex {
    String simpleValue;
    char singleChar = 'c';

    Regex left;
    Regex right;
    boolean orOperator;

    public Regex(char singleChar) {
      this.singleChar = singleChar;
    }

    public Regex(String simpleValue) {
      this.simpleValue = simpleValue;
    }

    public Regex(Regex left, Regex right, boolean orOperator) {
      this.left = left;
      this.right = right;
      this.orOperator = orOperator;
    }

    boolean isWildcard() {
      return '*' == singleChar;
    }

    public int evaluate(int length) {
      if (simpleValue != null) {
        if (simpleValue.charAt(1) == '*') {
          return 1;
        } else if (length == 2) {
          return 1;
        } else {
          return 0;
        }
      }
      if (singleChar != 'c') {
        if (length == 1) {
          return 1;
        } else {
          return 0;
        }
      }
      if (orOperator) {
        return left.evaluate(length) + right.evaluate(length);
      }

      if (right.isWildcard()) {
        for (int i = 1; i <= length; i++) {
          if (length % i == 0) {
            int leftSolution = left.evaluate(i);
            if (leftSolution != 0) {
              return (int) Math.round(Math.pow(leftSolution, length / i));
            }
          }
        }
        return 0;
      } else {
        int count = 0;
        for (int i = 0; i <= length; i++) {
          count += left.evaluate(i) * right.evaluate(length - i);
        }
        return count;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String input =
        "1\n"
            + "(((((((((((((((a|b)*)a)(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))(a|b))\n"
            + "1000000000";

    BufferedReader bufferedReader = new BufferedReader(new StringReader(input));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t)
        .forEach(
            tItr -> {
              try {
                String[] firstMultipleInput =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                String r = firstMultipleInput[0];

                int l = Integer.parseInt(firstMultipleInput[1]);

                int result = countStrings(r, l);

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

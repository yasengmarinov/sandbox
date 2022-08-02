package com.legoAzSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Yasen Marinov
 * @since 02/08/2022
 */
public class Helper {
  static int[] LETTER_MASKS;
  static Pattern AZ = Pattern.compile("[a-z]+");
  static int[] VOWELS =new int[] {'a', 'e', 'i', 'o','u'};
  static {
    Arrays.sort(VOWELS);
  }

  static {
    LETTER_MASKS = new int['z' - 'a' + 1];

    for(int i = 'a'; i <= 'z'; i++) {
      LETTER_MASKS[i - 'a'] = 1 << (i - 'a');
    }
  }

  static boolean isGoodWord(String word) {
    return AZ.matcher(word).matches() && noRepeatingLetters(word) && word.charAt(word.length() - 1) != 's';
  }
  private static boolean noRepeatingLetters(String word) {
    Set<Integer> chars = new HashSet<>();

    for (char ch : word.toCharArray()) {
      if (!chars.add((int) ch)) {
        return false;
      }
    }

    return true;
  }

  static int numOfLetters(int letters) {
    int cnt = 0;

    for(int i = 0; i < 27; i++) {
      cnt += letters & 1;
      letters = letters>> 1;
    }

    return cnt;
  }

  public static boolean onlySingleVowel(String word) {
    int cnt = 0;

    for (char c : word.toCharArray()) {
      if (Arrays.binarySearch(VOWELS, c) >= 0) {
        cnt++;
        if (cnt > 1) {
          return false;
        }
      }
    }
    return true;
  }

  static Collection<Character> letters(int letters) {
    List<Character> matched = new ArrayList<>();
    for(int i = 0; i < LETTER_MASKS.length; i++) {
      if ((letters & LETTER_MASKS[i]) != 0) {
        matched.add((char) (i + 'a'));
      }
    }

    return matched;
  }
  static boolean lettersContain(int letters, int word) {
    return word == (letters & word);
  }
  static int index(String string) {
    return index(string.toCharArray());
  }

  static int index(char[] chars) {
    int hash = 0;
    for(int i = 0; i < chars.length; i++) {
      hash = hash | maskForLetter(chars[i]);
    }

    return hash;
  }

  static int maskForLetter(char ch) {
    return LETTER_MASKS[ch - 'a'];
  }
}

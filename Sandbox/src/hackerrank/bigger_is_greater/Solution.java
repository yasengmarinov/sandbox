package hackerrank.bigger_is_greater;

import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		String a[] = new String[n];
		for(int a_i=0; a_i < n; a_i++){
			a[a_i] = in.next();
		}
		for (String word:a) {
			String lexBigger = nextLexicallyOrdered(word);
			if (lexBigger.equals(word)) {
				System.out.println("no answer");
			} else {
				System.out.println(lexBigger);
			}
		}
	}

	private static String nextLexicallyOrdered(String word) {
		for (int i = word.length() - 2; i >= 0; i--) {
			String lexBigger = nextLexicallyBiggerAfterIndex(word, i);
			if (!lexBigger.equals(word)) {
				return lexBigger;
			}
		}
		return word;
	}

	private static String nextLexicallyBiggerAfterIndex(String word, int index) {
		char[] charArray = word.toCharArray();
		char currentChar = charArray[index];
		char[] afterCurrChar = new char[charArray.length - index - 1];
		int afterCurrCharPosition = 0;
		for (int i = index + 1; i < charArray.length; i++) {
			afterCurrChar[afterCurrCharPosition] = charArray[i];
			afterCurrCharPosition++;
		}
		for (int i = afterCurrChar.length - 1; i >= 0; i--) {
			if (afterCurrChar[i] > currentChar) {
				charArray[index] = afterCurrChar[i];
				charArray[i + index + 1] = currentChar;
				charArray = sortLexicallyAfterIndex(charArray, index);
				break;
			}
		}
		return String.copyValueOf(charArray);
	}

	private static char[] sortLexicallyAfterIndex(final char[] charArray, int index) {
		char[] tmp = charArray.clone();
		char[] toSort = new char[charArray.length - index - 1];
		int toSortPosition = 0;
		for (int i = index + 1; i < tmp.length; i++) {
			toSort[toSortPosition] = tmp[i];
			toSortPosition++;
		}
		Arrays.sort(toSort);
		toSortPosition = 0;
		for (int i = index + 1; i < tmp.length; i++) {
			tmp[i] = toSort[toSortPosition];
			toSortPosition++;
		}
		return tmp;
	}

}
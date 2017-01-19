package hackerrank.mark_exploration;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String S = in.next();

		int diff = 0;
		for (int i = 0; i < S.length(); i++) {
			if (i % 3 == 1) {
				if (S.charAt(i) != 'O') {
					diff++;
				}
			} else {
				if (S.charAt(i) != 'S') {
					diff++;
				}
			}
		}

		System.out.println(diff);
	}
}

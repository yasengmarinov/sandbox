package hackerrank.repeated_string;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		long n = in.nextLong();

		long count = 0;
		int strSize = s.length();
		for (int i = 0; i < strSize; i++) {
			count+= s.charAt(i) == 'a' ? 1 : 0;
		}
		int leftOver = (int)(n % strSize);
		count = count*(n / strSize);
		for (int i = 0; i < leftOver; i++) {
			count+= s.charAt(i) == 'a' ? 1 : 0;
		}

		System.out.println(count);
	}
}

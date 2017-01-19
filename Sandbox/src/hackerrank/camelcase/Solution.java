package hackerrank.camelcase;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		Matcher upperCase = Pattern.compile(".*?[A-Z]{1}.*?").matcher(s);
		int count = 1;
		while (upperCase.find()) {
			count ++;
		}

		System.out.println(count);
	}
}

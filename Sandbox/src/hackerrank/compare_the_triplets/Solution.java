package hackerrank.compare_the_triplets;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int a0 = in.nextInt();
		int a1 = in.nextInt();
		int a2 = in.nextInt();
		int b0 = in.nextInt();
		int b1 = in.nextInt();
		int b2 = in.nextInt();

		int scoreA = 0;
		int scoreB = 0;
		if (a0 > b0) scoreA++;
		if (a0 < b0) scoreB++;
		if (a1 > b1) scoreA++;
		if (a1 < b1) scoreB++;
		if (a2 > b2) scoreA++;
		if (a2 < b2) scoreB++;

		System.out.println(scoreA + " " + scoreB);
	}
}


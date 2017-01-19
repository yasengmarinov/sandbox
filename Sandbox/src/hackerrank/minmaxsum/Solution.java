package hackerrank.minmaxsum;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long a = in.nextLong();
		long b = in.nextLong();
		long c = in.nextLong();
		long d = in.nextLong();
		long e = in.nextLong();

		long[] list = {a, b, c, d, e};
		Arrays.sort(list);

		BigInteger sumMin = BigInteger.valueOf(0);
		BigInteger sumMax = BigInteger.valueOf(0);

		for (int i = 0; i < 4; i++) {
			sumMin = sumMin.add(BigInteger.valueOf(list[i]));
		}
		for (int i = 1; i < 5; i++) {
			sumMax = sumMax.add(BigInteger.valueOf(list[i]));
		}

		System.out.println(sumMin + " " + sumMax);

	}
}

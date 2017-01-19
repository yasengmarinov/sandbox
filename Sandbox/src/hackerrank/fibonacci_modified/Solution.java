package hackerrank.fibonacci_modified;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Solution {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int t1 = in.nextInt();
		int t2 = in.nextInt();
		int n = in.nextInt();

		System.out.println(findNthModifiedFibonacciNumber(t1, t2, n));

	}

	private static BigInteger findNthModifiedFibonacciNumber(long t1, long t2, int n) {
		int currentIndex = 3;
		BigInteger result = findModifiedFibonacciNumber(BigInteger.valueOf(t1), BigInteger.valueOf(t2), currentIndex, n);
		return result;
	}

	private static BigInteger findModifiedFibonacciNumber(BigInteger t1, BigInteger t2, int currentIndex, int n) {
		BigInteger value = t1.add(t2.pow(2));
		if (currentIndex == n)
			return value;
		else return findModifiedFibonacciNumber(t2, value, ++currentIndex, n);
	}
}
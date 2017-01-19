package hackerrank.strange_advertising;

import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int hooked = 2;
		int totalHooked = 2;
		double currPpl = 5;
		for (int i = 1; i<n; i++) {
			currPpl = hooked*3;
			hooked = (int)Math.floor(currPpl/2);
			totalHooked +=hooked;
		}
		System.out.println(totalHooked);
	}
}
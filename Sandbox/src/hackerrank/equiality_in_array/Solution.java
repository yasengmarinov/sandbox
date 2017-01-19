package hackerrank.equiality_in_array;

import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int a[] = new int[n];
		for(int a_i=0; a_i < n; a_i++){
			a[a_i] = in.nextInt();
		}

		Arrays.sort(a);

		int seq = 1;
		int longestSeq = 1;
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i+1] == a[i]) {
				seq++;
				if (seq >= longestSeq) {
					longestSeq = seq;
				}
			} else {
				seq = 1;
			}
		}

		System.out.println(a.length - longestSeq);
	}
}
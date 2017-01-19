package hackerrank.sock_merchant;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int c[] = new int[n];
		for(int c_i=0; c_i < n; c_i++){
			c[c_i] = in.nextInt();
		}

		int count = 0;

		for (int i = 0; i <= c.length - 2; i++) {
			for (int j = i+1; j <= c.length - 1; j++) {
				if (c[i] == c[j] && c[j] != 0) {
					count++;
					c[j] = 0;
					break;
				}
			}
		}

		System.out.println(count);

	}
}

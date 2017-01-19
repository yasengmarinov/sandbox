package hackerrank.jumping_on_the_clouds;

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
		int jumps = 0;
		int position = 0;
		while (position < n-1) {
			if(position + 2 > n-1 || c[position+2] == 0) {
				position += 2;
			} else {
				position += 1;
			}
			jumps++;
		}
		System.out.println(jumps);
	}
}

package hackerrank.bon_appetit;

import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int a[] = new int[n];
		for(int a_i=0; a_i < n; a_i++){
			a[a_i] = in.nextInt();
		}

		int b = in.nextInt();
		int sum = 0;
		for (int price:a) {
			sum += price;
		}
		sum -= a[k];
		if (b*2 == sum) {
			System.out.println("Bon Appetit");
		}

		else {
			System.out.println(Math.abs(sum/2 - b));
		}

	}
}
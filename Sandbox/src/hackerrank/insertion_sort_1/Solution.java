package hackerrank.insertion_sort_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {



	public static void insertIntoSorted(int[] ar) {
		int toBeSorted = ar[ar.length-1];
		for (int i = ar.length -1; i>=0; i--) {
			if (isRightPlace(ar, toBeSorted, i)) {
				ar[i] = toBeSorted;
				printArray(ar);
				break;
			}
			else {
				ar[i] = ar[i-1];
				printArray(ar);
			}
		}

	}

	public static boolean isRightPlace(int[] ar, int value, int index) {
		if (index < 1) {
			return true;
		}
		if (ar[index - 1] <= value && index > ar.length - 2 ) {
			return true;
		}

		if (ar[index - 1] <= value && ar[index + 1] >= value) {
			return true;
		}
		return false;
	}


	/* Tail starts here */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int s = in.nextInt();
		int[] ar = new int[s];
		for(int i=0;i<s;i++){
			ar[i]=in.nextInt();
		}
		insertIntoSorted(ar);
	}


	private static void printArray(int[] ar) {
		for(int n: ar){
			System.out.print(n+" ");
		}
		System.out.println("");
	}


}

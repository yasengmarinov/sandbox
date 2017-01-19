package hackerrank.absolute_permutation;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		int[][] results = new int[t][];
		for(int a0 = 0; a0 < t; a0++){
			int n = in.nextInt();
			int k = in.nextInt();
			results[a0] = findAbsolutePermutation(n, k);
		}

		for (int[] result:results) {
			for (int number:result) {
				System.out.print(number + " ");
			}
			System.out.println("");
		}
	}

	private static int[] findAbsolutePermutation(int n, int k) {
		Set<Integer> numbersLeft = new HashSet<>();
		for (int i = 1; i <= n; i++) {
			numbersLeft.add(i);
		}

		int[] result = findNextPosition(numbersLeft, numbersLeft.size(), 1, k);

		if (result == null) {
			int[] toReturn = {-1};
			return toReturn;
		}

		return result;
	}

	private static int[] findNextPosition(Set<Integer> numbersLeft, int size, int position, int k) {
		if (position > size) {
			return new int[size];
		}
		int toFind = position - k;
		int toFindNext = position + k;

		if (numbersLeft.remove(toFind)) {
			return addNumberToPermutation(findNextPosition(numbersLeft, size, position + 1, k), position, toFind);
		} else if (numbersLeft.remove(toFindNext)) {
			return addNumberToPermutation(findNextPosition(numbersLeft, size, position + 1, k), position, toFindNext);
		} else {
			return null;
		}
	}

	private static int[] addNumberToPermutation(int[] list, int position, int toAdd) {
		if (list == null)
			return null;
		list[position - 1] = toAdd;
		return list;
	}
}

package hackerrank.almost_sorted;

import java.util.*;

public class Solution {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] array = new int[n];
		for(int a_i=0; a_i < n; a_i++){
			array[a_i] = in.nextInt();
		}

		if (isSorted(array, true)) {
			System.out.println("yes");
			return;
		}
		int[] swapValues = isSwappable(array);
		if (swapValues[0] != -1) {
			System.out.println("yes\n" + "swap " + swapValues[0] + " " + swapValues[1]);
		} else {
			int[] reverseValues = isReversable(array);
			if(reverseValues[0] != -1) {
				System.out.println("yes\n" + "reverse " + reverseValues[0] + " " + reverseValues[1]);
			} else {
				System.out.println("no");
			}
		}


	}

	private static int[] isReversable(int[] array) {
		int[] result = {-1, -1};
		int lastAscendingIndex = getLastAscendingIndex(array);
		int reverseCandidate = getReverseCandidate(array, lastAscendingIndex);
		if (reverseCandidate != -1) {
			result[0] = lastAscendingIndex + 1;
			result[1] = reverseCandidate + 1;
		}
		return result;
	}

	private static int[] isSwappable(int[] array) {
		int[] result = {-1, -1};
		int lastAscendingIndex = getLastAscendingIndex(array);
		int swapCandidate = getSwapCandidate(array, lastAscendingIndex);
		if (swapCandidate != -1) {
			result[0] = lastAscendingIndex + 1;
			result[1] = swapCandidate + 1;
		}
		return result;
	}

	private static int getReverseCandidate(int[] array, int lastAscendingIndex) {
		int candidate = -1;
		int lastDescdendingIndex = getLastDescendingIndex(array);
		if (lastDescdendingIndex != -1 && isSorted(getReversedArray(array, lastAscendingIndex, lastDescdendingIndex), true)) {
			candidate = lastDescdendingIndex;
		}
		return candidate;
	}

	private static int getSwapCandidate(int[] array, int lastAscendingIndex) {
		int candidate = -1;
		int lastDescdendingIndex = getLastDescendingIndex(array);
		if (lastDescdendingIndex != -1 && isSorted(getSwappedArray(array, lastAscendingIndex, lastDescdendingIndex), true)) {
			candidate = lastDescdendingIndex;
		}
		return candidate;
	}

	private static int[] getReversedArray(int[] array, int start, int end) {
		int[] result = array.clone();
		for (int i = end; i >= start; i--) {
			result[end + start - i] = array[i];
		}
		return result;
	}

	private static int[] getSwappedArray(int[] array, int fromIndex, int toIndex) {
		int[] result  = array.clone();
		result[fromIndex] = array[toIndex];
		result[toIndex] = array[fromIndex];
		return  result;
	}

	private static boolean isPlaceCorrect(int[] array, Integer value, int index) {
		if (index == 0 || array[index - 1] <= value) {
			if (index == array.length - 1 || array[index + 1] >= value) {
				return true;
			}
		}
		return false;
	}

	private static int getLastAscendingIndex(int[] array) {
		int index = 0;
		while (index + 1 < array.length && array[index] <= array[index+1]) {
			index++;
		}
		return index;
	}

	private static int getLastDescendingIndex(int[] array) {
		int index = array.length - 1;
		while (index > 0 && array[index] >= array[index-1]) {
			index--;
		}
		return index;
	}

	private static boolean isSorted(int[] array, boolean asc) {

		if (asc) {
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i] > array[i+1])
					return false;
			}
		} else {
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i] < array[i+1])
					return false;
			}
		}
		return true;
	}
}
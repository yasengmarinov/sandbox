package hackerrank.vertical_sticks;

import java.util.*;
/**
 * Created by b06514a on 1/17/2017.
 */
public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		double[] results = new double[t];
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[] array = new int[n];

			for (int j = 0; j < n; j++) {
				array[j] = in.nextInt();
			}

//			results[i] = calculateAverageVerticalSticks(array);
			results[i] = calculateAverageVerticalSticksSmart(array);
		}

		for (double result:results) {
			System.out.println(String.format("%.2f", result));
		}
	}

	private static double calculateAverageVerticalSticksSmart(int[] array) {
		double result = 0;
		for (int i = 0; i < array.length; i++) {
			result += getAverageStickPerElement(array, i);
		}
		return result;
	}

	private static double getAverageStickPerElement(int[] array, int index) {
		int currentElement = array[index];
		int k = 0;
		for (int element:array) {
			if (element >= currentElement)
				k ++;
		}

		return (double)(array.length + 1) / (k + 1);
	}

	private static double calculateAverageVerticalSticks(int[] array) {
		ArrayList<int[]> permutations;
		permutations = findPermutations(array);
		int verticalSticksSum = 0;
		for (int i = 0; i < permutations.size(); i++) {
			verticalSticksSum += calculateVerticalStickSum(permutations.get(i));
		}

		return (double)verticalSticksSum/permutations.size();
	}

	private static int calculateVerticalStickSum(int[] array) {
		int size = 0;
		for (int i = 0; i < array.length; i++) {
			size += calculateVerticalStick(array, i);
		}
		return size;
	}

	private static int calculateVerticalStick(int[] array, int index) {
		int size = 1;
		int currentElement = array[index];
		for (int i = index - 1; i >= 0 ; i--) {
			if (array[i] < currentElement) {
				size++;
			} else {
				break;
			}
		}
		return size;
	}

	private static ArrayList<int[]> findPermutations(int[] array) {
		ArrayList<int[]> permutations = new ArrayList<>();
		permutations.add(array);
		int currentIndex = 0;
		while (currentIndex < array.length) {
			permutations.addAll(findPermutaionFromIndex(permutations, currentIndex));
			currentIndex++;
		}
		return permutations;
	}

	private static ArrayList<int[]> findPermutaionFromIndex(ArrayList<int[]> initialPermutations, int currentIndex) {
		ArrayList<int[]> permutations = new ArrayList<>();
		Iterator<int[]> i = initialPermutations.iterator();
		while (i.hasNext()) {
			int[] presentPermutaion = i.next();
			for (int j = currentIndex + 1; j < presentPermutaion.length; j++) {
				permutations.add(swapElements(presentPermutaion, currentIndex, j));
			}
		}
		return permutations;
	}

	private static int[] swapElements(final int[] presentPermutaion, int index1, int index2) {
		int[] newPermutaion = presentPermutaion.clone();
		newPermutaion[index1] = presentPermutaion[index2];
		newPermutaion[index2] = presentPermutaion[index1];
		return newPermutaion;
	}

}

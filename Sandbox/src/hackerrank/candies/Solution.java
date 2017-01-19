package hackerrank.candies;

/**
 * Created by b06514a on 1/17/2017.
 */
import java.util.*;

public class Solution {

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = in.nextInt();
		}
		long candies = calculateCandies(array);
		System.out.println(candies);
	}

	private static long calculateCandies(int[] array) {
		long candies = 0;
		int[] arrayCandies = array.clone();
		arrayCandies[0] = 1;
		for (int i = 1; i < array.length; i++) {
			if (array[i-1] < array[i]) {
				arrayCandies[i] = arrayCandies[i-1] + 1;
			} else {
				arrayCandies[i] = 1;
			}
		}
//		for (int i = array.length - 1; i > 0; i--) {
//			if (array[i-1] > array[i] && arrayCandies[i-1] <= arrayCandies[i]) {
//				arrayCandies[i - 1] = arrayCandies[i] + 1;
//			}
//		}
//		if (array[0] > array[1] && arrayCandies[0] <= arrayCandies[1]) {
//			arrayCandies[0] = arrayCandies[1] + 1;
//		}
		int[] arrayCandiesDesc = array.clone();
		arrayCandiesDesc[array.length - 1] = 1;
		for (int i = array.length - 1; i > 0; i--) {
			if (array[i-1] > array[i]) {
				arrayCandiesDesc[i-1] = arrayCandiesDesc[i] + 1;
			} else {
				arrayCandiesDesc[i-1] = 1;
			}
		}
		for (int i = 0; i < array.length; i++) {
			candies+= Math.max(arrayCandies[i], arrayCandiesDesc[i]);
		}
//		for (int candie:arrayCandies) {
//			candies+= candie;
//		}
		return candies;
	}

}
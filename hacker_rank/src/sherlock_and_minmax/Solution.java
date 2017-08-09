package sherlock_and_minmax;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int p = scanner.nextInt();
        int q = scanner.nextInt();

        int currentOffer = p;

        int minSum = Integer.MIN_VALUE;
        int minSumA = -1;
        int minCurrentOffer = p;

        while (currentOffer <= q) {
            if(!(Math.abs(minSumA - currentOffer) < minSum)) {

                boolean swap = true;
                int currentMinSum = Integer.MAX_VALUE;
                int currentMinSumA = -1;

                for (int i = 0; i < a.length; i++) {
                    int currentSum = Math.abs(a[i] - currentOffer);

                    if (currentSum <= minSum) {
                        swap = false;
                        break;
                    }

                    if (currentSum < currentMinSum) {
                        currentMinSum = currentSum;
                        currentMinSumA = a[i];
                    }
                }

                if (swap) {
                    minSum = currentMinSum;
                    minSumA = currentMinSumA;
                    minCurrentOffer = currentOffer;
                }
            }
            currentOffer++;
        }

        System.out.println(minCurrentOffer);

    }

}

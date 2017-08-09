package sherlock_and_minmax;

import java.util.*;

public class Solution {

    public static final int PERCENT_OF_BORDERS = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int p = scanner.nextInt();
        int q = scanner.nextInt();

        int currentOffer = p;

        int minSum = Integer.MIN_VALUE;
        int minSumA = -1;
        int minCurrentOffer = p;

//        Arrays.sort(a);

//        List<Integer> order = getOrder(a);

        for (int i = 0; i < b.length; i++) {
            b[i] = a[i] - p;
        }
        while (currentOffer <= q) {
            if(!(Math.abs(minSumA - currentOffer) < minSum)) {

                boolean swap = true;
                int currentMinSum = Integer.MAX_VALUE;
                int currentMinSumA = -1;

                for (int i = 0; i < a.length; i++) {
                    int currentNumber = a[i];
                    int currentSum = Math.abs(currentNumber - currentOffer);
//                    int currentSum = Math.abs(b[i]--);

                    if (currentSum <= minSum) {
                        swap = false;
                        break;
                    }

                    if (currentSum < currentMinSum) {
                        currentMinSum = currentSum;
                        currentMinSumA = currentNumber;
                    }
                }

                if (swap) {
                    minSum = currentMinSum;
                    minSumA = currentMinSumA;
                    minCurrentOffer = currentOffer;
                    if (minSumA - minCurrentOffer > 0) {
                        currentOffer+= minSum * 2;
                    }
                }
            }

            currentOffer++;
        }

        System.out.println(minCurrentOffer);

    }

    private static List<Integer> getOrder(int[] a) {
        List<Integer> arrayOrderSample = new LinkedList<>();
        int percentOfBorders = PERCENT_OF_BORDERS;
        int sizeOfArray = a.length;
        int sizeOfSample = sizeOfArray * percentOfBorders / 100;
        int endMin = sizeOfSample;
        int startMid = (sizeOfArray - sizeOfSample) / 2;
        int endMid = (sizeOfArray + sizeOfSample) / 2;
        int startMax = sizeOfArray - sizeOfSample;

        addInterval(arrayOrderSample, 0, endMin);
        addInterval(arrayOrderSample, startMid, endMid);
        addInterval(arrayOrderSample, startMax, sizeOfArray);
        addInterval(arrayOrderSample, endMin, startMid);
        addInterval(arrayOrderSample, endMid, startMax);

        return arrayOrderSample;
    }

    private static void addInterval(List<Integer> arrayOrderSample, int start, int end) {
        for (int i = start; i < end; i++) {
            arrayOrderSample.add(i);
        }
    }

}

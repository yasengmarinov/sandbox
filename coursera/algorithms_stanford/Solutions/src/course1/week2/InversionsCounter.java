package course1.week2;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by b06514a on 4/20/2017.
 */
public class InversionsCounter {

    public static void main(String[] args) {

        Path inputFile = Paths.get("src", "course1/week2", "IntegerArray.txt");
        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] intArray = new int[input.size()];
        int[] sorted = new int[intArray.length];

        for (int i = 0; i < input.size(); i++) {
            intArray[i] = Integer.parseInt((String) input.get(i));
            sorted[i] = intArray[i];
        }


        System.out.println(sort(intArray, 0, intArray.length, sorted));
        int a[] = {3, 2, 8, 6, 1, 4, 5, 7};
        int b[] = {3, 2, 8, 6, 1, 4, 5, 7};
        System.out.println(sort(a, 0, a.length, b));

//        for (int i = 0; i < intArray.length - 1; i++) {
//            if (intArray[i] > intArray[i + 1])
//                System.out.println("dejba");
//        }


    }

    private static long sort(final int[] intArray, int start, int end, int[] sorted) {
        long lInv,rInv, mInv;

        if (end - start < 2) {
            sorted[start] = intArray[start];
            return 0;
        }

        int mid = (end + start) / 2;
        lInv = sort(sorted, start, mid, intArray);
        rInv = sort(sorted, mid, end, intArray);

        mInv = merge(sorted, start, mid, end, intArray);

        return lInv + rInv + mInv;
    }

    private static long merge(int[] intArray, int start, int mid, int end, int[] sorted) {

        int li = 0, ri = 0;
        long inv = 0L;

        for (int i = start; i < end; i++) {
            if (ri >= end - mid || li < mid - start && intArray[start + li] <= intArray[mid + ri]) {
                sorted[i] = intArray[start + li++];
            } else {
                sorted[i] = intArray[mid + ri++];
                inv += mid - start - li;
            }
        }

        return inv;
    }
}

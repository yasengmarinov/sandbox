package week3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by yasen on 4/22/17.
 */
public class QuickSort {

    public static void main(String[] args) {

        Path inputFile = Paths.get("src", "week3", "QuickSort.txt");
        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] intArray = new int[input.size()];

        for (int i = 0; i < input.size(); i++) {
            intArray[i] = Integer.valueOf((String) input.get(i));
        }

        System.out.println("First element as pivot: " +
                quickSort(intArray.clone(), 0, intArray.length, new FirstElemPivotPicker()));
        System.out.println("Last element as pivot: " +
                quickSort(intArray.clone(), 0, intArray.length, new LastElemPivotPicker()));
        System.out.println("Median as pivot: " +
                quickSort(intArray.clone(), 0, intArray.length, new MedianPivotPicker()));

    }

    private static long quickSort(int[] array, int start, int end, PivotPicker pivotPicker) {

        if (end - start <= 1) return 0;

        int i = start + 1;

        int pivotIndex = pivotPicker.pick(array, start, end);
        swap(array, pivotIndex, start);

        for (int j = start + 1; j < end; j++) {
            if (array[j] <= array[start])
                swap(array, j, i++);
        }
        swap(array, start, i - 1);

        long ln = quickSort(array, start, i - 1, pivotPicker);
        long rn = quickSort(array, i, end, pivotPicker);

        return end - start - 1 + ln + rn;
    }

    private static void swap(int[] array, int a, int b) {
        if (a == b) return;

        int tmp = array[b];
        array[b] = array[a];
        array[a] = tmp;
    }

}

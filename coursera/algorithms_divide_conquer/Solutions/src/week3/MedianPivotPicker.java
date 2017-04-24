package week3;

import java.util.Arrays;

/**
 * Created by yasen on 4/22/17.
 */
public class MedianPivotPicker implements PivotPicker {
    @Override
    public int pick(int[] array, int start, int end) {
        int med = start + (end - start) / 2 - (((end - start) % 2 == 0)? 1 : 0);

        int[] values = {array[start], array[end - 1], array[med]};
        Arrays.sort(values);

        if (array[start] == values[1]) return start;
        if (array[med] == values[1]) return med;
        if (array[end - 1] == values[1]) return end - 1;

        System.out.println("Ops");
        return 0;
    }
}

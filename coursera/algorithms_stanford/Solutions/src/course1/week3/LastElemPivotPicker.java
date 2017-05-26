package course1.week3;

/**
 * Created by yasen on 4/22/17.
 */
public class LastElemPivotPicker implements PivotPicker {
    @Override
    public int pick(int[] array, int start, int end) {
        return end - 1;
    }
}

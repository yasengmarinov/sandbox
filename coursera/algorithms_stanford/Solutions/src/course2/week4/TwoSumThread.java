package course2.week4;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by yasen on 5/8/17.
 */
public class TwoSumThread implements Callable<Integer> {
    Set<Long> hashTable;
    int start, end;

    public TwoSumThread(Set<Long> hashTable, int start, int end) {
        this.hashTable = hashTable;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        Long startInMs = Calendar.getInstance().getTimeInMillis();

        int pairsCount = 0;

        for (int j = start; j < end; j++) {
            if (pairExists(hashTable, j))
                pairsCount++;
        }

        Long timeInMs = Calendar.getInstance().getTimeInMillis() - startInMs;
        System.out.println("Completed numbers: " + start + " - " + end + ", time passed: " + timeInMs / 1000 + ", pairs: " + pairsCount);

        return pairsCount;
    }

    private boolean pairExists(Set<Long> numbers, int sum) {

        for (Long number :
                numbers) {
            if (numbers.contains(sum - number) && number != sum - number) {
                return true;
            }
        }
        return false;
    }
}

package course2.week4;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by yasen on 5/8/17.
 */
public class TwoSum {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Path inputFile = null;
        try {
            inputFile = Paths.get(new TwoSum().getClass().getResource("2sum.txt").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Long> hashSet = new HashSet<>(input.size(), 0.55F);

        for (Object number :
                input) {
            Long num = Long.valueOf(number.toString());

            hashSet.add(num);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int pairsCount = 0;

        Future<Integer>[] result = new Future[201];
        int futureIndex = 0;

        for (int i = -10000; i <= 10000; i+=100) {
            int max = (i + 100 < 10001) ? i + 100 : 10001;
            Callable<Integer> thread = new TwoSumThread(hashSet, i, max);
            result[futureIndex++] = executorService.submit(thread);}

        for (Future<Integer> future:
            result){
            pairsCount+= future.get();
        }

        System.out.println(pairsCount);

    }

}

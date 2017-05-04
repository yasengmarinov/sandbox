package course2.week3;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by b06514a on 5/4/2017.
 */
public class MedianMaintenance {

    public static void main(String[] args) {

        Path inputFile = null;
        try {
            inputFile = Paths.get(new MedianMaintenance().getClass().getResource("Median.txt").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Heap<Integer> heapMax = new Heap<Integer>(Heap.getMinComparator());
        Heap<Integer> heapMin = new Heap<Integer>(Heap.getMaxComparator());

        int medianSum = 0;

        for (Object line: input) {

            Integer num = Integer.valueOf(line.toString());
            if (heapMax.size() == 0 || num >= heapMax.getFirst())   heapMax.put(num);
            else                                                    heapMin.put(num);
            equalize(heapMin, heapMax);

            if (heapMax.size() > heapMin.size())
                medianSum += heapMax.getFirst();
            else
                medianSum += heapMin.getFirst();
            medianSum = medianSum % 10000;
        }
        System.out.println(medianSum);

    }

    private static void equalize(Heap<Integer> heap1, Heap<Integer> heap2) {
        if (heap1.size() - heap2.size() >= 2)
            heap2.put(heap1.removeFirst());
        else if (heap2.size() - heap1.size() >= 2)
            heap1.put(heap2.removeFirst());
    }

}

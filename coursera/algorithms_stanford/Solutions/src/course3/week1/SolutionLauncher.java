package course3.week1;

import Common.Utils;
import Common.Heap;

import java.util.List;

/**
 * Created by b06514a on 5/12/2017.
 */
public class SolutionLauncher {

    public static void main(String[] args) {

        List<String> input = Utils.parseFile(new SolutionLauncher().getClass(), "jobs.txt");

        Heap<Job> differenceHeap = new Heap<Job>(Job.differenceComparator());
        Heap<Job> ratioHeap = new Heap<Job>(Job.ratioComparator());

        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            int weight = Integer.valueOf(line.split(" ")[0]);
            int length = Integer.valueOf(line.split(" ")[1]);
            differenceHeap.put(new Job(weight, length));
            ratioHeap.put(new Job(weight, length));
        }

        System.out.println("Difference weighted sum: " + summedWeight(differenceHeap));
        System.out.println("Ration weighted sum: " + summedWeight(ratioHeap));

        input = Utils.parseFile(new SolutionLauncher().getClass(), "edges.txt");

        WeightedUndirectedGraph graph = new WeightedUndirectedGraph(Integer.valueOf(input.get(0).split(" ")[0]),
                            Integer.valueOf(input.get(0).split(" ")[1]), input.subList(1, input.size() - 1));
        System.out.println("MSP Cost: " + graph.findMSPCost());

    }

    private static long summedWeight(Heap<Job> heap) {
        long sum = 0;
        int completionTime = 0;
        while (heap.size() != 0) {
            Job job = heap.removeFirst();
            completionTime+= job.getLength();
            sum+= completionTime * job.getWeight();
        }
        return sum;
    }

}

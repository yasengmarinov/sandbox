package course4.week1;

import Common.Utils;

import java.util.List;

/**
 * Created by b06514a on 6/1/2017.
 */
public class SolutionLauncher {

    public static void main(String[] args) {

        String[] files = {"g1.txt", "g2.txt", "g3.txt"};
        for (String file : files)
            runAgainstGraph(file);

    }

    private static void runAgainstGraph(String filename) {
        List<String> input = Utils.parseFile(new SolutionLauncher().getClass(), filename);
        DirectedWeightedGraph graph = new DirectedWeightedGraph(input);
        AppPairsShortestPathFinder shortestFinder = new AppPairsShortestPathFinder(graph);
        System.out.println(shortestFinder.find());
    }

}

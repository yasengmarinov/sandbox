package course4.week1;

import common.Utils;

import java.util.List;

/**
 * Created by b06514a on 6/1/2017.
 * Solves the All-Pairs Shortest Path problem
 * Takes as input directed weighted graph
 * Prints the app-pars shortest path or NULL if negative cycle exists
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

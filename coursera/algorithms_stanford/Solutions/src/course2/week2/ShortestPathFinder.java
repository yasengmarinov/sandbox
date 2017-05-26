package course2.week2;

import course2.week1.SCCFinderLauncher;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by b06514a on 5/2/2017.
 */
public class ShortestPathFinder {

    public static void main(String[] args) {
        Path inputFile = null;
        try {
            inputFile = Paths.get(new ShortestPathFinder().getClass().getResource("dijkstraData.txt").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<List<WeightedEdge>> adjacencyList = new ArrayList<>(input.size());

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i).toString();
            String[] elems = line.split("\t");
            Integer vertex = Integer.valueOf(elems[0]);

            adjacencyList.add(new LinkedList<WeightedEdge>());
            for (int j = 1; j < elems.length; j++) {
                String[] edge = elems[j].split(",");
                adjacencyList.get(vertex - 1).add(new WeightedEdge(vertex, Integer.valueOf(edge[0]), Integer.valueOf(edge[1])));
            }
        }

        System.out.println("Laaaaaame solution");

        int[] weights = new int[adjacencyList.size()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1000000;
        }

        Set<Integer> visitedVertices = new HashSet<>(adjacencyList.size());

        SortedMap<Integer, WeightedEdge> greedyCost = new TreeMap<>();

        // Set initial node
        weights[0] = 0;
        visitedVertices.add(1);
        for (WeightedEdge edge: adjacencyList.get(0)) {
            greedyCost.put(edge.getWeight() + weights[0], edge);
        }

        while (visitedVertices.size() < adjacencyList.size() && !greedyCost.isEmpty()) {
            WeightedEdge edge = greedyCost.remove(greedyCost.firstKey());
            if (!visitedVertices.contains(edge.getHead())) {
                visitedVertices.add(edge.getHead());
                weights[edge.getHead() - 1] = weights[edge.getTail() - 1] + edge.getWeight();
                for (WeightedEdge outgoingEdge: adjacencyList.get(edge.getHead() - 1)) {
                    if (!visitedVertices.contains(outgoingEdge.getHead())) {
                        greedyCost.put(weights[edge.getHead() - 1] + outgoingEdge.getWeight(), outgoingEdge);
                    }
                }
            }
        }

        String indexesToBeReturned = "7,37,59,82,99,115,133,165,188,197";
        String[] indexesArray = indexesToBeReturned.split(",");
        for (String dist: indexesArray)
            System.out.print(weights[Integer.valueOf(dist) - 1] + ",");
    }


}

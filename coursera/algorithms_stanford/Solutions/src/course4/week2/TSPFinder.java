package course4.week2;

import common.tsp.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yasen on 6/3/17.
 */
public class TSPFinder {
    public static final float INFINITY_DISTANCE = 1000000F;
    private int numberOfCities;
    private List<Edge> edges;

    public TSPFinder(int numberOfCities, List<Edge> edges) {
        this.numberOfCities = numberOfCities;
        this.edges = edges;
    }

    public double find() {
        Map<Integer, Float> edgeWeights = new HashMap<>(edges.size());
        for (Edge edge : edges) {
            edgeWeights.put(edge.hashCode(), (float)edge.getDistance());
        }

        Map<Integer, List<Float>> solutions = new HashMap<>();
        initializeSolutions(solutions);

        for (int m = 2; m <= numberOfCities; m++) {
            System.out.println("Starting sets with size " + m);
            for (int s : subsets(m)) {
                if (solutions.get(s) == null) {
                    List<Float> list = new ArrayList<>(numberOfCities);
                    for (int i = 0; i < numberOfCities; i++) {
                        list.add(INFINITY_DISTANCE);
                    }
                    solutions.put(s, list);
                }
                for (int j : citiesInSetExcept(s, 0)) {
                    for (int k : citiesInSetExcept(s, j)) {
                        if (edgeWeights.get(Edge.getHash(j, k)) != null) {
                            float offerValue = solutions.get(common.Utils.bitDown(s, j)).get(k) +
                                    edgeWeights.get(Edge.getHash(j, k));
                            if (offerValue < solutions.get(s).get(j)) {
                                solutions.get(s).set(j, offerValue);
                            }
                        }
                    }
                }
            }
            for (int s : subsets(m - 1)) {
                solutions.remove(s);
            }
        }

        double minCourse = INFINITY_DISTANCE;
        int fullSet = subsets(numberOfCities).get(0);

        for (int i = 1; i < numberOfCities; i++) {
            if (edgeWeights.get(Edge.getHash(0, i)) != null) {
                double offer = solutions.get(fullSet).get(i) + edgeWeights.get(Edge.getHash(0, i));
                if (offer < minCourse)
                    minCourse = offer;
            }
        }

        return minCourse;
    }

    private void initializeSolutions(Map<Integer, List<Float>> solutions) {
        List<Float> list = new ArrayList<>(numberOfCities);
        list.add(0.0F);
        for (int i = 1; i < numberOfCities; i++) {
            list.add(INFINITY_DISTANCE);
        }
        solutions.put(1, list);
    }

    private List<Integer> citiesInSetExcept(int s, int except) {
        List<Integer> listOfCities = new ArrayList<>(numberOfCities);
        for (int i = 0; i < numberOfCities; i++) {
            if ((s & (1 << i)) != 0 && i != except)
                listOfCities.add(i);
        }
        return listOfCities;
    }

    private List<Integer> subsets(int m) {
        List<Integer> subsets = new ArrayList<>();

        subsetRecurse(1, m - 1, subsets, 1);

        return subsets;
    }

    private void subsetRecurse(int start, int m, List<Integer> subsets, int currentNumber) {
        if (m == 0) {
            subsets.add(currentNumber);
            return;
        }
        for (int i = start; i < numberOfCities - m + 1; i++) {
            int withBitUp = common.Utils.bitUp(currentNumber, i);
            subsetRecurse(i + 1, m - 1, subsets, withBitUp);
        }
    }

}

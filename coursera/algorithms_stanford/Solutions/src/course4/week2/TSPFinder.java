package course4.week2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yasen on 6/3/17.
 */
public class TSPFinder {
    private int numberOfCities;
    private List<Edge> edges;

    public TSPFinder(int numberOfCities, List<Edge> edges) {
        this.numberOfCities = numberOfCities;
        this.edges = edges;
    }

    public long find() {
        Map<Integer, Integer> edgeWeights = new HashMap<>(edges.size());
        for (Edge edge : edges) {
            edgeWeights.put(edge.hashCode(), edge.getDistance());
        }

        Map<Integer, List<Integer>> solutions = new HashMap<>();
        initializeSolutions(solutions);

        for (int m = 2; m <= numberOfCities; m++) {
            System.out.println("Starting sets with size " + m);
            for (int s : subsets(m)) {
                if (solutions.get(s) == null) {
                    List<Integer> list = new ArrayList<>(numberOfCities);
                    for (int i = 0; i < numberOfCities; i++) {
                        list.add(1000000);
                    }
                    solutions.put(s, list);
                }
                for (int j : citiesInSetExcept(s, 0)) {
                    for (int k : citiesInSetExcept(s, j)) {
                        if (edgeWeights.get(Edge.getHash(j, k)) != null) {
                            int offerValue = solutions.get(downBit(s, j)).get(k) +
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

        long minCourse = Integer.MAX_VALUE;
        int fullSet = subsets(numberOfCities).get(0);

        for (int i = 1; i < numberOfCities; i++) {
            if (edgeWeights.get(Edge.getHash(0, i)) != null) {
                long offer = solutions.get(fullSet).get(i) + edgeWeights.get(Edge.getHash(0, i));
                if (offer < minCourse)
                    minCourse = offer;
            }
        }

        return minCourse;
    }

    private void initializeSolutions(Map<Integer, List<Integer>> solutions) {
        List<Integer> list = new ArrayList<>(numberOfCities);
        list.add(0);
        for (int i = 1; i < numberOfCities; i++) {
            list.add(10000);
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
            int withBitUp = upBit(currentNumber, i);
            subsetRecurse(i + 1, m - 1, subsets, withBitUp);
        }
    }

    private static int upBit(int currentNumber, int position) {
        int tmp = currentNumber;
        tmp |= (1 << position);
        if (tmp == currentNumber)
            System.out.println("Ops, bit was already 1");
        return tmp;
    }

    private Integer downBit(int s, int position) {
        int tmp = s;
        tmp &= ~(1 << position);
        if (tmp == s)
            System.out.println("Ops, bit was already 0");
        return tmp;
    }

    public static void main(String[] args) {
//        List<Integer> test = subsets(5, 2);
//        System.out.println(citiesInSetExcept(25, 6));
    }
}

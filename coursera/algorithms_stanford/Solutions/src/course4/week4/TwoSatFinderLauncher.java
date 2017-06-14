package course4.week4;

import common.Utils;
import course2.week1.SCCFinder;

import java.util.*;

/**
 * Created by b06514a on 6/14/2017.
 */
public class TwoSatFinderLauncher {

    public static void main(String[] args) {
        String[] inputFileNames = {"2sat1.txt", "2sat2.txt", "2sat3.txt", "2sat4.txt", "2sat5.txt", "2sat6.txt"};
        for (String inputFileName : inputFileNames) {
            findSolution(inputFileName);
        }
    }

    private static void findSolution(String inputFileName) {
        List<String> input = Utils.parseFile(new TwoSatFinderLauncher().getClass(), inputFileName);
        int numberOfVariables = Integer.valueOf(input.get(0));
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>(numberOfVariables * 2);

        Set<Integer> vertices = new HashSet<>();
        for (int i = 1; i < input.size(); i++) {
            String row = input.get(i);
            int v1 = Integer.valueOf(row.split(" ")[0]);
            int v2 = Integer.valueOf(row.split(" ")[1]);
            vertices.add(v1);
            vertices.add(-v1);
            vertices.add(v2);
            vertices.add(-v2);
            addGraphEdgesForClause(adjacencyList, v1, v2);
        }

        System.out.println(new SCCFinder(adjacencyList, vertices).is2SATProblemSolvable());
    }

    private static void addGraphEdgesForClause(Map<Integer, List<Integer>> adjacencyList, int v1, int v2) {
        if (!adjacencyList.containsKey(-v1))
            adjacencyList.put(-v1, new LinkedList<>());
        if (!adjacencyList.containsKey(-v2))
            adjacencyList.put(-v2, new ArrayList<>());
        adjacencyList.get(-v1).add(v2);
        adjacencyList.get(-v2).add(v1);
    }

}

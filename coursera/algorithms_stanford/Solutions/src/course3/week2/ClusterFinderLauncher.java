package course3.week2;

import Common.Heap;
import Common.Utils;

import java.util.*;

/**
 * Created by b06514a on 5/17/2017.
 */
public class ClusterFinderLauncher {

    public static void main(String[] args) {

        LaunchProblem1();
        LaunchProblem2();
    }

    private static void LaunchProblem2() {
        List<String> input = Utils.parseFile(new ClusterFinderLauncher().getClass(), "clustering_big.txt");
        int numberVertices = Integer.valueOf(input.get(0).split(" ")[0]);
        int bitLength = Integer.valueOf(input.get(0).split(" ")[1]);
        Map<Integer, Integer> allEdgesMap = new HashMap<>(numberVertices);
        for (int i = 1; i < input.size(); i++) {
            int[] line = Arrays.stream(input.get(i).split(" ")).mapToInt(Integer::parseInt).toArray();
            int intRepresentation = bitArrayToInt(line);
            if (!allEdgesMap.containsKey(intRepresentation))
                allEdgesMap.put(bitArrayToInt(line),allEdgesMap.size() + 1);
        }

        List<Edge> closeEdges = new ArrayList<>();

        for (Integer node : allEdgesMap.keySet()) {
            for (int similar : withinDistance1(node, allEdgesMap.keySet(), bitLength)) {
                closeEdges.add(new Edge(node, similar, 1));
            }
        }
        for (int node : allEdgesMap.keySet()) {
            for (int similar : withinDistance2(node, allEdgesMap.keySet(), bitLength)) {
                closeEdges.add(new Edge(node, similar, 2));
            }
        }

        UnionFind unionFind = new UnionFind(allEdgesMap.size());
        for (Edge edge : closeEdges) {
            unionFind.union(allEdgesMap.get(edge.getTail()), allEdgesMap.get(edge.getHead()));
        }
        System.out.println("Number of clusters: " + unionFind.clustersNumber());
    }

    private static List<Integer> withinDistance2(int node, Set<Integer> hashSet, int bitLength) {
        List<Integer> similar = new LinkedList<>();
        for (int i = 0; i < bitLength - 1; i++) {
            for (int j = i + 1; j < bitLength; j++) {
                if (hashSet.contains(switchBit(node, i, j)))
                    similar.add(switchBit(node, i, j));
            }
        }
        return similar;
    }

    private static List<Integer> withinDistance1(int node, Set<Integer> hashSet, int bitLength) {
        List<Integer> similar = new LinkedList<>();
        for (int i = 0; i < bitLength; i++) {
            if (hashSet.contains(switchBit(node, i)))
                similar.add(switchBit(node, i));
        }
        return similar;
    }

    private static int switchBit(int number, int bitPos1, int bitPos2) {
        number ^= (1 << bitPos1) | (1 << bitPos2);
        return number;
    }

    private static int switchBit(int number, int bitPos) {
        number ^= (1 << bitPos);
        return number;
    }

    private static Integer bitArrayToInt(int[] bitArray) {
        int multiplicator = 1;
        int sum = 0;
        for (int i = bitArray.length - 1; i >= 0; i--) {
            sum+= bitArray[i] * multiplicator;
            multiplicator*= 2;
        }
        return sum;
    }

    private static void LaunchProblem1() {

        int k = 4;

        List<String> input = Utils.parseFile(new ClusterFinderLauncher().getClass(), "clustering1.txt");

        int numberVertices = Integer.valueOf(input.get(0));

        UnionFind unionFind = new UnionFind(numberVertices);

        Heap<Edge> edges = new Heap<Edge>(Heap.getMinComparator());
        for (int i = 1; i < input.size(); i++) {
            String[] params = input.get(i).split(" ");
            edges.put(new Edge(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2])));
        }

        while (unionFind.clustersNumber() > k) {
            Edge edge = edges.removeFirst();
            unionFind.union(edge.getTail(), edge.getHead());
        }

        Edge minEdge;

        do {
            minEdge = edges.removeFirst();
        } while (unionFind.connected(minEdge.getTail(), minEdge.getHead()));

        System.out.println("Cluster distance: " + minEdge.getWeight());
    }

}

package course3.week2;

import Common.Heap;
import Common.Utils;

import java.util.List;

/**
 * Created by b06514a on 5/17/2017.
 */
public class ClusterFinderLauncher {

    public static void main(String[] args) {

        List<String> input = Utils.parseFile(new ClusterFinderLauncher().getClass(), "clustering1.txt");

        int numberVertices = Integer.valueOf(input.get(0));

        UnionFind unionFind = new UnionFind(numberVertices);

        Heap<Edge> edges = new Heap<Edge>(Heap.getMinComparator());
        for (int i = 1; i < input.size(); i++) {
            String[] params = input.get(i).split(" ");
            edges.put(new Edge(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2])));
        }

        while (unionFind.clustersNumber() > 4) {
            Edge edge = edges.removeFirst();
            unionFind.union(edge.getTail(), edge.getHead());
        }

        System.out.println(edges.getFirst().getWeight());
    }

}

package course3.week1;

import Common.Heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by b06514a on 5/12/2017.
 */
public class WeightedUndirectedGraph {

    List<Edge> edges;
    List<Vertex> vertices;

    public WeightedUndirectedGraph(int verticesCount, int edgesCount, List<String> input) {
        vertices = new ArrayList<>(verticesCount);
        edges = new ArrayList<>(edgesCount);
        for (int i = 0; i < verticesCount; i++) {
            vertices.add(null);
        }
        for (String line : input) {
            String[] elems = line.split(" ");
            int tail = Integer.valueOf(elems[0]);
            int head = Integer.valueOf(elems[1]);
            int weight = Integer.valueOf(elems[2]);

            addVertex(tail);
            addVertex(head);
            Edge edge = new Edge(getVertex(tail), getVertex(head), weight);
            edges.add(edge);
            getVertex(tail).addEdge(edge);
            getVertex(head).addEdge(edge);
        }
    }

    private void addVertex(int vertex) {
        if(vertices.get(vertex - 1) == null) {
            vertices.set(vertex - 1, new Vertex(vertex));
        }
    }

    private Vertex getVertex(int vertex) {
        return vertices.get(vertex - 1);
    }

    private static class Vertex {
        int id;
        List<Edge> edges;

        public Vertex(int id) {
            this.id = id;
            edges = new ArrayList<>();
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }

        public String toString() {
            return String.valueOf(id);
        }

    }

    private static class Edge {
        private Vertex tail, head;
        private int weight;

        public Edge(Vertex tail, Vertex head, int weight) {
            this.tail = tail;
            this.head = head;
            this.weight = weight;
        }

        public String toString() {
            return tail + ", " + head;
        }

    }

    private long findMSPCost() {
        List<Vertex> visitedVertices = new ArrayList<>(vertices.size());
        visitedVertices.add(vertices.get(0));
        
        return 0;
    }

}

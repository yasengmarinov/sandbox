package course4.week1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b06514a on 6/1/2017.
 */
public class DirectedWeightedGraph {

    private List<Vertex> vertices;
    private List<Edge> edges;

    public DirectedWeightedGraph(List<String> adjacencyList) {

        int numberVertices = Integer.valueOf(adjacencyList.get(0).split(" ")[0]);
        int numberEdges = Integer.valueOf(adjacencyList.get(0).split(" ")[1]);

        vertices = new ArrayList<>(numberVertices + 1);
        edges = new ArrayList<>(numberEdges);

        for (int i = 0; i <= numberVertices; i++) {
            vertices.add(new Vertex(i));
        }

        for (int i = 0; i < numberEdges; i++) {
            String[] values = adjacencyList.get(i + 1).split(" ");
            int tail = Integer.valueOf(values[0]);
            int head = Integer.valueOf(values[1]);
            int weight = Integer.valueOf(values[2]);
            edges.add(new Edge(vertices.get(tail), vertices.get(head), weight));
        }

    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public class Vertex {
        private int id;

        public Vertex(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public class Edge {
        private Vertex head, tail;
        private int weight;

        public Edge(Vertex tail, Vertex head, int weight) {
            this.head = head;
            this.tail = tail;
            this.weight = weight;
        }

        public Vertex getHead() {
            return head;
        }

        public Vertex getTail() {
            return tail;
        }

        public int getWeight() {
            return weight;
        }
    }

}

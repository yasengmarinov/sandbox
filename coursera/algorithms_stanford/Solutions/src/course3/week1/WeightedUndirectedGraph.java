package course3.week1;

import common.Heap;

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

    public long findMSPCost() {
        long cost = 0;
        List<Vertex> visitedVertices = new ArrayList<>(vertices.size());
        Heap<Edge> availableEdges = new Heap<Edge>(Edge.weightComparator());

        expandFront(vertices.get(0), visitedVertices, availableEdges);

        while (visitedVertices.size() < vertices.size()) {
            Edge candidate = availableEdges.removeFirst();
            Vertex head = null;
            if (!visitedVertices.contains(candidate.getTail())) {
                head = candidate.getTail();
            } else if (!visitedVertices.contains(candidate.getHead())) {
                head = candidate.getHead();
            }
            if (head != null) {
                cost+= candidate.getWeight();
                expandFront(head, visitedVertices, availableEdges);
            }
        }

        return cost;
    }

    private void expandFront(Vertex vertex, List<Vertex> visitedVertices, Heap<Edge> availableEdges) {
        visitedVertices.add(vertex);
        for (Edge edge: vertex.getEdges()) {
            Vertex connected = edge.getHead().equals(vertex)? edge.getTail() : edge.getHead();
            if (!visitedVertices.contains(connected)) {
                availableEdges.put(edge);
            }
        }
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

        public List<Edge> getEdges() {
            return edges;
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

        public Vertex getTail() {
            return tail;
        }

        public Vertex getHead() {
            return head;
        }

        public int getWeight() {
            return weight;
        }

        public static Comparator<Edge> weightComparator() {
            return (o1, o2) -> Integer.compare(o1.getWeight(), o2.getWeight());
        }

        public String toString() {
            return tail + ", " + head;
        }

    }

}

package course1.week4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasen on 4/24/17.
 */
public class MinimalCut {

    public static void main(String[] args) {

        Path inputFile = Paths.get("src", "course1/week4", "kargerMinCut.txt");
        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Vertex> vertices = new ArrayList<>(input.size());
        for (int i = 1; i <= input.size(); i++) {
            vertices.add(new Vertex(i));
        }
        List<Edge> edges = new ArrayList<>(1000);

        for (int j = 0; j < vertices.size(); j++) {
            String[] numbers = input.get(j).toString().split("\t");
            for (int i = 1; i < numbers.length; i++) {
                if (Integer.valueOf(numbers[i]) > j + 1)
                    edges.add(new Edge(vertices.get(j), vertices.get(Integer.valueOf(numbers[i]) - 1)));
            }
        }
        System.out.println(findMinCut(vertices, edges));

    }

    private static int findMinCut(List<Vertex> vertices, List<Edge> edges) {
        int minCut = edges.size();
        for (int i = 0; i < vertices.size(); i++) {
            int currMin = kargerMinCut(vertices, edges);
            minCut = (currMin < minCut)? currMin : minCut;
        }

        return minCut;
    }

    private static int kargerMinCut(List<Vertex> vertices, List<Edge> edges) {

        List<Vertex> verticesCopy = new ArrayList<>(vertices.size());
        List<Edge> edgesCopy = new ArrayList<>(edges.size());

        for (Vertex vertex : vertices)
            verticesCopy.add(vertex.clone());
        for (Edge edge : edges)
            edgesCopy.add(edge.clone(verticesCopy));

        int verticesCount = verticesCopy.size();
        while (verticesCount > 2) {

            int randomIndex = (int) Math.floor(Math.random() * edgesCopy.size());
            // Remove edge
            Edge removed = edgesCopy.remove(randomIndex);
            // Merge vertices
            mergeVertices(verticesCopy, removed.getVer2().getId(), removed.getVer1().getId());

            // Remove self loops
            removeSelfLoops(edgesCopy);

            verticesCount--;
        }

        return edgesCopy.size();
    }

    private static void mergeVertices(List<Vertex> vertices, int id1, int id2) {
        for (Vertex vertex: vertices) {
            if (vertex.getId() == id1)
                vertex.setId(id2);
        }
    }

    private static void removeSelfLoops(List<Edge> edgesCopy) {
        for (int i = edgesCopy.size() - 1; i >= 0; i--) {
            Edge edge = edgesCopy.get(i);
            if (edge.getVer1().getId() == edge.getVer2().getId()) {
                edgesCopy.remove(i);
            }
        }
    }

    private static class Edge {
        private Vertex ver1, ver2;

        public Edge(Vertex ver1, Vertex ver2) {
            this.ver1 = ver1;
            this.ver2 = ver2;
        }

        public Vertex getVer1() {
            return ver1;
        }

        public Vertex getVer2() {
            return ver2;
        }

        public String toString() {
            return new StringBuilder().append(ver1).
                    append(", ").append(ver2).toString();
        }

        public Edge clone(List<Vertex> vertices) {
            return new Edge(vertices.get(ver1.getId() - 1), vertices.get(ver2.getId() - 1));
        }
    }

    private static class Vertex {
        private int id;
        public Vertex(int n) {
            this.id = n;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String toString() {
            return String.valueOf(id);
        }

        public Vertex clone() {
            return new Vertex(id);
        }
    }

}

package week4;

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

        Path inputFile = Paths.get("src", "week4", "kargerMinCut.txt");
        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Vertex> verticles = new ArrayList(200);
        for (int i = 1; i <= 200; i++) {
            verticles.add(new Vertex(i));
        }
        List<Edge> edges = new ArrayList<>(1000);

        for (int j = 0; j < verticles.size(); j++) {
            String[] numbers = input.get(j).toString().split("\t");
            for (int i = 1; i < numbers.length; i++) {
                edges.add(new Edge(verticles.get(j), verticles.get(Integer.valueOf(numbers[i]) - 1)));
            }
        }
        System.out.println("Done");
    }

    private static class Edge {
        Vertex ver1, ver2;
        public Edge(Vertex ver1, Vertex ver2) {
            this.ver1 = ver1;
            this.ver2 = ver2;
        }
        public String toString() {
            return new StringBuilder().append(ver1).
                    append(", ").append(ver2).toString();
        }
    }

    private static class Vertex {
        int id;
        public Vertex(int n) {
            this.id = n;
        }
        public String toString() {
            return String.valueOf(id);
        }
    }

}

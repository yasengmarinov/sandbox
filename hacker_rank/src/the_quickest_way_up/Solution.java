package the_quickest_way_up;

import java.util.*;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            runInstance(scanner);
        }

    }

    private static void runInstance(Scanner scanner) {
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 1; i <= 100; i++) {
            nodeMap.put(i, new Node(i));
        }
        for (int i = 1; i <= 99; i++) {
            for (int j = 1; j <= 6 && i + j <= 100; j++) {
                nodeMap.get(i).getChildren().add(nodeMap.get(i + j));
            }
        }
        Graph graph = new Graph(nodeMap.get(1));

        int numLadders = scanner.nextInt();
        for (int i = 0; i < numLadders; i++) {
            addShortcut(scanner, nodeMap);
        }
        int numSnakes = scanner.nextInt();
        for (int i = 0; i < numSnakes; i++) {
            addShortcut(scanner, nodeMap);
        }

        int distance = traverseGraph(graph);

        System.out.println(distance < Integer.MAX_VALUE ? distance : -1);
    }

    private static void addShortcut(Scanner scanner, Map<Integer, Node> nodeMap) {
        int head = scanner.nextInt();
        int tail = scanner.nextInt();
        nodeMap.get(head).getShortcuts().add(nodeMap.get(tail));
        nodeMap.get(head).getChildren().clear();
    }

    private static int traverseGraph(Graph graph) {
        int[] distances = new int[101];
        distances[1] = 0;
        for (int i = 2; i < 101; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        exploreNode(graph.getRoot(), distances);
        return distances[100];
    }

    private static void exploreNode(Node currentNode, int[] distances) {
        for (Node child : currentNode.getChildren()) {
            if (distances[child.getId()] > distances[currentNode.getId()] + 1) {
                distances[child.getId()] = distances[currentNode.getId()] + 1;
                exploreNode(child, distances);
            }
        }
        for (Node child : currentNode.getShortcuts()) {
            if (distances[child.getId()] > distances[currentNode.getId()]) {
                distances[child.getId()] = distances[currentNode.getId()];
                exploreNode(child, distances);
            }
        }
    }

    private static class Graph {
        Node root;

        public Graph(Node root) {
            this.root = root;
        }

        public Node getRoot() {
            return root;
        }
    }

    private static class Node {
        int id;
        List<Node> children = new LinkedList<>();
        List<Node> shortCuts = new LinkedList<>();

        public Node(int id) {
            this.id = id;

        }

        public int getId() {
            return id;
        }

        public List<Node> getChildren() {
            return children;
        }

        public List<Node> getShortcuts() {
            return shortCuts;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }

}

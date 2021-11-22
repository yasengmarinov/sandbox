package dijkstrashortreach;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    for (int a0 = 0; a0 < t; a0++) {
      runInstance(in);
    }
  }

  private static void runInstance(Scanner in) {
    int n = in.nextInt();
    Map<Integer, Vertex> vertices = new HashMap<>(n);
    for (int i = 1; i <= n; i++) {
      vertices.put(i, new Vertex(i));
    }
    Map<Vertex, List<Edge>> graphMap = new HashMap<>();
    for (Vertex vertex : vertices.values()) {
      graphMap.put(vertex, new LinkedList<Edge>());
    }

    Set<Long> seen = new HashSet<>();
    int m = in.nextInt();
    for (int a1 = 0; a1 < m; a1++) {
      int x = in.nextInt();
      int y = in.nextInt();
      int r = in.nextInt();

      if (!seen.contains((long) x * 1000000 + y * 1000 + r)) {
        graphMap.get(vertices.get(x)).add(new Edge(vertices.get(x), vertices.get(y), r));
        graphMap.get(vertices.get(y)).add(new Edge(vertices.get(y), vertices.get(x), r));
        seen.add((long) x * 1000000 + y * 1000 + r);
      }
    }
    int s = in.nextInt();

    int[] weights = dijkstraWeights(graphMap, vertices.get(s));

    for (int i = 0; i < weights.length; i++) {
      if (weights[i] != 0) {
        System.out.print((weights[i] < Integer.MAX_VALUE ? weights[i] : -1) + " ");
      }
    }
    System.out.println("");
  }

  private static int[] dijkstraWeights(Map<Vertex, List<Edge>> graphMap, Vertex start) {
    int n = graphMap.size();
    start.setTentativeWeight(0);

    Queue<Vertex> unvisited = new PriorityQueue<>();
    unvisited.addAll(graphMap.keySet());

    while (!unvisited.isEmpty()) {
      Vertex current = unvisited.poll();
      if (current.getTentativeWeight() == Integer.MAX_VALUE) {
        break;
      }
      for (Edge edge : graphMap.get(current)) {
        Vertex tail = edge.getTail();
        if (unvisited.contains(tail)) {
          int weight = edge.getWeight();

          if (current.getTentativeWeight() + weight < tail.getTentativeWeight()) {
            unvisited.remove(tail);
            tail.setTentativeWeight(current.getTentativeWeight() + weight);
            unvisited.add(tail);
          }
        }
      }
    }
    int[] dist = new int[n];
    for (Vertex vertex : graphMap.keySet()) {
      dist[vertex.getId() - 1] = vertex.getTentativeWeight();
    }
    return dist;
  }

  private static class Vertex implements Comparable<Vertex> {
    int id;
    int tentativeWeight;

    public Vertex(int id) {
      this.id = id;
      this.tentativeWeight = Integer.MAX_VALUE;
    }

    public int getId() {
      return id;
    }

    public void setTentativeWeight(int tentativeWeight) {
      this.tentativeWeight = tentativeWeight;
    }

    public int getTentativeWeight() {
      return tentativeWeight;
    }

    @Override
    public int compareTo(Vertex o) {
      return Integer.compare(this.tentativeWeight, o.tentativeWeight);
    }
  }

  private static class Edge {
    Vertex head;
    Vertex tail;
    int weight;

    public Edge(Vertex head, Vertex tail, int weight) {
      this.head = head;
      this.tail = tail;
      this.weight = weight;
    }

    public Vertex getTail() {
      return tail;
    }

    public int getWeight() {
      return weight;
    }
  }
}

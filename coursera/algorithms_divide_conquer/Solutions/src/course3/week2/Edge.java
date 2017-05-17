package course3.week2;

/**
 * Created by b06514a on 5/17/2017.
 */
public class Edge implements Comparable<Edge>{
    private int tail, head, weight;

    public Edge(int tail, int head, int weight) {
        this.tail = tail;
        this.head = head;
        this.weight = weight;
    }

    public int getTail() {
        return tail;
    }

    public int getHead() {
        return head;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.weight);
    }
}

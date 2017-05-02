package course2.week2;

/**
 * Created by b06514a on 5/2/2017.
 */
public class WeightedEdge {

    private int tail, head, weight;

    public WeightedEdge(int tail, int head, int weight) {
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


}

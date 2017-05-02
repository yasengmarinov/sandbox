package course2.week1;

import java.util.Comparator;

/**
 * Created by b06514a on 4/28/2017.
 */
public class Edge {

    public Edge(int tail, int head) {
        this.tail = tail;
        this.head = head;
    }

    int tail, head;

    public int getTail() {
        return tail;
    }

    public int getHead() {
        return head;
    }

    public static Comparator<Edge> forwardComparator() {
        return new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return Integer.compare(o1.getTail(), o2.getTail());
            }
        };
    }

    public static Comparator<Edge> reverseComparator() {
        return new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return Integer.compare(o1.getHead(), o2.getHead());
            }
        };
    }

}

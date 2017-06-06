package common.tsp;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by b06514a on 6/6/2017.
 */
public class DirectedEdge implements Serializable{
    int head;
    double distance;

    public DirectedEdge(int head, double distance) {
        this.head = head;
        this.distance = distance;
    }

    public int getHead() {
        return head;
    }

    public double getDistance() {
        return distance;
    }

    public static Comparator<DirectedEdge> distanceAndCityWise() {
        return new Comparator<DirectedEdge>() {
            @Override
            public int compare(DirectedEdge o1, DirectedEdge o2) {
                if (o1.distance != o2.distance)
                    return Double.compare(o1.distance, o2.distance);
                return Integer.compare(o1.getHead(), o2.getHead());
            }
        };
    }
}

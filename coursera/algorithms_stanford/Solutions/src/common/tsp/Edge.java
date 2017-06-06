package common.tsp;

/**
 * Created by yasen on 6/3/17.
 */
public class Edge implements Comparable<Edge>{
    int city1, city2;
    double distance;

    public Edge(int city1, int city2, double distance) {
        this.city1 = city1;
        this.city2 = city2;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Edge edge) {
        return Double.compare(this.distance, edge.distance);
    }

    @Override
    public int hashCode() {
        return getHash(city1, city2);
    }

    public static int getHash(int city1, int city2) {
        return (city1 <= city2) ? city1 * 100 + city2 : city2 * 100 + city1;
    }
}

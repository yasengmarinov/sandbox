import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by b06514a on 2/22/2017.
 */
public class PointSET {

    private Set<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    // draw all points to standard draw
    public void draw() {

    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {}

}
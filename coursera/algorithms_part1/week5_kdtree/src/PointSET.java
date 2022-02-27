import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
        if (p == null) throw new NullPointerException();
        points.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        for (Point2D point: points) {
            point.draw();
        }
    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        Set<Point2D> pointsInRect = new TreeSet<>();
        for (Point2D point: points) {
            if (rect.contains(point)) {
                pointsInRect.add(point);
            }
        }
        return pointsInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty())
            return null;
        double minDistance = 0;
        Point2D nearest = null;
        for (Point2D point: points) {
            if (nearest == null || p.distanceTo(point) < minDistance) {
                minDistance = p.distanceTo(point);
                nearest = point;
            }
        }
        return nearest;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET set = new PointSET();
        System.out.println(set.size());
        System.out.println(set.isEmpty());
        System.out.println(set.nearest(new Point2D(0.294, 0.879)));

    }

}
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by b06514a on 1/24/2017.
 */
public class FastCollinearPoints {

    private LineSegment[] segments;
    private int segmentsIndex;
    private Point[] pointsArray;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) throws Exception{
        validatePoints(points);
        this.pointsArray = points.clone();
        segments = new LineSegment[pointsArray.length];
        segmentsIndex = 0;

        Arrays.sort(pointsArray);
        for (int i = 0; i < pointsArray.length; i++) {
            Point[] pointsBySlope = pointsBySlopeAgainstPoint(i, pointsArray);
            int seq = 1;
            for (int j = 0; j < pointsBySlope.length; j++) {
                if (j < pointsBySlope.length - 1 && pointsArray[i].slopeTo(pointsBySlope[j]) == pointsArray[i].slopeTo(pointsBySlope[j + 1])) {
                    seq++;
                } else {
                    if (seq >= 3 && pointsArray[i].compareTo(pointsBySlope[j - seq + 1]) <= 0) {
                        AddNewSegment(new LineSegment(pointsArray[i], pointsBySlope[j]));
                    }
                    seq = 1;
                }
            }
        }

        ReduceSegments();
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    private Point[] pointsBySlopeAgainstPoint(int i, Point[] points) {
        Point[] pointsBySlope = points.clone();
        sortBySlope(pointsBySlope, i);
        return pointsBySlope;
    }

    private void sortBySlope(Point[] pointsBySlope, int i) {
        Point[] aux = new Point[pointsBySlope.length];
        Comparator<Point> comparator = pointsBySlope[i].slopeOrder();
        sort(pointsBySlope, aux, comparator, 0, pointsBySlope.length - 1);
    }

    private void sort(Point[] a, Point[] aux, Comparator<Point> comparator, int low, int high) {
        if (low >= high)
            return;
        int mid = low + (high - low) / 2;
        sort(a, aux, comparator, low, mid);
        sort(a, aux, comparator, mid + 1, high);
        merge(a, aux, comparator, low, mid, high);
    }

    private void merge(Point[] a, Point[] aux, Comparator<Point> comparator, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            aux[i] = a[i];
        }
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > high)
                a[k] = aux[i++];
            else if (comparator.compare(aux[j], aux[i]) < 0)
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    private void validatePoints(Point[] points) throws Exception{
        if (points == null)
            throw new NullPointerException();

        for (Point point:points)
            if (point == null)
                throw new NullPointerException();

        Point[] tmp = points.clone();
        Arrays.sort(tmp);
        for (int i = 0; i < tmp.length - 1; i++) {
            if (tmp[i].compareTo(tmp[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

    }

    private void AddNewSegment(LineSegment lineSegment) {
        if (segmentsIndex == segments.length) {
            LineSegment[] newSegments = new LineSegment[segments.length * 2];
            for (int m = 0; m < segments.length; m++) {
                newSegments[m] = segments[m];
            }
            segments = newSegments;
        }
        segments[segmentsIndex++] = lineSegment;
    }

    private void ReduceSegments() {
        if (segmentsIndex != segments.length) {
            LineSegment[] newSegments = new LineSegment[segmentsIndex];
            for (int m = 0; m < segmentsIndex; m++) {
                newSegments[m] = segments[m];
            }
            segments = newSegments;
        }
    }
}
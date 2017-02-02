import java.util.Arrays;

/**
 * Created by b06514a on 1/24/2017.
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int segmentsIndex;
    private Point[] pointsArray;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) throws Exception{
        validatePoints(points);
        pointsArray = points.clone();
        segments = new LineSegment[pointsArray.length];
        segmentsIndex = 0;

        Arrays.sort(pointsArray);
        for (int i = 0; i < pointsArray.length - 3 ; i++) {
            for (int j = i+1; j < pointsArray.length - 2 && j != i; j++) {
                for (int k = j+1; k < pointsArray.length - 1 && k != i && k != j; k++) {
                    for (int l = k+1; l < pointsArray.length && l != i && l != j && l != k; l++) {
                        if (pointsArray[i].slopeTo(pointsArray[j]) == pointsArray[i].slopeTo(pointsArray[k]) &&
                                pointsArray[i].slopeTo(pointsArray[k]) == pointsArray[i].slopeTo(pointsArray[l])) {
                            AddNewSegment(new LineSegment(pointsArray[i], pointsArray[l]));
                        }
                    }
                }
            }
        }

        ReduceSegments();
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

    private LineSegment getLineSegment(Point point0, Point point1, Point point2, Point point3) {
        Point[] points = {point0, point1, point2, point3};
        for (int i = 1; i < points.length; i++) {
            int currentIndex = i;
            while (currentIndex > 0 && points[currentIndex - 1].compareTo(points[currentIndex]) > 0) {
                Point tmp = points[currentIndex - 1];
                points[currentIndex - 1] = points[currentIndex];
                points[currentIndex] = tmp;
            }
        }

        return new LineSegment(points[0], points[points.length - 1]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
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
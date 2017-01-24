/**
 * Created by b06514a on 1/24/2017.
 */
public class BruteCollinearPoints {

    LineSegment[] segments = new LineSegment[0];

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length && j != i; j++) {
                for (int k = 0; k < points.length && k != i && k != j; k++) {
                    for (int l = 0; l < points.length && l != i && l != j && l != k; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            LineSegment[] newSegments = new LineSegment[segments.length + 1];
                            for (int m = 0; m < segments.length; m++) {
                                newSegments[m] = segments[m];
                            }
                            newSegments[segments.length] = new LineSegment(points[i], points[l]);
                            segments = newSegments;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}
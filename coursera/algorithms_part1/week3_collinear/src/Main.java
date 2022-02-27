import edu.princeton.cs.algs4.*;
/**
 * Created by b06514a on 1/25/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // read the n points from a file
        String tmp = "C:\\Users\\b06514a\\sandbox_project\\coursera\\algorythms_part1\\week3_collinear\\inpput.txt";
        In in = new In(tmp);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by b06514a on 2/23/2017.
 */
public class KdTree {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();

        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
            size++;
            return;
        }

        boolean verticalSplit = true;
        Node currentNode = root;
        while (true) {
            double insertKey, presentKey;
            if (verticalSplit) {
                insertKey = p.x();
                presentKey = currentNode.getP().x();
            } else {
                insertKey = p.y();
                presentKey = currentNode.getP().y();
            }

            if (insertKey < presentKey) {
                if (currentNode.getLb() != null) {
                    currentNode = currentNode.getLb();
                } else {
                    currentNode.setLb(new Node(p, splitRect(currentNode.getRect(), verticalSplit, true, currentNode.getP()), null, null));
                    size++;
                    break;
                }
            } else {
                if (insertKey == presentKey && currentNode.getP().equals(p)) return;
                if (currentNode.getRt() != null) {
                    currentNode = currentNode.getRt();
                } else {
                    currentNode.setRt(new Node(p, splitRect(currentNode.getRect(), verticalSplit, false, currentNode.getP()), null, null));
                    size++;
                    break;
                }
            }
            verticalSplit = !verticalSplit;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        Node currentNode = root;
        boolean verticalSplit = true;
        while (currentNode != null) {
            double insertKey, presentKey;
            if (verticalSplit) {
                insertKey = p.x();
                presentKey = currentNode.getP().x();
            } else {
                insertKey = p.y();
                presentKey = currentNode.getP().y();
            }
            if (insertKey < presentKey) {
              currentNode = currentNode.getLb();
            } else {
                if (currentNode.getP().equals(p))
                    return true;
                currentNode = currentNode.getRt();
            }
            verticalSplit = !verticalSplit;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        drawTree(root, true);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Set<Point2D> pointsInRange = new TreeSet<>();
        findPointsInNode(root, rect, pointsInRange);

        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;

        Node currentNode = root;
        Point2D nearestPoint[] = {root.getP()};

        nearestPointInTree(currentNode, p, nearestPoint, true);

        return nearestPoint[0];
    }

    private void nearestPointInTree(Node currentNode, Point2D p, Point2D[] nearestPoint, boolean verticalSplit) {
        Double minDistance = nearestPoint[0].distanceTo(p);
        if (currentNode.getP().distanceTo(p) < minDistance) {
            minDistance = currentNode.getP().distanceTo(p);
            nearestPoint[0] = currentNode.getP();
        }

        double insertKey, presentKey;
        if (verticalSplit) {
            insertKey = p.x();
            presentKey = currentNode.getP().x();
        } else {
            insertKey = p.y();
            presentKey = currentNode.getP().y();
        }

        if (insertKey < presentKey) {
            if (currentNode.getLb() != null && currentNode.getLb().getRect().distanceTo(p) < minDistance)
                nearestPointInTree(currentNode.getLb(), p, nearestPoint, !verticalSplit);
            if (currentNode.getRt() != null && currentNode.getRt().getRect().distanceTo(p) < minDistance)
                nearestPointInTree(currentNode.getRt(), p, nearestPoint, !verticalSplit);
        } else {
            if (currentNode.getRt() != null && currentNode.getRt().getRect().distanceTo(p) < minDistance)
                nearestPointInTree(currentNode.getRt(), p, nearestPoint, !verticalSplit);
            if (currentNode.getLb() != null && currentNode.getLb().getRect().distanceTo(p) < minDistance)
                nearestPointInTree(currentNode.getLb(), p, nearestPoint, !verticalSplit);
        }
    }


    private void drawTree(Node currentNode, boolean verticalSplit) {
        drawNode(currentNode, verticalSplit);

        if (currentNode.getLb() != null)
            drawTree(currentNode.getLb(), !verticalSplit);
        if (currentNode.getRt() != null)
            drawTree(currentNode.getRt(), !verticalSplit);
    }

    private void drawNode(Node node, boolean verticalSplit) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.getP().x(), node.getP().y());

        if (verticalSplit) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.getP().x(), node.getRect().ymin(), node.getP().x(), node.getRect().ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.getRect().xmin(), node.getP().y(), node.getRect().xmax(), node.getP().y());
        }
    }

    private RectHV splitRect(RectHV rect, boolean verticalSplit, boolean isLb, Point2D splitPoint) {
        RectHV toReturn;
        if (verticalSplit) {
            if (isLb) {
                toReturn = new RectHV(rect.xmin(), rect.ymin(), splitPoint.x(), rect.ymax());
            } else {
                toReturn = new RectHV(splitPoint.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        } else {
            if (isLb) {
                toReturn = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), splitPoint.y());
            } else {
                toReturn = new RectHV(rect.xmin(), splitPoint.y(), rect.xmax(), rect.ymax());
            }
        }
        return toReturn;
    }

    private void findPointsInNode(Node node, RectHV rect, Set<Point2D> pointsInRange) {
        if (node == null) return;

        if (rect.contains(node.getP()))
            pointsInRange.add(node.getP());
        if (node.getLb() != null && node.getLb().getRect().intersects(rect))
            findPointsInNode(node.getLb(), rect, pointsInRange);
        if (node.getRt() != null && node.getRt().getRect().intersects(rect))
            findPointsInNode(node.getRt(), rect, pointsInRange);
    }

    public static void main(String[] args) {

        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        System.out.println(kdTree.contains(new Point2D(0.7, 0.2)));
        kdTree.insert(new Point2D(0.5, 0.4));
        System.out.println(kdTree.contains(new Point2D(0.9, 0.6)));
        kdTree.insert(new Point2D(0.2, 0.3));
        System.out.println(kdTree.contains(new Point2D(0.9, 0.6)));
        kdTree.insert(new Point2D(0.4, 0.7));
        System.out.println(kdTree.contains(new Point2D(0.9, 0.6)));
        kdTree.insert(new Point2D(0.9, 0.6));
        System.out.println(kdTree.contains(new Point2D(0.9, 0.6)));
        System.out.println(kdTree.nearest(new Point2D(0, 0)));
        System.out.println("done");
//        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
//        StdDraw.enableDoubleBuffering();
//        KdTree kdtree = new KdTree();
//        while (true) {
//            if (StdDraw.mousePressed()) {
//                double x = StdDraw.mouseX();
//                double y = StdDraw.mouseY();
//                StdOut.printf("%8.6f %8.6f\n", x, y);
//                Point2D p = new Point2D(x, y);
//                if (rect.contains(p)) {
//                    StdOut.printf("%8.6f %8.6f\n", x, y);
//                    kdtree.insert(p);
//                    StdDraw.clear();
//                    kdtree.draw();
//                    StdDraw.show();
////                    Point2D nearest = kdtree.nearest(new Point2D(0,0));
////                    StdOut.printf("%8.6f %8.6f\n", nearest.x(), nearest.y());
//                }
//            }
//            StdDraw.pause(50);
//        }
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }

        public Point2D getP() {
            return p;
        }

        public void setP(Point2D p) {
            this.p = p;
        }

        public RectHV getRect() {
            return rect;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

        public Node getLb() {
            return lb;
        }

        public void setLb(Node lb) {
            this.lb = lb;
        }

        public Node getRt() {
            return rt;
        }

        public void setRt(Node rt) {
            this.rt = rt;
        }
    }
}

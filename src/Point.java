/**
 * Author: katooshka
 * Date: 2/20/16.
 */

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.setPenRadius(.01);
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.setPenRadius(.003);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x){
            return Double.NEGATIVE_INFINITY;
        }
        if (that.y == this.y) {
            return +0.0;
        }
        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        return 1.0 * (that.y - this.y) / (that.x - this.x);
    }

    @Override
    public int compareTo(Point that) {
        int comp = Integer.compare(y, that.y);
        if (comp != 0) return comp;
        return Integer.compare(x, that.x);
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(slopeTo(p1), slopeTo(p2));
            }
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In("input.txt");
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

    }
}

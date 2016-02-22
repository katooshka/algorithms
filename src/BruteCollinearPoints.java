import java.util.*;
import java.util.List;

/**
 * Author: katooshka
 * Date: 2/20/16.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        points = Arrays.copyOf(points, points.length);
        Arrays.sort(points);
        for (Point point : points) {
            if (point == null) throw new NullPointerException();
        }
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();
        }
        List<LineSegment> lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (checkCollinear(points[i], points[j], points[k], points[l])) {
                            lineSegments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
        this.lineSegments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private boolean checkCollinear(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d);
    }
}


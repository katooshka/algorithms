import javax.swing.text.Segment;
import java.util.*;

/**
 * Author: katooshka
 * Date: 2/21/16.
 */
public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
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
        for (Point basePoint : points) {
            Point[] arrayCopy = Arrays.copyOf(points, points.length);
            Comparator<Point> order = basePoint.slopeOrder();
            Arrays.sort(arrayCopy, order);
            for (int i = 0; i < arrayCopy.length; ) {
                int equalSlopesPoints = countEqualSlopesPoints(arrayCopy, basePoint, i);
                if (equalSlopesPoints >= 3) {
                    List<Point> sameLinePoints = sameLinePoints(basePoint, arrayCopy, i, equalSlopesPoints);
                    lineSegments.add(new LineSegment(Collections.min(sameLinePoints), Collections.max(sameLinePoints)));
                }
                i = i + equalSlopesPoints;
            }
        }
        this.lineSegments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private List<Point> sameLinePoints(Point basePoint, Point[] points, int start, int length) {
        List<Point> result = new ArrayList<Point>();
        result.add(basePoint);
        result.addAll(Arrays.asList(points).subList(start, start + length));
        return result;
    }

    private int countEqualSlopesPoints(Point[] points, Point basePoint, int index) {
        int counter = 1;
        double baseSlope = basePoint.slopeTo(points[index]);
        for (int i = index + 1; i < points.length; i++) {
            if (Math.abs(basePoint.slopeTo(points[i]) - baseSlope) > 1e-6) break;
            counter++;
        }
        return counter;
    }

}

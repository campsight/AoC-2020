import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day3_2019 {
    private static String COMMA_DELIMITER = ",";
    private static int HORIZONTAL = 0;
    private static int VERTICAL = 1;

    public static Point parseInstruction(String instruction, Point start) {
        String dir = instruction.substring(0,1);
        int dist = Integer.parseInt(instruction.substring(1));
        Direction d = Direction.valueOfLabel(dir);
        return new Point(start.x + d.x * dist, start.y + d.y * dist);
    }

    public static Point intersectionPoint(Point l1_p1, Point l1_p2, Point l2_p1, Point l2_p2) {
        int dir_l1 = (l1_p1.x == l1_p2.x) ? VERTICAL : HORIZONTAL;
        int dir_l2 = (l2_p1.x == l2_p2.x) ? VERTICAL : HORIZONTAL;
        if (dir_l1 == dir_l2) return null;
        if (dir_l1 == HORIZONTAL) {
            int x1 = Math.min(l1_p1.x, l1_p2.x);
            int x2 = Math.max(l1_p1.x, l1_p2.x);
            int xi = l2_p1.x;
            if ((xi < x1) || (xi > x2)) return null;
            int y1 = Math.min(l2_p1.y, l2_p2.y);
            int y2 = Math.max(l2_p1.y, l2_p2.y);
            int yi = l1_p1.y;
            if ((yi < y1) || (yi > y2)) return null;
            // System.out.println(l1_p1.toString() + l1_p2.toString() + l2_p1.toString() + l2_p1.toString() + "intersect: " + xi + ", " + yi);
            return new Point(xi, yi);
        } else {
            int x1 = Math.min(l2_p1.x, l2_p2.x);
            int x2 = Math.max(l2_p1.x, l2_p2.x);
            int xi = l1_p1.x;
            if ((xi < x1) || (xi > x2)) return null;
            int y1 = Math.min(l1_p1.y, l1_p2.y);
            int y2 = Math.max(l1_p1.y, l1_p2.y);
            int yi = l2_p1.y;
            if ((yi < y1) || (yi > y2)) return null;
            // System.out.println(l1_p1.toString() + l1_p2.toString() + l2_p1.toString() + l2_p1.toString() + "intersect: " + xi + ", " + yi);
            return new Point(xi, yi);
        }
    }

    public static LinkedList<Point> getListOfPoints(ArrayList<String> wire) {
        LinkedList<Point> w_points = new LinkedList<>();
        Point previousPoint = new Point(0,0);
        w_points.add(previousPoint);
        for (String s : wire) {
            previousPoint = parseInstruction(s, previousPoint);
            w_points.add(previousPoint);
        }
        return w_points;
    }

    public static int mDistance(Point p) {
        return (Math.abs(p.x) + Math.abs(p.y));
    }

    public static Point getClosestPoint(ArrayList<Point> points) {
        Iterator<Point> pointIterator = points.iterator();
        Point closestPoint = pointIterator.next();
        int m_dist = mDistance(closestPoint);
        while (pointIterator.hasNext()) {
            Point nextPoint = pointIterator.next();
            int next_dist = mDistance(nextPoint);
            if (next_dist < m_dist) {
                m_dist = next_dist;
                closestPoint = new Point(nextPoint);
            }
        }
        return closestPoint;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("resources\\input_day3.txt"));
        ArrayList<String> wire1 = new ArrayList<>(Arrays.asList(input.nextLine().split(",")));
        ArrayList<String> wire2 = new ArrayList<>(Arrays.asList(input.nextLine().split(",")));
        System.out.println(wire1);
        System.out.println(wire2);

        LinkedList<Point> w_points_1 = getListOfPoints(wire1);
        LinkedList<Point> w_points_2 = getListOfPoints(wire2);
        System.out.println(w_points_1);
        System.out.println(w_points_2);

        Iterator<Point> line1iterator = w_points_1.iterator();
        Point l1p0 = line1iterator.next();
        Point l1p1, l2p0, l2p1;
        ArrayList<Point> intersectionPoints = new ArrayList<>();
        while (line1iterator.hasNext()) {
            l1p1 = line1iterator.next();
            Iterator<Point> line2iterator = w_points_2.iterator();
            l2p0 = line2iterator.next();
            while (line2iterator.hasNext()) {
                l2p1 = line2iterator.next();
                Point iPoint = intersectionPoint(l1p0, l1p1, l2p0, l2p1);
                if ((iPoint != null) && !((iPoint.x == 0) && (iPoint.y == 0))) intersectionPoints.add(iPoint);
                l2p0 = new Point(l2p1);
            }
            l1p0 = new Point(l1p1);
        }
        System.out.println(intersectionPoints);
        Point solution = getClosestPoint(intersectionPoints);
        System.out.println(solution.toString() + mDistance(solution));
    }
}

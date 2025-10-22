package academy.maze.dto;

import java.util.Objects;

/**
 * Координаты точки
 *
 * @param x
 * @param y
 */
public record Point(int x, int y) {

    public static Point createPoint(int x, int y) {
        return new Point(x, y);
    }

    public static Point sumOfVal(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static Point halfOfVal(Point p) {
        return new Point(p.x / 2, p.y / 2);
    }
    public static Point sumOfValAndHalf(Point a, Point b) {
        return new Point(a.x + b.x / 2, a.y + b.y / 2);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point subtract(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    public int manhattanDistanceTo(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Point point = (Point) object;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point {" + x + ", " + y + '}';
    }

}

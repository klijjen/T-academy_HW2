package academy.maze.dto;

import java.util.Arrays;
import java.util.List;

/**
 * Путь в лабиринте. Точки в лабиринте находятся в порядке следования. Первой точкой является стартовая, последней —
 * финишная.
 *
 * @param points координаты точек пути.
 */
public record Path(List<Point> points) {
    public Path(Point[] points) {
        this(Arrays.asList(points));
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int length() {
        return points.size();
    }

    public Point getStart() {
        return points.getFirst();
    }

    public Point getEnd() {
        return points.getLast();
    }

    public static Path empty() {
        return new Path(new Point[0]);
    }

}

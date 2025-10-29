package academy.maze.dto;

import java.util.Arrays;
import java.util.List;

/**
 * Путь в лабиринте. Точки в лабиринте находятся в порядке следования. Первой точкой является стартовая, последняя —
 * финишная.
 *
 * @param points координаты точек пути.
 */
public record Path(List<Point> points) {

    /**
     * Создает путь из массива точек.
     *
     * @param points массив точек пути
     */
    public Path(Point[] points) {
        this(Arrays.asList(points));
    }

    /**
     * Проверяет, является ли путь пустым.
     *
     * @return true если путь не содержит точек, false в противном случае
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Возвращает длину пути (количество точек).
     *
     * @return количество точек в пути
     */
    public int length() {
        return points.size();
    }

    /**
     * Возвращает начальную точку пути.
     *
     * @return первая точка пути
     * @throws IndexOutOfBoundsException если путь пуст
     */
    public Point getStart() {
        return points.getFirst();
    }

    /**
     * Возвращает конечную точку пути.
     *
     * @return последняя точка пути
     * @throws IndexOutOfBoundsException если путь пуст
     */
    public Point getEnd() {
        return points.getLast();
    }

    /**
     * Создает пустой путь.
     *
     * @return путь без точек
     */
    public static Path empty() {
        return new Path(new Point[0]);
    }
}

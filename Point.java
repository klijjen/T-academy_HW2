package academy.maze.dto;

import java.util.Objects;

/**
 * Координаты точки
 *
 * @param x координата X
 * @param y координата Y
 */
public record Point(int x, int y) {

    /**
     * Создает новую точку с указанными координатами.
     *
     * @param x координата X
     * @param y координата Y
     * @return новая точка с заданными координатами
     */
    public static Point createPoint(int x, int y) {
        return new Point(x, y);
    }

    /**
     * Складывает координаты двух точек.
     *
     * @param a первая точка
     * @param b вторая точка
     * @return новая точка с координатами (a.x + b.x, a.y + b.y)
     */
    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    /**
     * Вычисляет среднюю точку между двумя точками.
     *
     * @param a первая точка
     * @param b вторая точка
     * @return точка с координатами ((a.x + b.x) / 2, (a.y + b.y) / 2)
     */
    public static Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /**
     * Смешивает координаты двух точек.
     * Вычисляет точку с координатами (a.x + b.x / 2, a.y + b.y / 2)
     *
     * @param a первая точка
     * @param b вторая точка
     * @return новая точка со смешанными координатами
     */
    public static Point blend(Point a, Point b) {
        return new Point(a.x + b.x / 2, a.y + b.y / 2);
    }

    /**
     * Вычисляет манхэттенское расстояние до другой точки.
     *
     * @param other другая точка
     * @return манхэттенское расстояние |x1 - x2| + |y1 - y2|
     */
    public int manhattanDistanceTo(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    /**
     * Сравнивает эту точку с другим объектом на равенство.
     *
     * @param object объект для сравнения
     * @return true если объект является точкой с такими же координатами
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Point point = (Point) object;
        return x == point.x && y == point.y;
    }

    /**
     * Возвращает хэш-код точки.
     *
     * @return хэш-код, вычисленный на основе координат
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Возвращает строковое представление точки.
     *
     * @return строка в формате "Point {x, y}"
     */
    @Override
    public String toString() {
        return "Point {" + x + ", " + y + '}';
    }
}

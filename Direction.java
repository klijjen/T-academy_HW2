package academy.maze.solver;

import academy.maze.dto.Point;

/**
 * Направления движения для алгоритмов решения лабиринтов.
 * Используется для навигации между соседними ячейками.
 */
public enum Direction {
    /** Направление вверх */
    UP(0, -1),

    /** Направление вниз */
    DOWN(0, 1),

    /** Направление влево */
    LEFT(-1, 0),

    /** Направление вправо */
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    /**
     * Создает направление с указанными смещениями.
     *
     * @param dx смещение по оси X
     * @param dy смещение по оси Y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Преобразует направление в точку со смещениями.
     *
     * @return точка с координатами (dx, dy)
     */
    public Point toPoint() {
        return new Point(dx, dy);
    }
}

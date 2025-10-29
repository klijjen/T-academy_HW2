package academy.maze.solver.Iterator;

import academy.maze.dto.Point;
import java.util.Iterator;

/**
 * Интерфейс итератора для обхода соседних ячеек в алгоритмах решения лабиринтов.
 * Расширяет стандартный Iterator для работы с точками (Point) лабиринта.
 */
public interface NeighborIteratorInterface extends Iterator<Point> {

    /**
     * Проверяет наличие следующей соседней ячейки.
     *
     * @return true если есть следующая соседняя ячейка, false в противном случае
     */
    boolean hasNext();

    /**
     * Возвращает следующую соседнюю ячейку.
     *
     * @return следующая точка (Point) соседней ячейки
     * @throws java.util.NoSuchElementException если больше нет соседних ячеек
     */
    Point next();
}

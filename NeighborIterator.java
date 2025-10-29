package academy.maze.solver.Iterator;

import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static academy.maze.dto.Point.add;

/**
 * Итератор для обхода проходимых соседних ячеек в лабиринте.
 * Реализует обход в 4 направлениях (вверх, вниз, влево, вправо).
 */
public class NeighborIterator implements NeighborIteratorInterface {
    private final List<Point> neighbors;
    private int index;

    /**
     * Создает итератор соседних ячеек для указанной точки.
     *
     * @param point точка, для которой ищутся соседи
     * @param maze лабиринт, в котором происходит поиск
     */
    public NeighborIterator(Point point, Maze maze) {
        this.neighbors = new ArrayList<>();
        this.index = 0;
        initNeighbors(point, maze);
    }

    /**
     * Инициализирует список валидных соседних ячеек.
     *
     * @param point исходная точка
     * @param maze лабиринт для проверки валидности
     */
    private void initNeighbors(Point point, Maze maze) {
        for (Direction dir : Direction.values()) {
            Point neighbor = add(point, dir.toPoint());
            if (isValidNeighbor(neighbor, maze)) {
                neighbors.add(neighbor);
            }
        }
    }

    /**
     * Проверяет, является ли соседняя ячейка валидной для перемещения.
     *
     * @param neighbor соседняя точка для проверки
     * @param maze лабиринт
     * @return true если ячейка находится в пределах лабиринта и проходима
     */
    private boolean isValidNeighbor(Point neighbor, Maze maze) {
        return maze.isValidPosition(neighbor) && maze.getCell(neighbor).isPassable();
    }

    /**
     * Проверяет наличие следующей соседней ячейки.
     *
     * @return true если есть следующая соседняя ячейка, false в противном случае
     */
    @Override
    public boolean hasNext() {
        return index < neighbors.size();
    }

    /**
     * Возвращает следующую соседнюю ячейку.
     *
     * @return следующая точка соседней ячейки
     * @throws NoSuchElementException если больше нет соседних ячеек
     */
    @Override
    public Point next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Point neighbor = neighbors.get(index);
        index++;
        return neighbor;
    }
}

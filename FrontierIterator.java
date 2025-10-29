package academy.maze.generator.Iterator;

import academy.maze.dto.CellType;
import academy.maze.generator.Direction;
import academy.maze.dto.Edge;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import static academy.maze.dto.Point.add;
import static academy.maze.dto.Point.blend;

/**
 * Итератор границы для алгоритмов генерации лабиринтов.
 * Реализует случайный выбор ребер из границы для создания случайных путей.
 */
public class FrontierIterator implements FrontierIteratorInterface {
    private final List<Edge> frontiers;
    private final Random random;
    private final Maze maze;

    /**
     * Создает итератор границы с указанными параметрами.
     *
     * @param random генератор случайных чисел для случайного выбора ребер
     * @param maze лабиринт, для которого создается итератор
     */
    public FrontierIterator(Random random, Maze maze) {
        this.frontiers = new ArrayList<>();
        this.random = random;
        this.maze = maze;
    }

    /**
     * Добавляет все возможные ребра из указанной точки в границу.
     * Для каждого направления проверяет соседнюю ячейку и добавляет ребро,
     * если соседняя ячейка является стеной и находится в пределах лабиринта.
     *
     * @param point точка, из которой добавляются ребра в границу
     */
    @Override
    public void addFrontier(Point point) {
        for (Direction dir : Direction.values()) {
            Point cell = add(point, dir.toPoint());
            Point wall = blend(point, dir.toPoint());
            if (maze.isValidPosition(cell) && maze.getCell(cell.x(), cell.y()) == CellType.WALL) {
                frontiers.add(new Edge(cell, wall));
            }
        }
    }

    /**
     * Проверяет наличие ребер в границе.
     *
     * @return true если в границе есть ребра, false если граница пуста
     */
    @Override
    public boolean hasNext() {
        return !frontiers.isEmpty();
    }

    /**
     * Возвращает случайное ребро из границы и удаляет его.
     *
     * @return случайное ребро из границы
     * @throws NoSuchElementException если граница пуста
     */
    @Override
    public Edge next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return frontiers.remove(random.nextInt(frontiers.size()));
    }
}

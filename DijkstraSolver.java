package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Iterator.NeighborIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import static academy.maze.solver.SolverUtils.buildPath;
import static academy.maze.solver.SolverUtils.validateInput;

/**
 * Реализация алгоритма Дейкстры для решения лабиринтов.
 * Находит кратчайший путь от начальной до конечной точки без использования эвристики.
 */
public class DijkstraSolver implements SolverStrategy {

    /**
     * Решает лабиринт с использованием алгоритма Дейкстры.
     * Если путь не найден, возвращается пустой путь.
     *
     * @param maze лабиринт для решения
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @return найденный путь или пустой путь, если решение не найдено
     */
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        validateInput(maze, start, end);

        Map<Point, Integer> distances = new HashMap<>();
        Map<Point, Point> previous = new HashMap<>();
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(x -> distances.getOrDefault(x, Integer.MAX_VALUE)));

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int curDistance = distances.get(point);

            if (curDistance > distances.get(point)) {
                continue;
            }

            if (point.equals(end)) {
                return buildPath(previous, start, end);
            }

            NeighborIterator neighborIterator = new NeighborIterator(point, maze);

            while (neighborIterator.hasNext()) {
                Point neighbor = neighborIterator.next();
                processNeighbor(point, neighbor, curDistance, distances, previous, queue);
            }
        }

        return Path.empty();
    }

    /**
     * Обрабатывает соседнюю ячейку в алгоритме Дейкстры.
     * Обновляет расстояние до соседа если найден более короткий путь.
     *
     * @param current текущая точка
     * @param neighbor соседняя точка
     * @param currentDistance расстояние до текущей точки
     * @param distances карта расстояний до всех точек
     * @param previous карта предыдущих точек для восстановления пути
     * @param queue очередь с приоритетом для обработки точек
     */
    private void processNeighbor(Point current, Point neighbor, int currentDistance, Map<Point, Integer> distances, Map<Point, Point> previous, PriorityQueue<Point> queue) {
        int newDistance = currentDistance + 1;

        if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
            distances.put(neighbor, newDistance);
            previous.put(neighbor, current);
            queue.add(neighbor);
        }
    }
}

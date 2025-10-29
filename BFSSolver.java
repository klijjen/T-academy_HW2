package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Iterator.NeighborIterator;

import java.util.*;
import static academy.maze.solver.SolverUtils.buildPath;
import static academy.maze.solver.SolverUtils.validateInput;
import static academy.maze.utils.Utils.validateStartAndEnd;

/**
 * Реализация алгоритма BFS для решения лабиринтов.
 * Находит кратчайший путь от начальной до конечной точки.
 */
public class BFSSolver implements SolverStrategy {

    /**
     * Решает лабиринт с использованием алгоритма BFS.
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

        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> previous = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        previous.put(start, null);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                return buildPath(previous, start, end);
            }

            processNeighbors(current, maze, queue, previous, visited);
        }

        return Path.empty();
    }

    /**
     * Обрабатывает всех соседей текущей точки.
     *
     * @param current текущая точка
     * @param maze лабиринт
     * @param queue очередь для добавления новых точек
     * @param previous карта предыдущих точек
     * @param visited множество посещенных точек
     */
    private void processNeighbors(Point current, Maze maze, Queue<Point> queue, Map<Point, Point> previous, Set<Point> visited) {
        NeighborIterator neighbors = new NeighborIterator(current, maze);
        while (neighbors.hasNext()) {
            Point neighbor = neighbors.next();

            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                previous.put(neighbor, current);
                queue.add(neighbor);
            }
        }
    }

}

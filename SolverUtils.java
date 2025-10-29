package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static academy.maze.utils.Utils.validateStartAndEnd;

/**
 * Утилитарный класс для алгоритмов решения лабиринтов.
 * Содержит общую логику валидации и восстановления пути, используемую различными алгоритмами решения.
 */
public class SolverUtils {

    /**
     * Проверяет корректность входных данных для алгоритмов решения лабиринтов.
     * Выполняет комплексную проверку начальной и конечной точек.
     *
     * @param maze лабиринт для проверки
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @throws IllegalArgumentException если maze, start или end равны null;
     *      если start или end находятся вне границ лабиринта;
     *      если start или end находятся на непроходимой ячейке;
     *      если start и end совпадают
     */
    protected static void validateInput(Maze maze, Point start, Point end) {
        validateStartAndEnd(start, end, maze);

        if (!maze.getCell(start.x(), start.y()).isPassable() ||
            !maze.getCell(end.x(), end.y()).isPassable()) {
            throw new IllegalArgumentException("Start or end point is on impassable cell");
        }

        if (start.equals(end)) {
            throw new IllegalArgumentException("Start and end points are the same");
        }
    }

    /**
     * Восстанавливает путь от конечной точки до начальной по карте предыдущих точек.
     * Общий метод для всех алгоритмов, использующих карту предыдущих точек.
     *
     * @param previous карта, содержащая для каждой точки её предыдущую точку в пути
     * @param start начальная точка
     * @param end конечная точка
     * @return восстановленный путь или пустой путь, если маршрут не существует
     */
    protected static Path buildPath(Map<Point, Point> previous, Point start, Point end) {
        if (!previous.containsKey(end)) {
            return Path.empty();
        }

        List<Point> pathPoints = new ArrayList<>();
        Point current = end;

        while (current != null) {
            pathPoints.add(current);
            current = previous.get(current);
        }
        Collections.reverse(pathPoints);

        if (!pathPoints.getFirst().equals(start)) {
            return Path.empty();
        }

        return new Path(pathPoints);
    }

}

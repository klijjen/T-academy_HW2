package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;
import java.util.LinkedList;
import java.util.Queue;
import static academy.maze.dto.Point.add;
import static academy.maze.dto.Point.createPoint;

/**
 * Утилита для проверки связности лабиринта.
 * Проверяет, существует ли путь от начальной точки до конечной.
 */
public class MazeChecker {

    /**
     * Проверяет, связан ли лабиринт - существует ли путь от начальной до конечной точки.
     * Использует алгоритм BFS (поиск в ширину) для проверки связности.
     *
     * @param maze лабиринт для проверки
     * @return true если существует путь от START до END, false в противном случае
     * @throws AssertionError если начальная точка не найдена в лабиринте
     */
    public static boolean isMazeConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.width()][maze.height()];
        Queue<Point> queue = new LinkedList<>();

        Point start = null;
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                if (maze.getCell(x, y) == CellType.START) {
                    start = createPoint(x, y);
                    queue.add(start);
                    break;
                }
            }
        }
        assert start != null;
        visited[start.y()][start.x()] = true;

        Point exit = null;
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                if (maze.getCell(x, y) == CellType.END) {
                    exit = createPoint(x, y);
                    break;
                }
            }
        }

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            assert exit != null;
            if (point.x() == exit.x() && point.y() == exit.y()) {
                return true;
            }

            for (Direction dir : Direction.values()) {
                Point neighbor = add(point, dir.toPoint());

                if (maze.isValidPosition(neighbor) && !visited[neighbor.y()][neighbor.x()] && maze.getCell(neighbor) != CellType.WALL) {
                    visited[neighbor.y()][neighbor.x()] = true;
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }
}

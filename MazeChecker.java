package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.LinkedList;
import java.util.Queue;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.dto.Point.sumOfVal;

public class MazeChecker {
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
        visited[start.x()][start.y()] = true;

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

            Point[] directions = {createPoint(0, -1), createPoint(0, 1), createPoint(-1, 0), createPoint(1, 0)};

            for (Point dir : directions) {
                Point neighbor = sumOfVal(point, dir);

                if (maze.isValidPosition(neighbor) && !visited[neighbor.x()][neighbor.y()] && maze.getCell(neighbor) != CellType.WALL) {
                    visited[neighbor.x()][neighbor.y()] = true;
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }

}

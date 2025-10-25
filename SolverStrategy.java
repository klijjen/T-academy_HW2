package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

/** Решатель лабиринта */
public interface SolverStrategy {

    /**
     * Решение лабиринта. Если путь не найден, то возвращается путь с длиной 0.
     *
     * @param maze лабиринт.
     * @param start начальная точка.
     * @param end конечная точка.
     * @return путь в лабиринте.
     */
    Path solve(Maze maze, Point start, Point end);

    default void validStartAndEnd(Point start, Point end, Maze maze) {
        if (!maze.isValidPosition(start)) {
            throw new IllegalArgumentException("Стартовая точка находится вне лабиринта");
        }
        if (!maze.isValidPosition(end)) {
            throw new IllegalArgumentException("Конечная точка находится вне лабиринта");
        }
    }
}

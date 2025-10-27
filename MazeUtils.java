package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;

public final class MazeUtils {
    private MazeUtils() {}

    public static void validateDimensions(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Размеры лабиринта должны быть >= 1");
        }
        if (width % 2 == 0 || height % 2 == 0) {
            throw new IllegalArgumentException("Размеры лабиринта должны быть нечетными");
        }
    }

    public static void createStartAndEnd(Maze maze) {
        maze.setCell(1, 1, CellType.START);
        maze.setCell(maze.width() - 2, maze.height() - 2, CellType.END);
    }

}

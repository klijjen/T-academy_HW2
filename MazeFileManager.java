package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import static academy.maze.utils.MazeUtils.validateDimensions;

public class MazeFileManager {

    public static void saveMaze(Maze maze, String filename, boolean useUnicode) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        visualizer.saveMazeToFile(maze, filename);
    }

    public static void saveSolution(Maze maze, Path path, String filename, boolean useUnicode) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        visualizer.saveMazeToFile(maze, path, filename);
    }

    public static Maze loadMaze(String filename) throws IOException {
        java.nio.file.Path filePath = java.nio.file.Path.of(filename);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Файл не найден: " + filename);
        }

        if (!Files.isReadable(filePath)) {
            throw new IOException("Нет прав на чтение файла: " + filename);
        }

        List<String> lines = Files.readAllLines(filePath);

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Файл лабиринта пустой: " + filename);
        }

        int height = lines.size();
        int width = lines.getFirst().length();

        validateDimensions(width, height);

        Maze maze = new Maze(width, height);

        for (int y = 0; y < height; y++) {
            String line = lines.get(y).trim();

            if (line.length() != width) {
                throw new IllegalArgumentException("Несовпадение ширины лабиринта в строке " + (y + 1) + " " + line.length() + " " + width);
            }

            for (int x = 0; x < width; x++) {
                char c = line.charAt(x);
                maze.setCell(x, y, CellType.getCellTypeByChar(c));
            }
        }

        return maze;
    }
}

package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import static academy.maze.utils.Utils.validateDimensions;

/**
 * Менеджер для работы с файлами лабиринтов.
 * Предоставляет методы для сохранения и загрузки лабиринтов и их решений.
 */
public class MazeFileManager {

    /**
     * Сохраняет лабиринт в файл.
     *
     * @param maze лабиринт для сохранения
     * @param filename имя файла для сохранения
     * @param useUnicode использовать Unicode символы для отображения
     */
    public static void saveMaze(Maze maze, String filename, boolean useUnicode) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        visualizer.saveMazeToFile(maze, filename);
    }

    /**
     * Сохраняет лабиринт с решением в файл.
     *
     * @param maze лабиринт для сохранения
     * @param path путь решения для отображения
     * @param filename имя файла для сохранения
     * @param useUnicode использовать Unicode символы для отображения
     */
    public static void saveSolution(Maze maze, Path path, String filename, boolean useUnicode) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        visualizer.saveMazeToFile(maze, path, filename);
    }

    /**
     * Загружает лабиринт из файла.
     *
     * @param filename имя файла для загрузки
     * @return загруженный лабиринт
     * @throws IOException если файл не найден, недоступен для чтения или произошла ошибка ввода-вывода
     * @throws IllegalArgumentException если файл пуст, имеет недопустимые размеры или некорректный формат
     */
    public static Maze loadMaze(String filename) throws IOException {
        java.nio.file.Path filePath = java.nio.file.Path.of(filename);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }

        if (!Files.isReadable(filePath)) {
            throw new IOException("No read permission for file: " + filename);
        }

        List<String> lines = Files.readAllLines(filePath);

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Maze file is empty: " + filename);
        }

        int height = lines.size();
        int width = lines.getFirst().length();

        validateDimensions(width, height);

        Maze maze = new Maze(width, height);

        for (int y = 0; y < height; y++) {
            String line = lines.get(y).trim();

            if (line.length() != width) {
                throw new IllegalArgumentException("Maze width mismatch in line " + (y + 1) + ". Expected: " + width + ", Actual: " + line.length());
            }

            for (int x = 0; x < width; x++) {
                char c = line.charAt(x);
                maze.setCell(x, y, CellType.getCellTypeByChar(c));
            }
        }

        return maze;
    }
}

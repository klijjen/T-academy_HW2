package academy.maze.utils;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;

/**
 * Сервис для вывода лабиринтов в консоль или файл.
 * Предоставляет гибкий интерфейс для отображения и сохранения лабиринтов и их решений.
 */
public class MazeOutputService {
    private final boolean useUnicode;
    private final MazeVisualizer visualizer;

    /**
     * Создает сервис вывода с указанными настройками.
     *
     * @param useUnicode использовать Unicode символы для отображения
     */
    public MazeOutputService(boolean useUnicode) {
        this.useUnicode = useUnicode;
        this.visualizer = new MazeVisualizer(useUnicode);
    }

    /**
     * Выводит лабиринт в консоль или сохраняет в файл.
     *
     * @param maze лабиринт для вывода
     * @param outputFile путь к файлу для сохранения (null для вывода в консоль)
     */
    public void outputMaze(Maze maze, String outputFile) {
        if (outputFile != null) {
            saveMazeToFile(maze, outputFile);
        } else {
            displayMaze(maze);
        }
    }

    /**
     * Выводит лабиринт с решением в консоль или сохраняет в файл.
     *
     * @param maze лабиринт для вывода
     * @param solution путь решения для отображения
     * @param outputFile путь к файлу для сохранения (null для вывода в консоль)
     */
    public void outputMazeWithSolution(Maze maze, Path solution, String outputFile) {
        if (outputFile != null) {
            saveSolutionToFile(maze, solution, outputFile);
        } else {
            displayMazeWithSolution(maze, solution);
        }
    }

    /**
     * Отображает лабиринт в консоли.
     *
     * @param maze лабиринт для отображения
     */
    public void displayMaze(Maze maze) {
        visualizer.displayMaze(maze);
    }

    /**
     * Отображает лабиринт с решением в консоли.
     *
     * @param maze лабиринт для отображения
     * @param solution путь решения для отображения
     */
    public void displayMazeWithSolution(Maze maze, Path solution) {
        visualizer.displayMaze(maze, solution);
    }

    /**
     * Сохраняет лабиринт в файл.
     *
     * @param maze лабиринт для сохранения
     * @param filename имя файла для сохранения
     */
    public void saveMazeToFile(Maze maze, String filename) {
        MazeFileManager.saveMaze(maze, filename, useUnicode);
    }

    /**
     * Сохраняет лабиринт с решением в файл.
     *
     * @param maze лабиринт для сохранения
     * @param solution путь решения для отображения
     * @param filename имя файла для сохранения
     */
    public void saveSolutionToFile(Maze maze, Path solution, String filename) {
        MazeFileManager.saveSolution(maze, solution, filename, useUnicode);
    }

    // Статические методы для удобства

    /**
     * Выводит лабиринт в консоль или сохраняет в файл (статический метод).
     *
     * @param maze лабиринт для вывода
     * @param useUnicode использовать Unicode символы
     * @param outputFile путь к файлу для сохранения (null для вывода в консоль)
     */
    public static void outputMaze(Maze maze, boolean useUnicode, String outputFile) {
        new MazeOutputService(useUnicode).outputMaze(maze, outputFile);
    }

    /**
     * Выводит лабиринт с решением в консоль или сохраняет в файл (статический метод).
     *
     * @param maze лабиринт для вывода
     * @param solution путь решения для отображения
     * @param useUnicode использовать Unicode символы
     * @param outputFile путь к файлу для сохранения (null для вывода в консоль)
     */
    public static void outputMazeWithSolution(Maze maze, Path solution, boolean useUnicode, String outputFile) {
        new MazeOutputService(useUnicode).outputMazeWithSolution(maze, solution, outputFile);
    }

    /**
     * Отображает лабиринт в консоли (статический метод).
     *
     * @param maze лабиринт для отображения
     * @param useUnicode использовать Unicode символы
     */
    public static void outputMaze(Maze maze, boolean useUnicode) {
        outputMaze(maze, useUnicode, null);
    }

    /**
     * Отображает лабиринт с решением в консоли (статический метод).
     *
     * @param maze лабиринт для отображения
     * @param solution путь решения для отображения
     * @param useUnicode использовать Unicode символы
     */
    public static void outputMazeWithSolution(Maze maze, Path solution, boolean useUnicode) {
        outputMazeWithSolution(maze, solution, useUnicode, null);
    }
}

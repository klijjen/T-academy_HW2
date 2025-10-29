package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.io.PrintWriter;
import static academy.maze.dto.Maze.copy;
import static academy.maze.utils.Utils.createStartAndEnd;

/**
 * Визуализатор лабиринтов для отображения в консоли и сохранения в файлы.
 * Поддерживает как ASCII, так и Unicode символы для отображения.
 */
public class MazeVisualizer {
    private final boolean useUnicode;

    /**
     * Создает визуализатор с указанными настройками.
     *
     * @param useUnicode использовать Unicode символы для отображения
     */
    public MazeVisualizer(boolean useUnicode) {
        this.useUnicode = useUnicode;
    }

    /**
     * Отображает лабиринт в консоли.
     *
     * @param maze лабиринт для отображения
     */
    public void displayMaze(Maze maze) {
        displayMaze(maze, Path.empty());
    }

    /**
     * Отображает лабиринт с решением в консоли.
     *
     * @param maze лабиринт для отображения
     * @param path путь решения для визуализации
     */
    public void displayMaze(Maze maze, Path path) {
        printMazeToConsole(createSolutionMaze(maze, path));
    }

    /**
     * Сохраняет лабиринт в файл.
     *
     * @param maze лабиринт для сохранения
     * @param filename имя файла для сохранения
     */
    public void saveMazeToFile(Maze maze, String filename) {
        saveMazeToFile(maze, Path.empty(), filename);
    }

    /**
     * Сохраняет лабиринт с решением в файл.
     *
     * @param maze лабиринт для сохранения
     * @param path путь решения для визуализации
     * @param filename имя файла для сохранения
     */
    public void saveMazeToFile(Maze maze, Path path, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            printMazeToWriter(createSolutionMaze(maze, path), writer);
        } catch (Exception e) {
            throw new RuntimeException("Error saving maze to file: " + filename, e);
        }
    }

    /**
     * Создает копию лабиринта с визуализацией пути решения.
     *
     * @param maze исходный лабиринт
     * @param path путь решения для визуализации
     * @return лабиринт с отображением пути решения
     */
    public Maze createSolutionMaze(Maze maze, Path path) {
        Maze solutionMaze = copy(maze);

        if (maze.width() <= 3 || maze.height() <= 3) {
            solutionMaze.setCell(1, 1, CellType.PATH);
            return solutionMaze;
        }

        if (!path.isEmpty()) {
            Point start = path.getStart();
            Point end = path.getEnd();

            createStartAndEnd(maze, start, end);

            for (Point point : path.points()) {
                CellType current = maze.getCell(point);
                if (current != CellType.START && current != CellType.END) {
                    solutionMaze.setCell(point, CellType.SOLUTION_PATH);
                }
            }
        }

        return solutionMaze;
    }

    /**
     * Выводит лабиринт в консоль.
     *
     * @param maze лабиринт для вывода
     */
    private void printMazeToConsole(Maze maze) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType cell = maze.getCell(x, y);
                System.out.print(getSymbol(cell));
            }
            System.out.println();
        }
    }

    /**
     * Записывает лабиринт в файл через PrintWriter.
     *
     * @param maze лабиринт для записи
     * @param writer писатель для вывода
     */
    private void printMazeToWriter(Maze maze, PrintWriter writer) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType cell = maze.getCell(x, y);
                writer.print(getSymbol(cell));
            }
            writer.println();
        }
    }

    /**
     * Возвращает символ для отображения типа ячейки.
     *
     * @param cellType тип ячейки
     * @return символ для отображения
     */
    private String getSymbol(CellType cellType) {
        if (useUnicode) {
            return switch (cellType) {
                case WALL -> "⬛";
                case PATH -> "⬜";
                case START -> "🚩";
                case END -> "🏁";
                case SOLUTION_PATH -> "💎";
                case SAND -> null;
                case COIN -> null;
            };
        }
        else {
            return cellType.getSymbol();
        }
    }
}

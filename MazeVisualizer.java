package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.jetbrains.annotations.NotNull;
import java.io.PrintWriter;
import static academy.maze.dto.Maze.copy;

public class MazeVisualizer {
    private final boolean useUnicode;

    public MazeVisualizer(boolean useUnicode) {
        this.useUnicode = useUnicode;
    }

    public void displayMaze(Maze maze) {
        displayMaze(maze, Path.empty());
    }

    public void displayMaze(Maze maze, Path path) {
        printMazeToConsole(createSolutionMaze(maze, path));
    }

    public void saveMazeToFile(Maze maze, String filename) {
        saveMazeToFile(maze, Path.empty(), filename);
    }

    public void saveMazeToFile(Maze maze, Path path, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            printMazeToWriter(createSolutionMaze(maze, path), writer);
            System.out.println("Лабиринт сохранен в файл: " + filename);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении лабиринта в файл: " + filename, e);
        }
    }

    public Maze createSolutionMaze(Maze maze, Path path) {
        Maze solutionMaze = copy(maze);

        if (!path.isEmpty()) {
            for (Point point : path.points()) {
                CellType current = maze.getCell(point);
                if (current != CellType.START && current != CellType.END) {
                    solutionMaze.setCell(point, CellType.SOLUTION_PATH);
                }
            }
        }

        return solutionMaze;
    }

    private void printMazeToConsole(Maze maze) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType cell = maze.getCell(x, y);
                System.out.print(getSymbol(cell));
            }
            System.out.println();
        }
    }

    private void printMazeToWriter(Maze maze, PrintWriter writer) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType cell = maze.getCell(x, y);
                writer.print(getSymbol(cell));
            }
            writer.println();
        }
    }

    private String getSymbol(CellType cellType) {
        if (useUnicode) {
            return switch (cellType) {
                case WALL -> "█";
                case PATH -> " ";
                case START -> "🚩";
                case END -> "🏁";
                case SOLUTION_PATH -> "·";
                case SAND -> null;
                case COIN -> null;
            };
        }
        else {
            return cellType.getSymbol();
        }
    }
}

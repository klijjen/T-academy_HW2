package academy.maze.command;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Solver;
import academy.maze.utils.MazeFileManager;
import academy.maze.utils.MazeVisualizer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;
import static academy.maze.solver.Solver.createSolver;
import static academy.maze.solver.SolverStrategy.validStartAndEnd;

/**
 * Команда для решения лабиринтов.
 *
 * Находит путь от начальной до конечной точки в указанном лабиринте
 * с использованием выбранного алгоритма поиска пути.
 *
 * Примеры использования:
 * solve --algorithm=astar --file=maze.txt 1,1 19,19
 * solve --algorithm=dijkstra --file=maze.txt 0,0 20,20 --output=solution.txt --unicode
 */
@Command(name = "solve", description = "Solve a maze with specified algorithm and points.")
public class SolverCommand implements Callable<Integer> {

    @Option(
        names = {"-a", "--algorithm"},
        description = "Алгоритм решения: astar или dijkstra",
        required = true
    )
    private String algorithm;

    @Option(
        names = {"-f", "--file"},
        description = "Файл с лабиринтом для решения",
        required = true
    )
    private String inputFile;

    @Option(
        names = {"-s", "--start"},
        description = "Начальная точка в формате: x,y",
        required = true
    )
    private String startPoint;

    @Option(
        names = {"-e", "--end"},
        description = "Конечная точка в формате: x,y",
        required = true
    )
    private String endPoint;

    @Option(
        names = {"-o", "--output"},
        description = "Имя файла для сохранения (если не указано, решение выводится в консоль)"
    )
    private String outputFile;

    @Option(
        names = {"-u", "--unicode"},
        description = "Использовать Unicode символы для визуализации (по умолчанию: false)",
        defaultValue = "false"
    )
    private boolean useUnicode;

    @Override
    public Integer call() throws Exception {
        try {
            // Парсинг и валидация входных данных
            Point start = parsePoint(startPoint);
            Point end = parsePoint(endPoint);
            Maze maze = loadMaze(inputFile);
            validStartAndEnd(start, end, maze);

            // Создание решателя и решение лабиринта
            Solver solver = createSolver(algorithm);
            Path solution = solver.solve(maze, start, end);


            // Обработка и вывод результата
            return handleSolution(maze, solution, start, end);
        } catch (Exception e) {
            System.err.println("Invalid point format: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Парсит строку с координатами точки в объект Point
     */
    private Point parsePoint(String pointStr) {
        try {
            String[] parts = pointStr.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException(pointStr + ", expected format: x,y");
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return new Point(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid point format: " + pointStr + ", expected format: x,y");        }
    }

    /**
     * Загружает лабиринт из файла
     */
    private Maze loadMaze(String mazeFile) throws Exception {
        return MazeFileManager.loadMaze(mazeFile);
    }

    /**
     * Обрабатывает и выводит решение лабиринта
     */
    private int handleSolution(Maze maze, Path solution, Point start, Point end) {
        if (solution.isEmpty()) {
            System.out.println("Путь от " + start + " до " + end + " не найден");
            return 1;
        }

//        System.out.println("Найден путь длиной: " + solution.length());
        outputSolution(maze, solution);
        return 0;
    }

    /**
     * Выводит решение в консоль или сохраняет в файл
     */
    private void outputSolution(Maze maze, Path solution) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        if (outputFile != null) {
            MazeFileManager.saveSolution(maze, solution, outputFile, useUnicode);
//            System.out.println("Решение сохранено в: " + outputFile);
        } else {
            visualizer.displayMaze(maze, solution);
        }
    }
}

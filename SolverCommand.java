package academy.maze.command;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Solver;
import academy.maze.utils.MazeOutputService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;
import static academy.maze.solver.Solver.createSolver;
import static academy.maze.utils.MazeInputService.loadMaze;
import static academy.maze.utils.Utils.isValidAlgorithm;
import static academy.maze.utils.Utils.validateStartAndEnd;

/**
 * Команда для решения лабиринтов.
 *
 * Находит путь от начальной до конечной точки в указанном лабиринте
 * с использованием выбранного алгоритма поиска пути.
 *
 * Алгоритмы решения:
 * - astar (A* Search): алгоритм поиска с эвристикой, находит кратчайший путь
 * - dijkstra (Dijkstra): алгоритм поиска по графу, гарантирует оптимальность
 *
 * Выходные данные:
 * - Если путь найден: визуализация решения с подсветкой пути
 * - Если путь не найден: соответствующее сообщение
 * - Решение может быть выведено в консоль или сохранено в файл
 *
 * Примеры использования:
 * solve --algorithm=astar --file=maze.txt --start=1,1 --end=19,19
 * solve --algorithm=dijkstra --file=maze.txt --start=0,0 --end=20,20 --output=solution.txt --unicode
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

    /**
     * Основной метод выполнения команды решения лабиринта.
     *
     * @return 0 в случае успешного нахождения пути, 1 в случае ошибки или если путь не найден
     * @throws Exception при возникновении ошибок загрузки, валидации или решения
     *
     * Обработка ошибок:
     * - Некорректный формат файла: IllegalArgumentException
     * - Неподдерживаемый алгоритм: IllegalArgumentException
     * - Точки вне лабиринта или на стенах: IllegalArgumentException
     * - Ошибки чтения файла: IOException
     */
    @Override
    public Integer call() throws Exception {
        try {
            Point start = parsePoint(startPoint);
            Point end = parsePoint(endPoint);
            Maze maze = loadMaze(inputFile);
            validateStartAndEnd(start, end, maze);
            isValidAlgorithm(algorithm, false);

            Solver solver = createSolver(algorithm);
            Path solution = solver.solve(maze, start, end);

            return handleSolution(maze, solution, start, end);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }


    /**
     * Парсит строку с координатами точки в объект Point.
     *
     * @param pointStr строка с координатами в формате "x,y"
     * @return объект Point с координатами
     * @throws IllegalArgumentException при некорректном формате или нечисловых координатах
     */
    private Point parsePoint(String pointStr) {
        try {
            String[] parts = pointStr.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid point format: " + pointStr + ", expected format: x,y");
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return new Point(x, y);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());        }
    }


    /**
     * Обрабатывает и выводит решение лабиринта.
     *
     * @param maze исходный лабиринт
     * @param solution найденный путь (может быть пустым)
     * @param start начальная точка
     * @param end конечная точка
     * @return 0 если путь найден, 1 если путь не найден
     */
    private int handleSolution(Maze maze, Path solution, Point start, Point end) {
        if (solution.isEmpty()) {
            System.out.println("No path found from " + start + " to " + end);
            return 1;
        }
        outputSolution(maze, solution);
        return 0;
    }

    /**
     * Выводит решение в консоль или сохраняет в файл.
     *
     * @param maze исходный лабиринт
     * @param solution найденный путь
     */
    private void outputSolution(Maze maze, Path solution) {
        if (outputFile != null) {
            MazeOutputService.outputMazeWithSolution(maze, solution, useUnicode, outputFile);
        }
        else {
            MazeOutputService.outputMazeWithSolution(maze, solution, useUnicode);
        }
    }
}

package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;

/**
 * Утилитарный класс с вспомогательными методами для работы с лабиринтами.
 * Содержит методы валидации, создания точек и проверки алгоритмов.
 */
public final class Utils {
    private Utils() {}

    /**
     * Проверяет корректность размеров лабиринта.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @throws IllegalArgumentException если размеры некорректны
     */
    public static void validateDimensions(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Maze dimensions must be >= 1");
        }
        if (width % 2 == 0 || height % 2 == 0) {
            throw new IllegalArgumentException("Maze dimensions must be odd numbers");
        }
    }

    /**
     * Проверяет корректность названия алгоритма.
     *
     * @param algo название алгоритма
     * @param isGenerator true для алгоритмов генерации, false для алгоритмов решения
     * @return true если алгоритм поддерживается, false в противном случае
     */
    public static boolean isValidAlgorithm(String algo, boolean isGenerator) {
        if (isGenerator)
            return "dfs".equalsIgnoreCase(algo) || "prim".equalsIgnoreCase(algo) || "wilson".equalsIgnoreCase(algo);
        else
            return "astar".equalsIgnoreCase(algo) || "dijkstra".equalsIgnoreCase(algo) || "bfs".equalsIgnoreCase(algo);
    }

    /**
     * Проверяет корректность входных параметров для генерации или решения лабиринта.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @param algorithm название алгоритма
     * @param isGenerator true для алгоритмов генерации, false для алгоритмов решения
     * @throws IllegalArgumentException если параметры некорректны
     */
    public static void validateInput(int width, int height, String algorithm, boolean isGenerator) {
        validateDimensions(width, height);
        if (!isValidAlgorithm(algorithm, isGenerator)) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm + ". Supported algorithms: " + (isGenerator ? "dfs, prim, wilson" : "astar, dijkstra"));
        }
    }

    /**
     * Создает начальную и конечную точки в лабиринте по умолчанию.
     * Начальная точка устанавливается в (1, 1), конечная - в противоположном углу.
     *
     * @param maze лабиринт для установки точек
     */
    public static void createStartAndEnd(Maze maze) {
        maze.setCell(1, 1, CellType.START);
        maze.setCell(maze.width() - 2, maze.height() - 2, CellType.END);
    }

    /**
     * Создает начальную и конечную точки в указанных позициях лабиринта.
     *
     * @param maze лабиринт для установки точек
     * @param start начальная точка
     * @param end конечная точка
     */
    public static void createStartAndEnd(Maze maze, Point start, Point end) {
        maze.setCell(start, CellType.START);
        maze.setCell(end, CellType.END);
    }

    /**
     * Проверяет корректность начальной и конечной точек в лабиринте.
     *
     * @param start начальная точка
     * @param end конечная точка
     * @param maze лабиринт для проверки
     * @throws IllegalArgumentException если точки некорректны
     */
    public static void validateStartAndEnd(Point start, Point end, Maze maze) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end points cannot be null");
        }
        if (!maze.isValidPosition(start)) {
            throw new IllegalArgumentException("Start point " + start + " is outside maze boundaries");
        }
        if (!maze.isValidPosition(end)) {
            throw new IllegalArgumentException("End point " + end + " is outside maze boundaries");
        }
        if (!maze.getCell(start).isPassable()) {
            throw new IllegalArgumentException("Start point " + start + " is on impassable cell: " + maze.getCell(start));
        }
        if (!maze.getCell(end).isPassable()) {
            throw new IllegalArgumentException("End point " + end + " is on impassable cell: " + maze.getCell(end));
        }
    }
}

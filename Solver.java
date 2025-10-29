package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

/**
 * Фабрика и обертка для решателей лабиринтов.
 * Предоставляет единый интерфейс для решения лабиринтов различными алгоритмами.
 */
public class Solver {
    private SolverStrategy strategy;

    /**
     * Создает решатель с указанной стратегией.
     *
     * @param strategy стратегия решения лабиринта
     */
    public Solver(SolverStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Устанавливает стратегию решения лабиринта.
     *
     * @param strategy стратегия решения лабиринта
     */
    public void setStrategy(SolverStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Решает лабиринт с использованием текущей стратегии.
     *
     * @param maze лабиринт для решения
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @return найденный путь или пустой путь, если решение не найдено
     */
    public Path solve(Maze maze, Point start, Point end) {
        return strategy.solve(maze, start, end);
    }

    /**
     * Создает решатель для указанного алгоритма.
     *
     * @param algorithm название алгоритма решения
     * @return решатель лабиринтов
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public static Solver createSolver(String algorithm) {
        return switch (algorithm.toLowerCase()) {
            case "astar" -> Solver.createAStarSolver();
            case "dijkstra" -> Solver.createDijkstraSolver();
            case "bfs" -> Solver.createBFSSolver();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm + ". Supported algorithms: astar, dijkstra, bfs");
        };
    }

    /**
     * Создает решатель, использующий алгоритм A*.
     *
     * @return решатель с алгоритмом A*
     */
    private static Solver createAStarSolver() {
        return new Solver(new AStarSolver());
    }

    /**
     * Создает решатель, использующий алгоритм Дейкстры.
     *
     * @return решатель с алгоритмом Дейкстры
     */
    private static Solver createDijkstraSolver() {
        return new Solver(new DijkstraSolver());
    }

    /**
     * Создает решатель, использующий алгоритм BFS.
     *
     * @return решатель с алгоритмом BFS
     */
    private static Solver createBFSSolver() {
        return new Solver(new BFSSolver());
    }
}

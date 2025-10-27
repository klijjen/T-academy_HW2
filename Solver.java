package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

public class Solver {
    private SolverStrategy strategy;

    public Solver(SolverStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy (SolverStrategy strategy) {
        this.strategy = strategy;
    }

    public Path solve(Maze maze, Point start, Point end) {
        return strategy.solve(maze, start, end);
    }

    public static Solver createSolver(String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "astar":
                return Solver.createAStarSolver();
            case "dijkstra":
                return Solver.createDijkstraSolver();
            default:
                throw new IllegalArgumentException("Неизвестный алгоритм: " + algorithm + ". Поддерживаемые алгоритмы: astar, dijkstra");
        }
    }

    private static Solver createAStarSolver() {
        return new Solver(new AStarSolver());
    }

    private static Solver createDijkstraSolver() {
        return new Solver(new DijkstraSolver());
    }
}

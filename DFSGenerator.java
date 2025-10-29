package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static academy.maze.dto.Point.add;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.dto.Point.midpoint;
import static academy.maze.utils.Utils.createStartAndEnd;

/**
 * Генератор лабиринтов с использованием алгоритма поиска в глубину (DFS).
 * Создает лабиринты с длинными коридорами и меньшим количеством тупиков.
 */
public class DFSGenerator implements GeneratorStrategy {

    /**
     * Создает новый генератор лабиринтов по алгоритму DFS.
     */
    public DFSGenerator() {}

    /**
     * Генерирует лабиринт указанного размера с использованием алгоритма DFS.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт
     */
    @Override
    public Maze generate(int width, int height) {
        Maze maze = new Maze(width, height);
        Point start = createPoint(1, 1);

        DFS(maze, start);

        createStartAndEnd(maze);

        return maze;
    }

    /**
     * Выполняет алгоритм поиска в глубину для генерации лабиринта.
     *
     * @param maze лабиринт для генерации
     * @param start начальная точка генерации
     */
    private void DFS(Maze maze, Point start) {
        Stack<Point> stack = new Stack<>();
        stack.push(start);
        maze.setCell(start, CellType.PATH);

        while (!stack.isEmpty()) {
            Point point = stack.peek();

            List<Point> unvisitedNeighbors = getUnvisitedNeighbors(point, maze);

            if (!unvisitedNeighbors.isEmpty()) {
                Point neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));

                Point wall = midpoint(point, neighbor);
                maze.setCell(wall, CellType.PATH);
                maze.setCell(neighbor, CellType.PATH);

                stack.push(neighbor);
            }
            else {
                stack.pop();
            }
        }
    }

    /**
     * Возвращает список непосещенных соседей для указанной точки.
     *
     * @param point точка для проверки соседей
     * @param maze лабиринт
     * @return список непосещенных соседних точек
     */
    private List<Point> getUnvisitedNeighbors(Point point, Maze maze) {
        List<Point> neighbors = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Point doubleNeighbor = add(point, add(dir.toPoint(), dir.toPoint()));

            if (maze.isValidPosition(doubleNeighbor) && maze.getCell(doubleNeighbor) == CellType.WALL) {
                neighbors.add(doubleNeighbor);
            }
        }

        return neighbors;
    }
}

package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.dto.Point.halfOfVal;
import static academy.maze.dto.Point.sumOfVal;
import static academy.maze.utils.MazeUtils.createStartAndEnd;
import static academy.maze.utils.MazeUtils.validateDimensions;

public class DFSGenerator implements GeneratorStrategy {
    public DFSGenerator() {}

    @Override
    public Maze generate(int width, int height) {
        validateDimensions(width, height);

        Maze maze = new Maze(width, height);
        Point start = createPoint(1, 1);

        DFS(maze, start);

        createStartAndEnd(maze);

        return maze;
    }

    private void DFS(Maze maze, Point start) {
        Stack<Point> stack = new Stack<>();
        stack.push(start);
        maze.setCell(start, CellType.PATH);

        while (!stack.isEmpty()) {
            Point point = stack.peek();

            List<Point> unvisitedNeighbors = getUnvisitedNeighbors(point, maze);

            if (!unvisitedNeighbors.isEmpty()) {
                Point neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));

                Point wall = halfOfVal(sumOfVal(point, neighbor));
                maze.setCell(wall, CellType.PATH);
                maze.setCell(neighbor, CellType.PATH);

                stack.push(neighbor);
            }
            else {
                stack.pop();
            }
        }
    }

    private List<Point> getUnvisitedNeighbors(Point point, Maze maze) {
        List<Point> neighbors = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Point neighbor = sumOfVal(point, dir.toPoint());

            if (maze.isValidPosition(neighbor) && maze.getCell(neighbor) == CellType.WALL) {
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }
}

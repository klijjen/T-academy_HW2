package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.generator.Iterator.NeighborIterator;
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

    private void DFS(Maze maze, Point point) {
        maze.setCell(point.x(), point.y(), CellType.PATH);

        NeighborIterator iterator = new NeighborIterator(point, maze, random);

        while (iterator.hasNext()) {
            Point neighbor = iterator.next();
            if (maze.getCell(neighbor) == CellType.WALL) {
                Point wall = halfOfVal(sumOfVal(point, neighbor));
                maze.setCell(wall.x(), wall.y(), CellType.PATH);
                DFS(maze, neighbor);
            }
        }
    }
}

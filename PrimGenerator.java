package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Edge;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.generator.Iterator.FrontierIterator;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.utils.MazeUtils.createStartAndEnd;
import static academy.maze.utils.MazeUtils.validateDimensions;

public class PrimGenerator implements GeneratorStrategy {

    public PrimGenerator() {}

    @Override
    public Maze generate(int width, int height) {
        validateDimensions(width, height);

        Maze maze = new Maze(width, height);

        Point start = createPoint(1, 1);
        maze.setCell(start.x(), start.y(), CellType.PATH);

        FrontierIterator frontierIterator = new FrontierIterator(random, maze);
        frontierIterator.addFrontier(start);

        while (frontierIterator.hasNext()) {
            Edge edge = frontierIterator.next();
            Point cell = edge.cell();
            Point wall = edge.wall();

            if (maze.isValidPosition(cell) && maze.getCell(cell.x(), cell.y()) == CellType.WALL) {

                maze.setCell(wall.x(), wall.y(), CellType.PATH);
                maze.setCell(cell.x(), cell.y(), CellType.PATH);

                frontierIterator.addFrontier(cell);
            }
        }

        createStartAndEnd(maze);

        return maze;
    }
}

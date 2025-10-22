package academy.maze.generator.Iterator;

import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Edge;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import static academy.maze.dto.Point.sumOfVal;
import static academy.maze.dto.Point.sumOfValAndHalf;

public class FrontierIterator implements FrontierIteratorInterface {
    private final List<Edge> frontiers;
    private final Random random;
    private final Maze maze;

    public FrontierIterator(Random random, Maze maze) {
        this.frontiers = new ArrayList<>();
        this.random = random;
        this.maze = maze;
    }

    @Override
    public void addFrontier(Point point) {
        for (Direction dir : Direction.values()) {
            Point cell = sumOfVal(point, dir.toPoint());
            Point wall = sumOfValAndHalf(point, dir.toPoint());
            if (maze.isValidPosition(cell) && maze.getCell(cell.x(), cell.y()) == CellType.WALL) {
                frontiers.add(new Edge(cell, wall));
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !frontiers.isEmpty();
    }

    @Override
    public Edge next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return frontiers.remove(random.nextInt(frontiers.size()));
    }
}

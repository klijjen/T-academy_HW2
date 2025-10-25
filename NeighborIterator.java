package academy.maze.solver.Iterator;

import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static academy.maze.dto.Point.sumOfVal;

public class NeighborIterator implements NeighborIteratorInterface {
    private final List<Point> neighbors;
    private int index;

    public NeighborIterator(Point point, Maze maze) {
        this.neighbors = new ArrayList<>();
        this.index = 0;
        initNeighbors(point, maze);
    }

    private void initNeighbors(Point point, Maze maze) {
        for (Direction dir : Direction.values()) {
            Point neighbor = sumOfVal(point, dir.toPoint());
            if (isValidNeighbor(neighbor, maze)) {
                neighbors.add(neighbor);
            }
        }
    }

    private boolean isValidNeighbor(Point neighbor, Maze maze) {
        return maze.isValidPosition(neighbor) && maze.getCell(neighbor).isPassable();
    }

    @Override
    public boolean hasNext() {
        return index < neighbors.size();
    }

    @Override
    public Point next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Point neighbor = neighbors.get(index);
        index++;
        return neighbor;
    }
}

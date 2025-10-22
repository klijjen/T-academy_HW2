package academy.maze.generator.Iterator;

import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import static academy.maze.dto.Point.sumOfVal;

public class NeighborIterator implements NeighborIteratorInterface {
    private final List<Point> neighbors;
    private int index;

    public NeighborIterator(Point point, Maze maze, Random random) {
        this.neighbors = new ArrayList<>();
        initNeighbors(point, maze);
        Collections.shuffle(neighbors, random);
        this.index = 0;
    }

    private void initNeighbors(Point point, Maze maze) {
        for (Direction dir : Direction.values()) {
            Point neighbor = sumOfVal(point, dir.toPoint());
            if (maze.isValidPosition(neighbor) && maze.getCell(neighbor) == CellType.WALL) {
                neighbors.add(neighbor);
            }
        }
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
        index++;
        return neighbors.get(index);
    }
}

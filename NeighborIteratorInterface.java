package academy.maze.solver.Iterator;

import academy.maze.dto.Point;
import java.util.Iterator;

public interface NeighborIteratorInterface extends Iterator<Point> {
    boolean hasNext();
    Point next();
}

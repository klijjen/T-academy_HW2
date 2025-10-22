package academy.maze.generator.Iterator;

import academy.maze.dto.Edge;
import academy.maze.dto.Point;
import java.util.Iterator;

public interface FrontierIteratorInterface extends Iterator<Edge> {
    boolean hasNext();
    Edge next();
    void addFrontier(Point point);
}

package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Iterator.NeighborIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraSolver implements SolverStrategy {
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        SolverStrategy.validStartAndEnd(start, end, maze);

        if (!maze.getCell(start.x(), start.y()).isPassable() ||
            !maze.getCell(end.x(), end.y()).isPassable()) {
            return Path.empty();
        }

        if (start.equals(end)) {
            return new Path(List.of(start));
        }

        Map<Point, Integer> distances = new HashMap<>();
        Map<Point, Point> previous = new HashMap<>();
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(x -> distances.getOrDefault(x, Integer.MAX_VALUE)));

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int curDistance = distances.get(point);

            if (curDistance > distances.get(point)) {
                continue;
            }

            if (point.equals(end)) {
                break;
            }

            NeighborIterator neighborIterator = new NeighborIterator(point, maze);

            while (neighborIterator.hasNext()) {
                Point neighbor = neighborIterator.next();
                processNeighbor(point, neighbor, curDistance, distances, previous, queue);
            }
        }

        return buildPath(previous, start, end);
    }

    private void processNeighbor(Point current, Point neighbor, int currentDistance, Map<Point, Integer> distances, Map<Point, Point> previous, PriorityQueue<Point> queue) {
        int newDistance = currentDistance + 1;

        if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
            distances.put(neighbor, newDistance);
            previous.put(neighbor, current);
            queue.add(neighbor);
        }
    }

    private Path buildPath(Map<Point, Point> previous, Point start, Point end) {
        if (!previous.containsKey(end)) {
            return Path.empty();
        }

        List<Point> pathPoints = new ArrayList<>();
        Point current = end;

        while (current != null) {
            pathPoints.add(current);
            current = previous.get(current);
        }
        Collections.reverse(pathPoints);

        if (!pathPoints.getFirst().equals(start)) {
            return Path.empty();
        }

        return new Path(pathPoints);
    }
}

package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Node;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Iterator.NeighborIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements SolverStrategy {
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        SolverStrategy.validStartAndEnd(start, end, maze);

        if (!maze.getCell(start.x(), start.y()).isPassable() ||
            !maze.getCell(end.x(), end.y()).isPassable()) {
            return Path.empty();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<Point, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, null, 0, start.manhattanDistanceTo(end));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node node = openSet.poll();

            if (node.point().equals(end)) {
                return reconstructPath(node);
            }

            NeighborIterator neighborIterator = new NeighborIterator(node.point(), maze);

            while (neighborIterator.hasNext()) {
                Point neighbor = neighborIterator.next();
                processNeighbor(node, neighbor, end, openSet, allNodes);
            }
        }

        return Path.empty();
    }

    private void processNeighbor(Node currentNode, Point neighbor, Point end, PriorityQueue<Node> openSet, Map<Point, Node> allNodes) {
        double tentativeCost = currentNode.gCost() + 1;

        Node neighborNode = allNodes.get(neighbor);
        if (neighborNode == null) {
            Node newNode = new Node(neighbor, currentNode, tentativeCost, neighbor.manhattanDistanceTo(end));
            openSet.add(newNode);
            allNodes.put(neighbor, newNode);
        }
        else if (tentativeCost < neighborNode.gCost()) {
            Node updatedNode = new Node(neighbor, currentNode, tentativeCost, neighborNode.hCost());

            openSet.remove(neighborNode);
            openSet.add(updatedNode);
            allNodes.put(neighbor, updatedNode);
        }
    }

    private Path reconstructPath(Node endNode) {
        java.util.List<Point> path = new ArrayList<>();
        Node node = endNode;

        while (node != null) {
            path.addFirst(node.point());
            node = node.parent();
        }

        return new Path(path);
    }

}

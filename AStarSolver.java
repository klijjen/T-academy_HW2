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
import static academy.maze.solver.SolverUtils.validateInput;
import static academy.maze.utils.Utils.validateStartAndEnd;

/**
 * Реализация алгоритма A* для решения лабиринтов.
 * Находит кратчайший путь от начальной до конечной точки с использованием эвристики.
 */
public class AStarSolver implements SolverStrategy {

    /**
     * Решает лабиринт с использованием алгоритма A*.
     * Если путь не найден, возвращается пустой путь.
     *
     * @param maze лабиринт для решения
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @return найденный путь или пустой путь, если решение не найдено
     */
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        validateInput(maze, start, end);

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

    /**
     * Обрабатывает соседнюю ячейку в алгоритме A*.
     * Вычисляет стоимость пути до соседа и обновляет при необходимости.
     *
     * @param currentNode текущий обрабатываемый узел
     * @param neighbor соседняя точка
     * @param end конечная точка
     * @param openSet очередь с приоритетом для обработки узлов
     * @param allNodes карта всех посещенных узлов
     */
    private void processNeighbor(Node currentNode, Point neighbor, Point end, PriorityQueue<Node> openSet, Map<Point, Node> allNodes) {
        int tentativeCost = currentNode.gCost() + 1;

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

    /**
     * Восстанавливает путь от конечного узла до начального.
     *
     * @param endNode конечный узел пути
     * @return восстановленный путь от начала до конца
     */
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

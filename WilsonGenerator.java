package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.solver.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static academy.maze.dto.Point.add;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.utils.Utils.createStartAndEnd;

/**
 * Генератор лабиринтов с использованием алгоритма Уилсона.
 * Создает равномерно распределенные лабиринты с использованием алгоритма случайного блуждания.
 */
public class WilsonGenerator implements GeneratorStrategy {

    /**
     * Создает новый генератор лабиринтов по алгоритму Уилсона.
     */
    public WilsonGenerator() {}

    /**
     * Генерирует лабиринт указанного размера с использованием алгоритма Уилсона.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт
     */
    @Override
    public Maze generate(int width, int height) {
        Maze maze = new Maze(width, height);

        Set<Point> visited = new HashSet<>();

        Point start = createPoint(1, 1);
        maze.setCell(start, CellType.PATH);
        visited.add(start);

        List<Point> unvisited = new ArrayList<>();
        for (int y = 1; y < height - 1; y += 2) {
            for (int x = 1; x < width - 1; x += 2) {
                Point cell = createPoint(x, y);
                if (!visited.contains(cell)) {
                    unvisited.add(cell);
                }
            }
        }

        while (!unvisited.isEmpty()) {
            Point current = unvisited.get(random.nextInt(unvisited.size()));
            List<Point> path = new ArrayList<>();
            path.add(current);

            while (!visited.contains(current)) {
                List<Direction> possibleDirections = getPossibleDirections(current, maze);
                if (possibleDirections.isEmpty()) {
                    break;
                }

                Direction dir = possibleDirections.get(random.nextInt(possibleDirections.size()));
                Point next = add(current, add(dir.toPoint(), dir.toPoint()));

                int index = path.indexOf(next);
                if (index != -1) {
                    path = path.subList(0, index + 1);
                } else {
                    path.add(next);
                }

                current = next;
            }

            addPathToMaze(maze, path, visited);

            unvisited.removeAll(visited);
        }

        createStartAndEnd(maze);
        return maze;
    }

    /**
     * Возвращает возможные направления движения из текущей точки.
     *
     * @param point текущая точка
     * @param maze лабиринт
     * @return список возможных направлений
     */
    private List<Direction> getPossibleDirections(Point point, Maze maze) {
        List<Direction> directions = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Point neighbor = add(point, add(dir.toPoint(), dir.toPoint()));
            if (maze.isValidPosition(neighbor)) {
                directions.add(dir);
            }
        }

        return directions;
    }

    /**
     * Добавляет путь в лабиринт, проламывая стены.
     *
     * @param maze лабиринт
     * @param path путь для добавления
     * @param visited множество посещенных ячеек
     */
    private void addPathToMaze(Maze maze, List<Point> path, Set<Point> visited) {
        for (int i = 0; i < path.size(); i++) {
            Point current = path.get(i);
            maze.setCell(current, CellType.PATH);
            visited.add(current);

            // Проламываем стену между текущей и следующей ячейкой
            if (i < path.size() - 1) {
                Point next = path.get(i + 1);
                Point wall = getWallBetween(current, next);
                if (wall != null) {
                    maze.setCell(wall, CellType.PATH);
                }
            }
        }
    }

    /**
     * Находит стену между двумя ячейками.
     *
     * @param a первая ячейка
     * @param b вторая ячейка
     * @return точка стены между ячейками или null если ячейки не соседние
     */
    private Point getWallBetween(Point a, Point b) {
        int dx = b.x() - a.x();
        int dy = b.y() - a.y();

        if (Math.abs(dx) == 2 && dy == 0) {
            return createPoint(a.x() + dx / 2, a.y());
        } else if (Math.abs(dy) == 2 && dx == 0) {
            return createPoint(a.x(), a.y() + dy / 2);
        }

        return null;
    }
}

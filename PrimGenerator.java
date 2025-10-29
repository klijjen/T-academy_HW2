package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Edge;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.generator.Iterator.FrontierIterator;
import static academy.maze.dto.Point.createPoint;
import static academy.maze.utils.Utils.createStartAndEnd;

/**
 * Генератор лабиринтов с использованием алгоритма Прима.
 * Создает сбалансированные лабиринты с равномерным распределением путей.
 */
public class PrimGenerator implements GeneratorStrategy {

    /**
     * Создает новый генератор лабиринтов по алгоритму Прима.
     */
    public PrimGenerator() {}

    /**
     * Генерирует лабиринт указанного размера с использованием алгоритма Прима.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт
     */
    @Override
    public Maze generate(int width, int height) {
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

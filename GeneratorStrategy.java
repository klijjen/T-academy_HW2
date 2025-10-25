package academy.maze.generator;

import academy.maze.dto.Maze;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/** Генератор лабиринта */
public interface GeneratorStrategy {

    /**
     * Генерирует лабиринт.
     *
     * @param width ширина лабиринта.
     * @param height высота лабиринта.
     * @return лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт.
     */
    Maze generate(int width, int height);
    Random random = ThreadLocalRandom.current();

}

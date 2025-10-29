package academy.maze.generator;

import academy.maze.dto.Maze;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Стратегия генерации лабиринта.
 * Определяет интерфейс для различных алгоритмов генерации лабиринтов.
 */public interface GeneratorStrategy {

    /**
     * Генерирует лабиринт.
     *
     * @param width ширина лабиринта.
     * @param height высота лабиринта.
     * @return лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт.
     */
    Maze generate(int width, int height);

    /**
     * Генератор случайных чисел для использования в алгоритмах генерации.
     */
    Random random = ThreadLocalRandom.current();

}

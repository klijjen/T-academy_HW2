package academy.maze.generator;

import academy.maze.dto.Maze;

/**
 * Фабрика и обертка для генераторов лабиринтов.
 * Предоставляет единый интерфейс для создания лабиринтов различными алгоритмами.
 */
public class Generator {

    private final GeneratorStrategy strategy;

    /**
     * Создает генератор с указанной стратегией.
     *
     * @param strategy стратегия генерации лабиринта
     */
    public Generator(GeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Генерирует лабиринт указанного размера.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт
     */
    public Maze generate(int width, int height) {
        return strategy.generate(width, height);
    }

    /**
     * Создает генератор для указанного алгоритма.
     *
     * @param algorithm название алгоритма генерации
     * @return генератор лабиринтов
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public static Generator createGenerator(String algorithm) {
        return switch (algorithm.toLowerCase()) {
            case "dfs" -> Generator.createDFSGenerator();
            case "prim" -> Generator.createPrimGenerator();
            case "wilson" -> Generator.createWilsonGenerator();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm + ". Supported algorithms: dfs, prim, wilson");
        };
    }

    /**
     * Создает генератор, использующий алгоритм DFS (поиск в глубину).
     *
     * @return генератор с алгоритмом DFS
     */
    private static Generator createDFSGenerator() {
        return new Generator(new DFSGenerator());
    }

    /**
     * Создает генератор, использующий алгоритм Прима.
     *
     * @return генератор с алгоритмом Прима
     */
    private static Generator createPrimGenerator() {
        return new Generator(new PrimGenerator());
    }

    /**
     * Создает генератор, использующий алгоритм Уилсона.
     *
     * @return генератор с алгоритмом Уилсона
     */
    private static Generator createWilsonGenerator() {
        return new Generator(new WilsonGenerator());
    }
}

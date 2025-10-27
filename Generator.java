package academy.maze.generator;

import academy.maze.dto.Maze;

public class Generator {
    private GeneratorStrategy strategy;
    public Generator(GeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy (GeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public Maze generate(int wight, int height) {
        return strategy.generate(wight, height);
    }

    public static Generator createGenerator(String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "dfs":
                return Generator.createDFSGenerator();
            case "prim":
                return Generator.createPrimGenerator();
            default:
                throw new IllegalArgumentException("Неизвестный алгоритм: " + algorithm + ". Поддерживаемые алгоритмы: dfs, prim");
        }
    }

    private static Generator createDFSGenerator() {
        return new Generator(new DFSGenerator());
    }

    private static Generator createPrimGenerator() {
        return new Generator(new PrimGenerator());
    }
}

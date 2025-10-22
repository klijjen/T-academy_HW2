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
}

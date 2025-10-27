package academy.maze.command;

import academy.maze.dto.Maze;
import academy.maze.generator.Generator;
import academy.maze.utils.MazeFileManager;
import academy.maze.utils.MazeUtils;
import academy.maze.utils.MazeVisualizer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import static academy.maze.generator.Generator.createGenerator;

/**
 * Команда для генерации лабиринтов.
 *
 * Генерирует лабиринт указанного размера с использованием выбранного алгоритма.
 * Размеры лабиринта должны быть нечетными числами не менее 1.
 *
 * Примеры использования:
 * generate --algorithm=dfs --width=15 --height=15
 * generate --algorithm=prim --width=21 --height=21 --output=maze.txt --unicode
 */
@Command(name = "generate", description = "Generate a maze with specified algorithm and dimensions.")
public class GeneratorCommand implements Callable<Integer> {

    @Option(
        names = {"-a", "--algorithm"},
        description = "Generation algorithm: dfs, prim",
        required = true
    )
    private String algorithm;

    @Option(
        names = {"-w", "--width"},
        description = "Maze width",
        required = true
    )
    private int width;

    @Option(
        names = {"-h", "--height"},
        description = "Maze height",
        required = true
    )
    private int height;

    @Option(
        names = {"-o", "--output"},
        description = "Output file path"
    )
    private String outputFile;

    @Option(
        names = {"-u", "--unicode"},
        description = "Use Unicode",
        defaultValue = "false"
    )
    private boolean useUnicode;

    @Override
    public Integer call() throws Exception {
        MazeUtils.validateDimensions(width, height);
        width += 2;
        height += 2;

        try {
            Generator generator = createGenerator(algorithm);

            Maze maze = generator.generate(width, height);

            outputMaze(maze);

            return 0;
        } catch (Exception e) {
//            System.err.println("Ошибка при генерации лабиринта: " + e.getMessage());
            return 1;
        }
    }


    /**
     * Выводит лабиринт в консоль или сохраняет в файл
     */
    private void outputMaze(Maze maze) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        if (outputFile != null) {
            MazeFileManager.saveMaze(maze, outputFile, useUnicode);
        }
        else {
            visualizer.displayMaze(maze);
        }
    }
}

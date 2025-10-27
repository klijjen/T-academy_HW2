package academy.maze.command;

import academy.maze.dto.Maze;
import academy.maze.generator.Generator;
import academy.maze.utils.MazeFileManager;
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
 * Размеры лабиринта должны быть нечетными числами не менее 3.
 *
 * Примеры использования:
 * generate --algorithm=dfs --width=15 --height=15
 * generate --algorithm=prim --width=21 --height=21 --output=maze.txt --unicode
 */
@Command(name = "generate", description = "Генерация лабиринта с использованием указанного алгоритма")
public class GeneratorCommand implements Callable<Integer> {

    @Option(
        names = {"-a", "--algorithm"},
        description = "Алгоритм генерации: dfs или prim",
        required = true
    )
    private String algorithm;

    @Option(
        names = {"-w", "--width"},
        description = "Ширина лабиринта (должна быть нечетной и >= 3)",
        required = true
    )
    private int width;

    @Option(
        names = {"-h", "--height"},
        description = "Высота лабиринта (должна быть нечетной и >= 3)",
        required = true
    )
    private int height;

    @Option(
        names = {"-o", "--output"},
        description = "Имя файла для сохранения (если не указано, лабиринт выводится в консоль)"
    )
    private Files outputFile;

    @Option(
        names = {"-u", "--unicode"},
        description = "Использовать Unicode символы для визуализации (по умолчанию: false)",
        defaultValue = "false"
    )
    private boolean useUnicode;

    @Override
    public Integer call() throws Exception {
        try {
            // Создание генератора на основе выбранного алгоритма
            Generator generator = createGenerator(algorithm);

            // Генерация лабиринта
            Maze maze = generator.generate(width, height);

            // Вывод результата
            outputMaze(maze);

            return 0;
        } catch (Exception e) {
            System.err.println("Ошибка при генерации лабиринта: " + e.getMessage());
            return 1;
        }
    }


    /**
     * Выводит лабиринт в консоль или сохраняет в файл
     */
    private void outputMaze(Maze maze) {
        MazeVisualizer visualizer = new MazeVisualizer(useUnicode);
        if (outputFile != null) {
            MazeFileManager.saveMaze(maze, outputFile.toString(), useUnicode);
            System.out.println("Лабиринт сохранен в: " + outputFile.toString());
        }
        else {
            visualizer.displayMaze(maze);
        }
    }
}

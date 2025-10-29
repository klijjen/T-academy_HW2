package academy.maze.command;

import academy.maze.dto.Maze;
import academy.maze.generator.Generator;
import academy.maze.utils.MazeOutputService;
import academy.maze.utils.Utils;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;
import static academy.maze.generator.Generator.createGenerator;

/**
 * Команда для генерации лабиринтов.
 *
 * Генерирует лабиринт указанного размера с использованием выбранного алгоритма.
 * Размеры лабиринта должны быть нечетными числами не менее 1.
 * Лабиринт автоматически окружается стенами по периметру.
 *
 * Алгоритмы генерации:
 *   - dfs (Depth-First Search): создает лабиринты с длинными коридорами и меньшим количеством тупиков
 *   - prim (Prim's Algorithm): создает более сбалансированные лабиринты с равномерным распределением путей
 *
 * Выходные данные:
 *   - Если указан файл вывода (-o), лабиринт сохраняется в файл
 *   - Если файл вывода не указан, лабиринт отображается в консоли
 *   - Флаг --unicode позволяет использовать Unicode-символы для отображения
 *
 * Примечание: фактические размеры лабиринта будут на 2 больше указанных из-за добавления стен по периметру.
 *
 * Примеры использования:
 *
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

    /**
     * Основной метод выполнения команды генерации лабиринта.
     *
     * @return 0 в случае успеха, 1 в случае ошибки
     * @throws Exception при возникновении ошибок генерации или валидации
     *
     * Обработка ошибок:
     * - Некорректные размеры: IllegalArgumentException
     * - Неподдерживаемый алгоритм: IllegalArgumentException
     * - Ошибки ввода-вывода: IOException
     * - Некорректные координаты точек: IllegalArgumentException
     */
    @Override
    public Integer call() throws Exception {
        try {
            Utils.validateInput(width, height, algorithm, true);

            Generator generator = createGenerator(algorithm);
            Maze maze = generator.generate(width + 2, height + 2);

            outputMaze(maze);

            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }

    /**
     * Вывод лабиринта в консоль или файл.
     *
     * @param maze лабиринт для вывода
     *
     * В зависимости от наличия outputFile:
     * - Если outputFile указан: лабиринт сохраняется в файл
     * - Если outputFile не указан: лабиринт выводится в консоль
     *
     * Флаг useUnicode определяет набор символов для отображения:
     * - true: Unicode символы
     * - false: ASCII символы
     */
    private void outputMaze(Maze maze) {
        if (outputFile != null) {
            MazeOutputService.outputMaze(maze, useUnicode, outputFile);
        }
        else {
            MazeOutputService.outputMaze(maze, useUnicode);
        }
    }

}

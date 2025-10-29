package academy;

import academy.maze.command.GeneratorCommand;
import academy.maze.command.SolverCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Главный класс приложения для генерации и решения лабиринтов.
 * Предоставляет интерфейс командной строки для работы с лабиринтами.
 */
@Command(
    name = "maze-app",
    version = "1.0",
    description = "Maze generator and solver CLI application.",
    mixinStandardHelpOptions = true,
    subcommands = {GeneratorCommand.class, SolverCommand.class}
)
public class Application implements Runnable {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    /**
     * Выполняется когда приложение запущено без подкоманд.
     * Отображает справку по использованию.
     */
    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}

package academy.maze.utils;

import academy.maze.dto.Maze;

/**
 * Сервис для загрузки лабиринтов из файлов.
 * Предоставляет упрощенный интерфейс для загрузки лабиринтов.
 */
public class MazeInputService {

    /**
     * Загружает лабиринт из файла.
     *
     * @param mazeFile путь к файлу с лабиринтом
     * @return загруженный лабиринт
     * @throws Exception если файл не найден, недоступен для чтения,
     *                   имеет недопустимый формат или произошла ошибка ввода-вывода
     */
    public static Maze loadMaze(String mazeFile) throws Exception {
        return MazeFileManager.loadMaze(mazeFile);
    }
}

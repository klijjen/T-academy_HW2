package academy.maze.generator.Iterator;

import academy.maze.dto.Edge;
import academy.maze.dto.Point;
import java.util.Iterator;

/**
 * Интерфейс итератора для работы с границей (фронтиром) в алгоритмах генерации лабиринтов.
 * Расширяет стандартный Iterator для работы с ребрами (Edge) лабиринта.
 */
public interface FrontierIteratorInterface extends Iterator<Edge> {

    /**
     * Проверяет наличие следующего ребра в границе.
     *
     * @return true если в границе есть следующие ребра, false в противном случае
     */
    boolean hasNext();

    /**
     * Возвращает следующее ребро из границы.
     *
     * @return следующее ребро (Edge)
     * @throws java.util.NoSuchElementException если в границе больше нет элементов
     */
    Edge next();

    /**
     * Добавляет точку в границу для последующей обработки.
     * Используется в алгоритмах генерации для расширения области обработки.
     *
     * @param point точка для добавления в границу
     */
    void addFrontier(Point point);
}

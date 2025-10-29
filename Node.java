package academy.maze.dto;
/**
 * Узел для алгоритмов поиска пути (A*).
 * Представляет ячейку в лабиринте с информацией о стоимости пути и родительском узле.
 *
 * @param point координаты ячейки в лабиринте
 * @param parent родительский узел в пути
 * @param gCost стоимость пути от начальной точки до этой ячейки
 * @param hCost эвристическая стоимость от этой ячейки до конечной точки
 */

public record Node(Point point, Node parent, int gCost, int hCost) implements Comparable<Node> {
    /**
     * Вычисляет общую стоимость узла (f-cost).
     * f-cost = g-cost + h-cost
     *
     * @return общая стоимость узла
     */
    public int getTotalCost() {
        return gCost + hCost;
    }

    /**
     * Сравнивает узлы по общей стоимости для приоритетной очереди.
     * Узлы с меньшей общей стоимостью имеют высший приоритет.
     *
     * @param other другой узел для сравнения
     * @return отрицательное число, ноль или положительное число, если этот узел
     *         меньше, равен или больше указанного узла
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getTotalCost(), other.getTotalCost());
    }
}

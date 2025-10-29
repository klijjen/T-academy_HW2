package academy.maze.dto;

/**
 * Лабиринт.
 *
 * @param cells Массив ячеек лабиринта.
 * @param width Ширина лабиринта
 * @param height Высота лабиринта
 */
public record Maze(CellType[][] cells, int width, int height) {
    public Maze {
        if (cells == null) {
            throw new IllegalArgumentException("Cells array cannot be null");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        if (cells.length != height || cells[0].length != width) {
            throw new IllegalArgumentException("Cells array dimensions do not match width and height");
        }
    }

    /**
     * Создает лабиринт из существующего массива ячеек.
     * Ширина и высота определяются автоматически из массива.
     *
     * @param cells массив ячеек лабиринта
     */
    public Maze(CellType[][] cells) {
        this(cells, cells[0].length, cells.length);
    }

    /**
     * Создает новый лабиринт указанного размера, заполненный стенами.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     */
    public Maze(int width, int height) {
        this(createEmptyMaze(width, height), width, height);
    }

    /**
     * Создает пустой лабиринт (все ячейки - стены) указанного размера.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return массив ячеек, заполненный стенами
     */
    private static CellType[][] createEmptyMaze(int width, int height) {
        CellType[][] cells = new CellType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = CellType.WALL;
            }
        }
        return cells;
    }

    /**
     * Возвращает тип ячейки в указанной точке.
     *
     * @param point координаты точки
     * @return тип ячейки в указанной точке или WALL, если позиция невалидна
     */
    public CellType getCell(Point point) {
        return getCell(point.x(), point.y());
    }

    /**
     * Возвращает тип ячейки по координатам (x, y).
     *
     * @param x координата X
     * @param y координата Y
     * @return тип ячейки в указанных координатах или WALL, если позиция невалидна
     */
    public CellType getCell(int x, int y) {
        return isValidPosition(x, y) ? cells[y][x] : CellType.WALL;
    }

    /**
     * Устанавливает тип ячейки в указанной точке.
     *
     * @param point координаты точки
     * @param type тип ячейки для установки
     */
    public void setCell(Point point, CellType type) {
        setCell(point.x(), point.y(), type);
    }

    /**
     * Устанавливает тип ячейки по координатам (x, y).
     *
     * @param x координата X
     * @param y координата Y
     * @param type тип ячейки для установки
     */
    public void setCell(int x, int y, CellType type) {
        if (isValidPosition(x, y)) {
            cells[y][x] = type;
        }
    }

    /**
     * Проверяет, являются ли координаты (x, y) валидной позицией в лабиринте.
     * (исключая граничные стены).
     *
     * @param x координата X
     * @param y координата Y
     * @return true если координаты находятся в пределах лабиринта, false в противном случае
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 1 && x < width - 1 && y >= 1 && y < height - 1;
    }

    /**
     * Проверяет, является ли точка валидной позицией внутри проходимой области лабиринта
     * (исключая граничные стены).
     *
     * @param point координаты точки
     * @return true если точка находится внутри проходимой области, false в противном случае
     */
    public boolean isValidPosition(Point point) {
        return point.x() >= 1 && point.x() < width - 1 && point.y() >= 1 && point.y() < height - 1;
    }

    /**
     * Создает копию лабиринта.
     *
     * @param maze исходный лабиринт для копирования
     * @return новая копия лабиринта
     */
    public static Maze copy(Maze maze) {
        return new Maze(maze.cells);
    }

}

package academy.maze.dto;

import java.util.Arrays;

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
            throw new IllegalArgumentException("Массив ячеек не может быть пустым ");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными ");
        }
        if (cells.length != height || cells[0].length != width) {
            throw new IllegalArgumentException("Массив ячеек не соответствует ширине и высоте.");
        }
    }

    public Maze(CellType[][] cells) {
        this(cells, cells.length, cells[0].length);
    }

    public Maze(int width, int height) {
        this(createEmptyMaze(width, height), width, height);
    }

    public CellType getCell(Point point) {
        return cells[point.x()][point.y()];
    }

    private static CellType[][] createEmptyMaze (int width, int height) {
        CellType[][] cells = new CellType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = CellType.WALL;
            }
        }
        return cells;
    }

    public CellType getCell(int x, int y) {
        return isValidPosition(x, y) ? cells[x][y] : CellType.WALL;
    }

    public void setCell(int x, int y, CellType type) {
        if (isValidPosition(x, y)) {
            cells[x][y] = type;
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isValidPosition(Point point) {
        return point.x() >= 0 && point.x() < width && point.y() >= 0 && point.y() < height;
    }

    private CellType[][] copyCell() {
        CellType[][] newCells = new CellType[height][width];
        for (int i = 0; i < height; i++) {
            newCells[i] = Arrays.copyOf(cells[i], width);
        }
        return newCells;
    }

}

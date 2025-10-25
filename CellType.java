package academy.maze.dto;

/** Тип ячейки в лабиринте */
public enum CellType {
    WALL("#"),           // Стена
    PATH(" "),           // Путь
    START("O"),          // Начало
    END("X"),            // Конец
    SOLUTION_PATH("."),  // Путь решения
    SAND("~"),           // Песок (замедляет движение)
    COIN("$");           // Монетка (ускоряет движение)

    private final String symbol;

    CellType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CellType getCellTypeByChar(char c) {
        String findSymbol = String.valueOf(c);
        for (CellType cellType : CellType.values()) {
            if (findSymbol.equals(cellType.getSymbol())) {
                return cellType;
            }
        }
        return null;
    }

    public boolean isPassable() {
        return this != WALL;
    }



}

package academy.maze.dto;

/** Тип ячейки в лабиринте */
public enum CellType {
    /** Стена - непроходимое препятствие */
    WALL("#"),           // Стена

    /** Путь - проходимая клетка */
    PATH(" "),           // Путь

    /** Начальная точка */
    START("O"),          // Начало

    /** Конечная точка */
    END("X"),            // Конец

    /** Путь решения - отображает найденный маршрут */
    SOLUTION_PATH("."),  // Путь решения

    SAND("~"),           // Песок (увеличивает стоимость движения)
    COIN("$");           // Монетка (уменьшает стоимость движения)

    private final String symbol;

    CellType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Возвращает символ, представляющий данный тип ячейки
     * @return строковый символ ячейки
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Определяет тип ячейки по символу
     * @param c символ ячейки
     * @return соответствующий CellType или null, если символ не найден
     */
    public static CellType getCellTypeByChar(char c) {
        String findSymbol = String.valueOf(c);
        for (CellType cellType : CellType.values()) {
            if (findSymbol.equals(cellType.getSymbol())) {
                return cellType;
            }
        }
        return null;
    }

    /**
     * Проверяет, является ли ячейка проходимой
     * @return true если ячейка проходима, false если нет
     */
    public boolean isPassable() {
        return this != WALL;
    }
}

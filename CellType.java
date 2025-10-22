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

    public boolean isPossible() {
        return this != WALL;
    }



}

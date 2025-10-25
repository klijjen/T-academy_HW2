package academy.maze.solver;

import academy.maze.dto.Point;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Point toPoint() {
        return new Point(dx, dy);
    }

}

package academy.maze.dto;

public enum Direction {
    UP(0, -2),
    DOWN(0, 2),
    LEFT(-2, 0),
    RIGHT(2, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }

    public Point toPoint() {
        return new Point(dx, dy);
    }

}

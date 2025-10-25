package academy.maze.dto;

public record Node(Point point, Node parent, double gCost, double hCost) implements Comparable<Node> {
    public double getTotalCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.getTotalCost(), other.getTotalCost());
    }
}

package org.example.game;

public class Cell {
    private final int x;
    private final int y;
    private CellEntityType type;
    private boolean visited;

    public Cell(int x, int y, CellEntityType type) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates cannot be negative");
        }
        this.x = x;
        this.y = y;
        this.type = type;
        this.visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    public void setUnvisited() {
        this.visited = false;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", visited=" + visited +
                '}';
    }
}

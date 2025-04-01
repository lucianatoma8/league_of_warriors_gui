package org.example.game;

import org.example.entities.characters.Character;
import org.example.exceptions.ImpossibleMove;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private final int width;
    private final int height;
    private Character currentCharacter;
    private Cell currentCell;

    private Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CellEntityType getCellType(int x, int y) {
        return this.get(y).get(x).getType();
    }

    public boolean isCellVisited(int x, int y) {
        return this.get(y).get(x).isVisited();
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }

    public static Grid generateGrid(Character character) {
        Random random = new Random();

        int width = 5 + random.nextInt(6);
        int height = 5 + random.nextInt(6);

        Grid grid = new Grid(width, height);

        // initialize the grid with VOID cells
        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(j, i, CellEntityType.VOID));
            }
            grid.add(row);
        }

        int playerX = random.nextInt(width);
        int playerY = random.nextInt(height);
        Cell playerCell = grid.get(playerY).get(playerX);
        playerCell.setType(CellEntityType.PLAYER);
        playerCell.setVisited();
        grid.currentCharacter = character;
        grid.currentCell = playerCell;

        int numberOfEnemies = 4 + random.nextInt(7); // at least 4 enemies
        int numberOfSanctuaries = 2 + random.nextInt(4); // at least 2 sanctuaries

        placeCells(grid, CellEntityType.ENEMY, numberOfEnemies);
        placeCells(grid, CellEntityType.SANCTUARY, numberOfSanctuaries);
        placeCells(grid, CellEntityType.PORTAL, 1); // 1 portal

        return grid;
    }

    private static void placeCells(Grid grid, CellEntityType type, int count) {
        Random random = new Random();

        while (count > 0) {
            int x = random.nextInt(grid.width);
            int y = random.nextInt(grid.height);
            Cell cell = grid.get(y).get(x);

            // only place entities on VOID cells
            if (cell.getType() == CellEntityType.VOID) {
                cell.setType(type);
                cell.setUnvisited();
                count--;
            }
        }
    }

    public void goNorth() throws ImpossibleMove {
        if (currentCell.getY() == 0) {
            throw new ImpossibleMove("Cannot move North. You are at the top edge of the grid.");
        }
        moveToCell(currentCell.getX(), currentCell.getY() - 1);
    }

    public void goSouth() throws ImpossibleMove {
        if (currentCell.getY() == height - 1) {
            throw new ImpossibleMove("Cannot move South. You are at the bottom edge of the grid.");
        }
        moveToCell(currentCell.getX(), currentCell.getY() + 1);
    }

    public void goWest() throws ImpossibleMove {
        if (currentCell.getX() == 0) {
            throw new ImpossibleMove("Cannot move West. You are at the left edge of the grid.");
        }
        moveToCell(currentCell.getX() - 1, currentCell.getY());
    }

    public void goEast() throws ImpossibleMove {
        if (currentCell.getX() == width - 1) {
            throw new ImpossibleMove("Cannot move East. You are at the right edge of the grid.");
        }
        moveToCell(currentCell.getX() + 1, currentCell.getY());
    }

    private void moveToCell(int x, int y) {
        currentCell.setVisited();
        if (currentCell.getType() == CellEntityType.PLAYER) {
            currentCell.setType(CellEntityType.VOID);
        }

        currentCell = get(y).get(x);
        if (currentCell.getType() != CellEntityType.PLAYER) {
            currentCell.setVisited();
        }
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void displayGrid() {
        for (ArrayList<Cell> row : this) {
            for (Cell cell : row) {
                if (cell == currentCell) {
                    System.out.print("[P] ");
                } else {
                    switch (cell.getType()) {
                        case SANCTUARY -> System.out.print("[S] ");
                        case ENEMY -> System.out.print("[E] ");
                        case PORTAL -> System.out.print("[F] ");
                        case VOID -> {
                            if (cell.isVisited()) {
                                System.out.print("[V] ");
                            } else {
                                System.out.print("[N] ");
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    public static Grid generatePredefinedGrid(Character character) {
        int width = 5;
        int height = 5;

        Grid grid = new Grid(width, height);

        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(j, i, CellEntityType.VOID));
            }
            grid.add(row);
        }

        grid.get(0).get(0).setType(CellEntityType.PLAYER);
        grid.get(0).get(0).setVisited();
        grid.currentCell = grid.get(0).get(0);
        grid.currentCharacter = character;

        grid.get(0).get(3).setType(CellEntityType.SANCTUARY);
        grid.get(2).get(0).setType(CellEntityType.SANCTUARY);
        grid.get(1).get(3).setType(CellEntityType.SANCTUARY);
        grid.get(4).get(3).setType(CellEntityType.SANCTUARY);

        grid.get(3).get(4).setType(CellEntityType.ENEMY);

        grid.get(4).get(4).setType(CellEntityType.PORTAL);

        return grid;
    }

    public void setCell(int x, int y, CellEntityType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            this.get(y).get(x).setType(type);
        }
    }

    public void initializeWithVoidCells() {
        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(j, i, CellEntityType.VOID));
            }
            this.add(row);
        }
    }

    public void setCurrentCharacter(Character character) {
        this.currentCharacter = character;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.get(i).get(j).getType() == CellEntityType.PLAYER) {
                    this.currentCell = this.get(i).get(j);
                    break;
                }
            }
        }
    }
}

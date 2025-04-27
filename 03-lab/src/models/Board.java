package models;

import java.util.*;

public class Board {
    private final int X_DIM;
    private final int Y_DIM;
    private Cell[][] cells;

    public Board(int x, int y, List<Coordinates> markedCells) {
        this.X_DIM = x;
        this.Y_DIM = y;

        initCells(markedCells);
    }

    public void initCells(List<Coordinates> markedCells) {
        cells = new Cell[X_DIM][Y_DIM];

        for (int i = 0; i < Y_DIM; ++i) {
            for (int j = 0; j < X_DIM; ++j) {
                Coordinates currCoords = new Coordinates(i, j);
                boolean isMarked = markedCells.contains(currCoords);

                cells[i][j] = new Cell(i, isMarked);
            }
        }
    }

    public Cell getCell(int index) {
        Coordinates coords = convertIndexToCoords(index);
        int x = coords.getX();
        int y = coords.getY();

        return getCell(x, y);
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public List<Cell> getAdjCells(int index) {
        Coordinates coords = convertIndexToCoords(index);
        int x = coords.getX();
        int y = coords.getY();

        return getAdjCells(x, y);
    }

    public List<Cell> getAdjCells(int x, int y) {
        int[][] directions = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
        List<Cell> adjCells = new ArrayList<>();
    
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
    
            if (isInBounds(newX, newY)) {
                Cell adjCell = getCell(newX, newY);
                adjCells.add(adjCell);
            }
        }
    
        return adjCells;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < X_DIM && y >= 0 && y < Y_DIM;
    }

    public void printBoard() {
        // String result = "";
        
        // for (Cell cell : cells) {
        //     boolean isMarked = cell.getState();
        //     int index = cell.getIndex();

        //     if (isMarked) {
        //         result += "*";
        //     }
        //     else {
        //         result += "o";
        //     }
            
        //     if ((index+1) % X_DIM == 0) {
        //         result += "\n";
        //     }
        // }

        // System.out.println(result);
    }

    private Coordinates convertIndexToCoords(int index) {
        int x = index % X_DIM;
        int y = index / X_DIM;

        return new Coordinates(x, y);
    }

    private int convertCoordsToIndex(Coordinates coords) {
        int x = coords.getX();
        int y = coords.getY();

        return y*X_DIM + x;
    }
}

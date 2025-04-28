package models;

import java.util.*;

public class Board {
    private final int X_DIM;
    private final int Y_DIM;

    private boolean[][] currBoard;
    private boolean[][] nextBoard;

    public Board(int x, int y, List<Coordinates> markedCells) {
        this.X_DIM = x;
        this.Y_DIM = y;

        this.currBoard = new boolean[x][y];
        this.nextBoard = new boolean[x][y];
    }

    public boolean getCellState(int index) {
        Coordinates coords = convertIndexToCoords(index);
        int x = coords.getX();
        int y = coords.getY();

        return getCellState(x, y);
    }

    public boolean getCellState(int x, int y) {
        return currBoard[y][x];
    }

    public int countActiveNeighbors(int x, int y) {
        int[][] directions = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
        int count = 0;

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
    
            if (isInBounds(newX, newY) && getCellState(newX, newY)) {
                ++count;
            }
        }
    
        return count;
    }

    public int countActiveNeighbors(int index) {
        Coordinates coords = convertIndexToCoords(index);
        int x = coords.getX();
        int y = coords.getY();

        return countActiveNeighbors(x, y);
    }

    public void proceed() {
        currBoard = nextBoard;
        nextBoard = new boolean[X_DIM][Y_DIM];
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < X_DIM && y >= 0 && y < Y_DIM;
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

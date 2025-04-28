package models;

import java.util.*;

public class Board extends Grid {
    private boolean[][] currBoard;
    private boolean[][] nextBoard;
    private boolean hasChanged;

    public Board(int x, int y, List<Coordinates> markedCells) {
        super(x, y);

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

    public void setNextCellState(int index, boolean state) {
        Coordinates coords = convertIndexToCoords(index);
        int x = coords.getX();
        int y = coords.getY();

        setNextCellState(x, y, state);
    }

    public void setNextCellState(int x, int y, boolean newState) {
        boolean currState = getCellState(x, y);
        nextBoard[x][y] = newState;

        if (newState != currState) {
            hasChanged = true;
        }
    }

    public boolean hasBoardChanged() {
        return hasChanged;
    } 

    public boolean calculateNextState(boolean state, int activeNeighborCount) {
        boolean nextState;

        if (state) {
            nextState = activeNeighborCount == 2 || activeNeighborCount == 3;
        } 
        else {
            nextState = activeNeighborCount == 3;
        }

        return nextState;
    }

    public void applyNextBoard() {
        currBoard = nextBoard;
        nextBoard = new boolean[X_DIM][Y_DIM];
        hasChanged = false;
    }
}

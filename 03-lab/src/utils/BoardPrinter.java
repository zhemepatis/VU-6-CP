package utils;

import models.*;

public class BoardPrinter {
    private Board board;

    public BoardPrinter(Board board) {
        this.board = board;
    }

    public void printIteration(int iteration) {
        System.out.println("ITERATION: " + iteration);
    }

    public void print() {
        String result = "";
        int x = board.getXDimension();
        int y = board.getYDimension();

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean isActive = board.getCellState(j, i);
                result += (isActive) ? "+" : ".";
            }

            result += "\n";
        }

        System.out.println(result);
    }
}

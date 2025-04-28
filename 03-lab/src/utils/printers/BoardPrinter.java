package utils.printers;

import models.*;

public class BoardPrinter {
    private Board board;

    public BoardPrinter(Board board) {
        this.board = board;
    }

    public void print() {
        String result = "";
        int x = board.getXDimension();
        int y = board.getYDimension();

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean isActive = board.getCellState(x, y);
                result += (isActive) ? "+" : " ";
            }
        }

        System.out.println(result);
    }
}

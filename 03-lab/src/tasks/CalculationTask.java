package tasks;

import models.*;

public class CalculationTask implements Runnable {
    private final int START_INDEX;
    private final int END_INDEX;

    private Board board;
    private Boolean gameFinished;

    public CalculationTask(int startIndex, int endIndex, Board board, Boolean gameFinished) {
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;
        this.gameFinished = gameFinished;
    }

    @Override
    public void run() {
        while (!gameFinished) {
            // TODO: Calculate
            // TODO: Increment barrier
            // TODO: Wait for indication
        }
    }
}

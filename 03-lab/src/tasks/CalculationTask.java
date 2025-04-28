package tasks;

import models.*;
import utils.*;

public class CalculationTask implements Runnable {
    private final int START_INDEX;
    private final int END_INDEX;

    private Board board;
    private Boolean gameFinished;

    private CounterLock barrierLock;

    public CalculationTask(int startIndex, int endIndex, Board board, Boolean gameFinished, CounterLock barrierLock) {
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;
        this.gameFinished = gameFinished;

        this.barrierLock = barrierLock;
    }

    @Override
    public void run() {
        while (!gameFinished) {
            for (int i = START_INDEX; i < END_INDEX; ++i) {
                int activeNeighborCount = board.countActiveNeighbors(i);
                boolean currState = board.getCellState(i);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextState(i, newState);
            }

            barrierLock.advance();

            // TODO: wait for indication to proceed further
        }
    }
}

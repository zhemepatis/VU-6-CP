package tasks;

import models.*;
import utils.concurrency.CounterLock;

public class CalculationTask implements Runnable {
    private final int START_INDEX;
    private final int END_INDEX;

    private Board board;
    private Boolean gameFinished;

    private CounterLock procceedCalculationsLock;
    private CounterLock taskCompletionLock;

    public CalculationTask(int startIndex, int endIndex, Board board, Boolean gameFinished, CounterLock procceedCalculationsLock, CounterLock taskCompletionLock) {
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;
        this.gameFinished = gameFinished;

        this.procceedCalculationsLock = procceedCalculationsLock;
        this.taskCompletionLock = taskCompletionLock;
    }

    @Override
    public void run() {
        int nextIteration = 1;

        while (!gameFinished) {
            for (int i = START_INDEX; i < END_INDEX; ++i) {
                int activeNeighborCount = board.countActiveNeighbors(i);
                boolean currState = board.getCellState(i);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextCellState(i, newState);
            }

            taskCompletionLock.advance();

            try {
                procceedCalculationsLock.await(nextIteration);
            } catch(InterruptedException ex) {
                System.out.println("Interrupted proceed calculations lock await.");
                break;
            }
            
        }
    }
}

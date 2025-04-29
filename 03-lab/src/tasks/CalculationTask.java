package tasks;

import models.*;
import utils.concurrency.CounterLock;
import utils.wrappers.*;;

public class CalculationTask implements Runnable {
    private final int START_INDEX;
    private final int END_INDEX;

    private Board board;
    private BooleanWrapper gameFinished;

    private CounterLock procceedCalculationsLock;
    private CounterLock workersDoneLock;

    public CalculationTask(int startIndex, int endIndex, Board board, BooleanWrapper gameFinished, CounterLock procceedCalculationsLock, CounterLock workersDoneLock) {
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;
        this.gameFinished = gameFinished;

        this.procceedCalculationsLock = procceedCalculationsLock;
        this.workersDoneLock = workersDoneLock;
    }

    @Override
    public void run() {
        int nextIteration = 1;

        awaitStartSignal(nextIteration);

        // game loop
        while (!gameFinished.value) {      
            for (int i = START_INDEX; i <= END_INDEX; ++i) {
                int activeNeighborCount = board.countActiveNeighbors(i);
                boolean currState = board.getCellState(i);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextCellState(i, newState);
            }

            workersDoneLock.advance();

            ++nextIteration;
            awaitStartSignal(nextIteration);
        }
    }

    private void awaitStartSignal(int await) {
        try {
            procceedCalculationsLock.await(await);
        } catch(InterruptedException ex) {
            System.out.println("Interrupted proceed calculations lock await.");
        }   
    }
}

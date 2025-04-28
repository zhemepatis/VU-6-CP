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

        waitForCalculationStart(nextIteration);

        // game loop
        while (!gameFinished.value) {
            System.out.println("Calculation task cycle");
        
            for (int i = START_INDEX; i < END_INDEX; ++i) {
                int activeNeighborCount = board.countActiveNeighbors(i);
                boolean currState = board.getCellState(i);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextCellState(i, newState);
            }

            System.out.println("Calculation advance");
            workersDoneLock.advance();

            ++nextIteration;
            waitForCalculationStart(nextIteration);
            System.out.println(gameFinished);
        }
    }

    public void waitForCalculationStart(int await) {
        try {
            System.out.println("Calculation lock await: " + await);

            procceedCalculationsLock.await(await);
            
            System.out.println("Claculation lock released");
        } catch(InterruptedException ex) {
            System.out.println("Interrupted proceed calculations lock await.");
        }   
    }
}

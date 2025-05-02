package tasks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import models.*;

public class CalculationTask implements Runnable {
    private final int START_INDEX;
    private final int END_INDEX;

    private Board board;
    private int maxIterationCount;

    private CyclicBarrier barrier;

    public CalculationTask(int startIndex, int endIndex, Board board, int maxIterationCount, CyclicBarrier barrier) {
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;
        this.maxIterationCount = maxIterationCount;

        this.barrier = barrier;
    }

    @Override
    public void run() {
        int iteration = 1;

        awaitThreads();

        // game loop
        while (iteration != maxIterationCount) {      
            for (int i = START_INDEX; i < END_INDEX - 1; ++i) {
                int activeNeighborCount = board.countActiveNeighbors(i);
                boolean currState = board.getCellState(i);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextCellState(i, newState);
            }

            awaitThreads();
            awaitThreads();

            ++iteration;
        }
    }

    private void awaitThreads() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            System.out.println("Broken barrier.");
        }
    }
}

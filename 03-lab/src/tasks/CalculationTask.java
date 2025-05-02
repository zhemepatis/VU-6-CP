package tasks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import models.*;

public class CalculationTask implements Runnable {
    private final int MAX_ITERATION_COUNT;
    private final int START_INDEX;
    private final int END_INDEX;

    private CyclicBarrier barrier;

    private Board board;

    public CalculationTask(int startIndex, int endIndex, Board board, int maxIterationCount, CyclicBarrier barrier) {
        this.MAX_ITERATION_COUNT = maxIterationCount;
        this.START_INDEX = startIndex;
        this.END_INDEX = endIndex;

        this.board = board;

        this.barrier = barrier;
    }

    @Override
    public void run() {
        // game loop
        for (int i = 0; i < MAX_ITERATION_COUNT; ++i) {
            for (int j = START_INDEX; j < END_INDEX - 1; ++j) {
                int activeNeighborCount = board.countActiveNeighbors(j);
                boolean currState = board.getCellState(j);
                boolean newState = board.calculateNextState(currState, activeNeighborCount);

                board.setNextCellState(j, newState);
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                System.out.println("Broken barrier.");
            }
        }
    }
}

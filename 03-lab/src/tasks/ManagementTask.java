package tasks;

import models.Board;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier; 
import utils.BoardPrinter;

public class ManagementTask implements Runnable {
    private final int THREAD_COUNT;
    private Thread[] threads;
    private boolean verbose;

    private CyclicBarrier barrier;

    private Board board;
    private int maxIterationCount;

    public ManagementTask(int maxIterationCount, Board board, int threadCount, boolean verbose) {
        this.THREAD_COUNT = threadCount;
        this.verbose = verbose;
        
        this.barrier = new CyclicBarrier(threadCount + 1);

        this.board = board;
        this.maxIterationCount = maxIterationCount;

        createThreads();
    }
    
    @Override
    public void run() {
        int iteration = 0;
        BoardPrinter boardPrinter = new BoardPrinter(board);

        // print initial setup
        if (verbose) {
            boardPrinter.printIteration(iteration);
            boardPrinter.print();
        }
        ++iteration;

        // create and run threads
        startThreads();
        awaitThreads();

        // game loop
        while (iteration != maxIterationCount) {
            // wait for all threads to complete their tasks
            awaitThreads();

            board.applyNextBoard();

            // print iteration
            if (verbose) {
                boardPrinter.printIteration(iteration + 1);
                boardPrinter.print();
            }
            ++iteration;
            
            awaitThreads();
        }

        // waiting for all threads to finish their tasks
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception ex) {

        }
    }

    private void awaitThreads() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            System.out.println("Broken barrier.");
        }
    }

    private void createThreads() {
        int cellNum = board.getSize();

        threads = new Thread[THREAD_COUNT];
        int threadShare = cellNum / THREAD_COUNT;
        int remainder = cellNum % THREAD_COUNT;

        int startIndex = 0;
        for (int i = 0; i < THREAD_COUNT; ++i) {
            int endIndex = startIndex + threadShare - 1;

            if (i < remainder) {
                ++endIndex;
            }

            CalculationTask task = new CalculationTask(startIndex, endIndex, board, maxIterationCount, barrier);
            threads[i] = new Thread(task);

            startIndex = endIndex + 1;
        }
    }

    private void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }
}

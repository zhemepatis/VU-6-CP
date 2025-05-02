package tasks;

import models.Board;

import java.util.concurrent.CyclicBarrier; 
import utils.BoardPrinter;

public class TaskManager {
    private int MAX_ITERATION_COUNT;
    private int iteration = 0;

    private final int THREAD_COUNT;
    private Thread[] threads;

    private CyclicBarrier barrier;

    private Board board;
    private BoardPrinter boardPrinter;
    private final boolean VERBOSE;

    public TaskManager(int maxIterationCount, Board board, int threadCount, boolean verbose) {
        this.MAX_ITERATION_COUNT = maxIterationCount;
        
        this.board = board;
        this.boardPrinter = new BoardPrinter(board);
        this.VERBOSE = verbose;
        
        this.THREAD_COUNT = threadCount;
        this.barrier = new CyclicBarrier(threadCount, () -> {
            board.applyNextBoard();

            // print iteration
            if (VERBOSE) {
                boardPrinter.printIteration(iteration);
                boardPrinter.print();
            }
            ++iteration;
        });

        createThreads();
    }
    
    public void run() {
        // printing initial setup
        if (VERBOSE) {
            boardPrinter.printIteration(iteration);
            boardPrinter.print();
        }
        ++iteration;

        // start threads
        for (Thread thread : threads) {
            thread.start();
        }

        // waiting for all threads to finish their tasks
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception ex) {

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

            CalculationTask task = new CalculationTask(startIndex, endIndex, board, MAX_ITERATION_COUNT, barrier);
            threads[i] = new Thread(task);

            startIndex = endIndex + 1;
        }
    }
}

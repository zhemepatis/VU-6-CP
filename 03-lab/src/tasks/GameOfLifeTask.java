package tasks;

import models.Board;
import utils.concurrency.CounterLock;
import utils.printers.BoardPrinter;
import utils.wrappers.BooleanWrapper;

public class GameOfLifeTask implements Runnable {
    private final int THREAD_COUNT;
    private Thread[] threads;
    private boolean verbose;

    private CounterLock workersDoneLock;
    private CounterLock procceedCalculationsLock;

    private Board board;
    private int maxIterationCount;
    private BooleanWrapper gameFinished;

    public GameOfLifeTask(int maxIterationCount, Board board, int threadCount, boolean verbose) {
        this.THREAD_COUNT = threadCount;
        this.verbose = verbose;
        
        this.workersDoneLock = new CounterLock();
        this.procceedCalculationsLock = new CounterLock();

        this.board = board;
        this.maxIterationCount = maxIterationCount;
        this.gameFinished = new BooleanWrapper(false);

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
        procceedCalculationsLock.advance();

        // game loop
        while (!gameFinished.value) {
            // wait for all threads to complete their tasks
            awaitWorkerCompletion(iteration*THREAD_COUNT);

            board.applyNextBoard();

            // print iteration
            if (verbose) {
                boardPrinter.printIteration(iteration);
                boardPrinter.print();
            }
            ++iteration;

            if (iteration == maxIterationCount) {
                gameFinished.value = true;
            }
            
            procceedCalculationsLock.advance();
        }

        // waiting for all threads to finish their tasks
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception ex) {

        }
    }

    private void awaitWorkerCompletion(int workerCount) {
        try {
            workersDoneLock.await(workerCount);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted task completion lock await.");
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

            CalculationTask task = new CalculationTask(startIndex, endIndex, board, gameFinished, procceedCalculationsLock, workersDoneLock);
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

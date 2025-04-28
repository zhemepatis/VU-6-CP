package tasks;

import models.Board;
import utils.concurrency.CounterLock;

public class GameOfLifeTask implements Runnable {
    private final int THREAD_COUNT;
    private Thread[] threads;

    private CounterLock barrierLock;
    private boolean proceed;

    private Board board;
    private Boolean gameFinished;

    public GameOfLifeTask(int threadCount, Board board) {
        this.THREAD_COUNT = threadCount;
        this.barrierLock = new CounterLock();

        this.board = board;
        this.gameFinished = Boolean.FALSE;

        initThreads();
    }
    
    @Override
    public void run() {
        while (!gameFinished) {
            runThreads();

            // wait for all threads to complete their tasks
            try {
                barrierLock.await(THREAD_COUNT);
            } catch (InterruptedException ex) {
                System.out.println("Await has been interrupted.");
                break;
            }

            // check if it's time to end the game
            boolean hasChanged = board.hasBoardChanged();
            if (!hasChanged) {
                gameFinished = false;
            }

            board.applyNextBoard();

            // TODO: let other threads know that it is allowed to proceed
        }
    }

    private void initThreads() {
        int cellNum = board.getSize();

        threads = new Thread[THREAD_COUNT];
        int threadShare = cellNum / THREAD_COUNT;
        int remainder = cellNum % THREAD_COUNT;

        int startIndex = 0;
        for (int i = 0; i < THREAD_COUNT; ++i) {
            int endIndex = startIndex + threadShare;
            if (i < remainder) {
                ++endIndex;
            }

            CalculationTask task = new CalculationTask(startIndex, endIndex, board, gameFinished, barrierLock);
            threads[i] = new Thread(task);

            startIndex = endIndex + 1;
        }
    }

    private void runThreads() {
        for (Thread thread : threads) {
            thread.run();
        }
    }
}

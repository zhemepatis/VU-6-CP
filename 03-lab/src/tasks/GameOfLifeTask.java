package tasks;

import models.Board;
import utils.concurrency.CounterLock;
import utils.printers.BoardPrinter;

public class GameOfLifeTask implements Runnable {
    private final int THREAD_COUNT;
    private Thread[] threads;

    private CounterLock taskCompletionLock;
    private CounterLock procceedCalculationsLock;

    private Board board;
    private Boolean gameFinished;

    public GameOfLifeTask(int threadCount, Board board) {
        this.THREAD_COUNT = threadCount;
        this.taskCompletionLock = new CounterLock();
        this.procceedCalculationsLock = new CounterLock();

        this.board = board;
        this.gameFinished = Boolean.FALSE;

        initThreads();
    }
    
    @Override
    public void run() {
        BoardPrinter boardPrinter = new BoardPrinter(board);

        while (!gameFinished) {
            boardPrinter.print();

            runThreads();

            // wait for all threads to complete their tasks
            try {
                taskCompletionLock.await(THREAD_COUNT);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted task completion lock await.");
                break;
            }

            // check if it's time to end the game
            boolean hasChanged = board.hasBoardChanged();
            if (!hasChanged) {
                gameFinished = false;
            }

            board.applyNextBoard();

            // let calculation tasks proceed
            procceedCalculationsLock.advance();
        }
    }

    private void initThreads() {
        int cellNum = board.getSize();

        threads = new Thread[THREAD_COUNT];
        int threadShare = cellNum / THREAD_COUNT;
        int remainder = cellNum % THREAD_COUNT;
        System.out.println(remainder);

        int startIndex = 0;
        for (int i = 0; i < THREAD_COUNT; ++i) {
            int endIndex = startIndex + threadShare - 1;
            if (i < remainder) {
                ++endIndex;
            }

            System.out.println(startIndex);
            System.out.println(endIndex);
            System.out.println();
            CalculationTask task = new CalculationTask(startIndex, endIndex, board, gameFinished, procceedCalculationsLock, taskCompletionLock);
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

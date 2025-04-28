package tasks;

import models.Board;
import utils.concurrency.CounterLock;
import utils.printers.BoardPrinter;
import utils.wrappers.BooleanWrapper;

public class GameOfLifeTask implements Runnable {
    private final int THREAD_COUNT;
    private Thread[] threads;

    private CounterLock workersDoneLock;
    private CounterLock procceedCalculationsLock;

    private Board board;
    private BooleanWrapper gameFinished;

    public GameOfLifeTask(int threadCount, Board board) {
        this.THREAD_COUNT = threadCount;
        
        this.workersDoneLock = new CounterLock();
        this.procceedCalculationsLock = new CounterLock();

        this.board = board;
        this.gameFinished = new BooleanWrapper(false);
    }
    
    @Override
    public void run() {
        int iteration = 0;
        BoardPrinter boardPrinter = new BoardPrinter(board);

        // print initial setup
        System.out.println("Iteration: " + iteration);
        boardPrinter.print();
        ++iteration;

        runThreads();
        System.out.println("GoL advance");
        procceedCalculationsLock.advance();

        while (!gameFinished.value) {
            System.out.println("GoL task cycle");

            // wait for all threads to complete their tasks
            waitForCalculationsToFinish(iteration*THREAD_COUNT);

            // check if it's time to end the game
            boolean hasChanged = board.hasBoardChanged();
            if (hasChanged) {
                board.applyNextBoard();

                System.out.println("Iteration: " + iteration);
                boardPrinter.print();
                ++iteration;
                
                System.out.println("GoL advance");
                procceedCalculationsLock.advance();
                continue;
            }

            gameFinished.value = true;
            System.out.println("GoL advance (for finish)");
            procceedCalculationsLock.advance();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception ex) {

        }

        System.out.println("Game's finished");
    }

    private void waitForCalculationsToFinish(int await) {
        try {
            System.out.println("GoL lock await: " + await);
            workersDoneLock.await(await);
            System.out.println("GoL lock released");
        } catch (InterruptedException ex) {
            System.out.println("Interrupted task completion lock await.");
        }
    }

    private void runThreads() {
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
            threads[i].start();

            startIndex = endIndex + 1;
        }
    }
}

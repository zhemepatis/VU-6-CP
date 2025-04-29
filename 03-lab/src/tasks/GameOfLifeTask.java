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
        boardPrinter.printIteration(iteration);
        boardPrinter.print();
        ++iteration;

        // create and run threads
        runThreads();
        System.out.println("GoL advance");
        procceedCalculationsLock.advance();

        // game loop
        while (!gameFinished.value) {
            System.out.println("GoL task cycle");

            // wait for all threads to complete their tasks
            awaitWorkerCompletion(iteration*THREAD_COUNT);

            // check if it's time to end the game
            boolean hasChanged = board.hasBoardChanged();
            if (!hasChanged) {
                gameFinished.value = true;

                System.out.println("GoL advance (for finish)");
                procceedCalculationsLock.advance();

                continue;
            }

            board.applyNextBoard();

            boardPrinter.printIteration(iteration);
            boardPrinter.print();
            ++iteration;
            
            System.out.println("GoL advance");
            procceedCalculationsLock.advance();
        }

        // waiting for all threads to finish their tasks
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception ex) {

        }

        System.out.println("Game's finished");
    }

    private void awaitWorkerCompletion(int workerCount) {
        try {
            System.out.println("GoL lock await: " + workerCount);
            workersDoneLock.await(workerCount);
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

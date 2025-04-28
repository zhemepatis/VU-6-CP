package tasks;

import models.Board;
import utils.CounterLock;

public class GameOfLifeTask implements Runnable {
    private final int THREAD_COUNT;
    private CounterLock barrierLock;
    private Thread[] threads;

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

        }
    }

    private void initThreads() {
    
    }
}

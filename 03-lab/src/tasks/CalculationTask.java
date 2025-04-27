package tasks;

import java.util.*;
import models.*;

public class CalculationTask implements Runnable {
    private Board board;
    private Queue<Integer> pool;

    public CalculationTask(Board board, Queue<Integer> pool) {
        this.board = board;
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            Integer index;
            synchronized (pool) {
                index = pool.poll();
            }

            if (index == null) {
                break;
            }

            // get cells of interest
            Cell currCell = board.getCell(index);
            List<Cell> adjCells = board.getAdjCells(index);

            // calculating next state
            int markedCellsCount = 0;
            for (Cell cell : adjCells) {
                boolean isMarked = cell.getState();
                
                if (isMarked) {
                    ++markedCellsCount;
                }
            }
            
            currCell.calculateNextState(markedCellsCount);
        }
    }
}

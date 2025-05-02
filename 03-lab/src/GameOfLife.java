import java.util.*;
import models.*;
import tasks.*;

public class GameOfLife {
    private static int dimX;
    private static int dimY;

    private static int threadCount;

    private static boolean verbose;

    public static void main(String[] args) throws Exception {
        parseArgs(args);
        int maxIterationCount = 1000;
        Board board = createBoard();
        
        // create main task and thread
        TaskManager taskManager = new TaskManager(maxIterationCount, board, threadCount, verbose);

        long startTime = System.currentTimeMillis();

        // run thread
        taskManager.run();

        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.;

        System.out.println("Threads: " + threadCount);
        System.out.println("Workload: " + dimX * dimY);
        System.out.printf("Elapsed time: %.4f\n", elapsedTime);
    }

    private static void parseArgs(String[] args) throws Exception {
        dimX = Integer.parseInt(args[0]);
        dimY = Integer.parseInt(args[1]);

        threadCount = Integer.parseInt(args[2]);

        verbose = Integer.parseInt(args[3]) == 1;
    }

    private static Board createBoard() throws Exception {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        List<Coordinates> markedTiles = new ArrayList<>();

        for (int i = 0; i < dimY; ++i) {
            for (int j = 0; j < dimX; ++j) {
                boolean cellState = rnd.nextBoolean();

                if (cellState) {
                    Coordinates coords = new Coordinates(j, i);
                    markedTiles.add(coords);
                }
            }
        }

        // creating map based on input
        return new Board(dimX, dimY, markedTiles);
    }
}

import java.util.*;
import java.io.*;
import models.*;
import tasks.GameOfLifeTask;

public class Main {
    private static int dimX;
    private static int dimY;
    private static int threadCount;
    private static boolean verbose;

    private static final String INPUT_FILE_PATH = "data/input.txt";

    public static void main(String[] args) throws Exception {
        parseArgs(args);
        verbose = false;
        int maxIterationCount = 1000;
        Board board = createBoard();
        
        // create main task and thread
        GameOfLifeTask gameOfLifeTask = new GameOfLifeTask(maxIterationCount, board, threadCount, verbose);
        Thread gameOfLifeThread = new Thread(gameOfLifeTask);

        long startTime = System.currentTimeMillis();

        // run thread
        gameOfLifeThread.run();

        long endTime = System.currentTimeMillis();
        double elapsedTime = (double) (endTime - startTime) / 1000;

        System.out.println("Threads: " + threadCount);
        System.out.println("Workload: " + dimX * dimY);
        System.out.printf("Elapsed time: %.6f\n", elapsedTime);
    }

    private static void parseArgs(String[] args) throws Exception {
        dimX = Integer.parseInt(args[0]);
        dimY = Integer.parseInt(args[1]);

        threadCount = Integer.parseInt(args[2]);

        verbose = Integer.parseInt(args[3]) == 1;
    }

    private static Board createBoard() throws Exception {
        File file = new File(INPUT_FILE_PATH);
        Scanner reader = new Scanner(file);

        // reading marked tiles
        List<Coordinates> markedTiles = new ArrayList<>();

        while (reader.hasNext()) {
            int x = reader.nextInt();
            int y = reader.nextInt();

            Coordinates coords = new Coordinates(x, y);
            markedTiles.add(coords);
        }

        // closing resources
        reader.close();

        // creating map based on input
        return new Board(dimX, dimY, markedTiles);
    }
}

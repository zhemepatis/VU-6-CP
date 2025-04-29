import java.util.*;
import java.io.*;
import models.*;
import tasks.GameOfLifeTask;

public class Main {
    private static final String INPUT_FILE_PATH = "data/input.txt";

    public static void main(String[] args) throws Exception {
        int threadCount = parseArgs(args);
        int maxIterationCount = 1000;
        Board board = createBoard(INPUT_FILE_PATH);
        
        // create main task and thread
        GameOfLifeTask gameOfLifeTask = new GameOfLifeTask(maxIterationCount, board, threadCount);
        Thread gameOfLifeThread = new Thread(gameOfLifeTask);

        // run thread
        gameOfLifeThread.run();

        // wait for thread to finish
        gameOfLifeThread.join();
    }

    private static int parseArgs(String[] args) throws Exception {
        int threadCount = Integer.parseInt(args[0]);
        return threadCount;
    }

    private static Board createBoard(String filePath) throws Exception {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        // reading map dimensions
        int dimX = reader.nextInt();
        int dimY = reader.nextInt();

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

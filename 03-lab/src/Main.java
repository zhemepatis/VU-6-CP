import java.io.File;
import java.util.*;
import models.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "data/input.txt";

    public static void main(String[] args) throws Exception {
        int threadCount = parseArgs(args);
        Map map = createMap(INPUT_FILE_PATH);

        map.printTiles();
    }

    private static int parseArgs(String[] args) throws Exception {
        int threadCount = Integer.parseInt(args[0]);
        return threadCount;
    }

    private static Map createMap(String filePath) throws Exception {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        // reading map dimensions
        int x = reader.nextInt();
        int y = reader.nextInt();

        // reading marked tiles
        List<Integer> markedTiles = new ArrayList<>();

        while (reader.hasNext()) {
            int tileNum = reader.nextInt();
            markedTiles.add(tileNum);
        }

        // closing resources
        reader.close();

        // creating map based on input
        return new Map(x, y, markedTiles);
    }
}

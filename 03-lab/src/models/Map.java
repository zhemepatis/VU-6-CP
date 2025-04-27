package models;
import java.util.*;

public class Map {
    private int x;
    private int y;
    private Tile[] tiles;

    public Map(int x, int y, List<Integer> markedTiles) {
        this.x = x;
        this.y = y;

        initTiles(x, y, markedTiles);
    }

    public void initTiles(int x, int y, List<Integer> markedTiles) {
        int arrayLength = x*y;
        tiles = new Tile[arrayLength];

        for (int i = 0; i < arrayLength; ++i) {
            boolean isMarked = markedTiles.contains(i);
            tiles[i] = new Tile(i, isMarked);
        }
    }

    public void printTiles() {
        for (Tile tile : tiles) {
            System.out.println(tile.getState());
        }
    }
}

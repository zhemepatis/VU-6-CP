package models;

public class Grid {
    protected final int X_DIM;
    protected final int Y_DIM;

    public Grid(int x, int y) {
        this.X_DIM = x;
        this.Y_DIM = y;
    }

    public int getXDimension() {
        return X_DIM;
    }

    public int getYDimension() {
        return Y_DIM;
    }

    public int getSize() {
        return X_DIM*Y_DIM;
    }

    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x < X_DIM && y >= 0 && y < Y_DIM;
    }

    protected Coordinates convertIndexToCoords(int index) {
        int x = index % X_DIM;
        int y = index / X_DIM;

        return new Coordinates(x, y);
    }
}

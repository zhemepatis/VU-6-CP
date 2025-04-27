package models;

public class Coordinates {
    private final int X;
    private final int Y;

    public Coordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Coordinates)) {
            return false;
        }

        Coordinates c = (Coordinates) o;
        return X == c.X && Y == c.Y;
    }
}

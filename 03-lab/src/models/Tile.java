package models;

public class Tile {
    public int index;
    private boolean state; // TRUE - marked, FALSE - not marked
    private boolean previousState; // TRUE - marked, FALSE - not marked

    public Tile(int index, boolean state) {
        this.index = index;
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        previousState = this.state;
        this.state = state;
    }

    public boolean stateHasChanged() {
        return state == previousState;
    }
}

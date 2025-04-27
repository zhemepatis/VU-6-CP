package models;

public class Cell {
    private int index;
    private boolean state;
    private Boolean prevState = null;
    private Boolean nextState = null;

    public Cell(int index, boolean state) {
        this.index = index;
        this.state = state;
    }

    public void calculateNextState(int adjCellCount) {
        if (state) {
            nextState = adjCellCount == 2 || adjCellCount == 3;
        } 
        else {
            nextState = adjCellCount == 3;
        }
    }

    public void exchange() {
        prevState = state;
        state = nextState;
        nextState = null;
    }

    public boolean hasChanged() {
        return prevState != state;
    } 

    public int getIndex() {
        return index;
    }

    public boolean getState() {
        return state;
    }
}

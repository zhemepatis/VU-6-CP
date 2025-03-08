package utils;

// TODO: make locks sortable so that lock time would be shorter
public class PartialLock {
    private int idxFrom;
    private int idxTo;

    public PartialLock(int idxFrom, int idxTo) {
        this.idxFrom = idxFrom;
        this.idxTo = idxTo;
    }

    public int getIdxFrom() {
        return idxFrom;
    }

    public int getIdxTo() {
        return idxTo;
    }
}

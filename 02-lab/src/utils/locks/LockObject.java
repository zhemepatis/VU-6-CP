package utils.locks;

// TODO: make locks sortable so that lock time would be shorter
class LockObject {
    private int idxFrom;
    private int idxTo;

    protected LockObject(int idxFrom, int idxTo) {
        this.idxFrom = idxFrom;
        this.idxTo = idxTo;
    }

    protected int getIdxFrom() {
        return idxFrom;
    }

    protected int getIdxTo() {
        return idxTo;
    }
}
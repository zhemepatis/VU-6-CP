package utils.locks;

import java.util.List;
import java.util.ArrayList;
import utils.ArrayUtils;

public class ArrayLock<T> {
    private List<RangeLock> locks;

    public ArrayLock() {
        this.locks = new ArrayList<RangeLock>();
    }

    public void lock(int idxFrom, int idxTo) throws InterruptedException {
        while (true) {
            boolean lockIsPermitted = true;
            RangeLock currLock = null;    // TODO: change to something more making sense

            synchronized (locks) {
                for (RangeLock lock : locks) {
                    currLock = lock;
                    int lockIdxFrom = currLock.getIdxFrom();
                    int lockIdxTo = currLock.getIdxTo();
                    
                    if (ArrayUtils.isWithinRange(lockIdxFrom, lockIdxTo, idxTo) || 
                        ArrayUtils.isWithinRange(lockIdxFrom, lockIdxTo, idxFrom)) {
                        lockIsPermitted = false;
                        break;
                    }
                }

                if (lockIsPermitted) {
                    RangeLock newLock = new RangeLock(idxFrom, idxTo);
                    locks.add(newLock);
                }
            }

            if (!lockIsPermitted)
                currLock.wait();
            else 
                break;
        }
    }

    public void unlock(int indexFrom, int indexTo) {
        // TODO: implement
    }
}

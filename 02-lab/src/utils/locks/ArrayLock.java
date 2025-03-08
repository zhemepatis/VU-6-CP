package utils.locks;

import java.util.List;
import java.util.ArrayList;
import utils.ArrayUtils;

public class ArrayLock<T> {
    private List<PartialLock> locks;

    public ArrayLock() {
        this.locks = new ArrayList<PartialLock>();
    }

    public void lock(int idxFrom, int idxTo) throws InterruptedException {
        while (true) {
            boolean lockIsPermitted = true;
            PartialLock currLock = null;    // TODO: change to something more making sense

            synchronized (locks) {
                for (PartialLock lock : locks) {
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
                    PartialLock newLock = new PartialLock(idxFrom, idxTo);
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

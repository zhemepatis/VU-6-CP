package utils.locks;

import java.util.List;
import java.util.ArrayList;
import utils.ArrayUtils;

public class ArrayLock<T> {
    private List<LockObject> locks;

    public ArrayLock() {
        this.locks = new ArrayList<LockObject>();
    }

    public void lock(int idxFrom, int idxTo) throws InterruptedException {
        while (true) {
            LockObject conflictingLock;

            synchronized (locks) {
                conflictingLock = hasConflictingLock(idxFrom, idxTo);

                if (conflictingLock == null) {
                    LockObject newLock = new LockObject(idxFrom, idxTo);
                    locks.add(newLock);
                }
            }

            if (conflictingLock != null)
                conflictingLock.wait();
            else 
                break;
        }
    }

    private LockObject hasConflictingLock(int idxFrom, int idxTo) {
        for (LockObject lock : locks) {
            int lockIdxFrom = lock.getIdxFrom();
            int lockIdxTo = lock.getIdxTo();
            
            if (ArrayUtils.isWithinRange(lockIdxFrom, lockIdxTo, idxTo) || 
                ArrayUtils.isWithinRange(lockIdxFrom, lockIdxTo, idxFrom)) {
                return lock;
            }
        }

        return null;
    }

    public void unlock(int indexFrom, int indexTo) {
        // TODO: implement
    }
}

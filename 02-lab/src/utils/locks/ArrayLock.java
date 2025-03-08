package utils.locks;

import java.util.List;
import java.util.ArrayList;
import utils.ArrayUtils;

public class ArrayLock {
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
                    break;
                }
            }

            synchronized (conflictingLock) {
                conflictingLock.wait();
            }
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

    public void unlock(int idxFrom, int idxTo) {
        LockObject currLock = null;
        
        synchronized (locks) {
            for (LockObject lock : locks) {
                int lockIdxFrom = lock.getIdxFrom();
                int lockIdxTo = lock.getIdxTo();
    
                if (lockIdxFrom == idxFrom && lockIdxTo == idxTo) {
                    locks.remove(lock);
                    currLock = lock;
                    break;
                }
            }
        }

        synchronized (currLock) {
            if (currLock != null){
                currLock.notify();
            }
        }
    }
}

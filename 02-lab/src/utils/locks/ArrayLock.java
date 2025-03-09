package utils.locks;

import java.util.List;
import java.util.ArrayList;
import utils.ArrayUtils;

public class ArrayLock {
    private List<LockObject> locks;

    public ArrayLock() {
        this.locks = new ArrayList<LockObject>();
    }

    // TODO: handle Interrupted exception
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
        LockObject lockToRelease = null;
        
        synchronized (locks) {
            lockToRelease = getLockObject(idxFrom, idxTo);
            
            if (lockToRelease != null) {
                locks.remove(lockToRelease);
            }
        }

        synchronized (lockToRelease) {
            lockToRelease.notify();
        }
    }

    private LockObject getLockObject(int idxFrom, int idxTo) {
        LockObject lock = new LockObject(idxFrom, idxTo);

        int lockIdx = locks.indexOf(lock);
        if (lockIdx == -1) {
            return null;
        }

        return locks.get(lockIdx);
    }
}

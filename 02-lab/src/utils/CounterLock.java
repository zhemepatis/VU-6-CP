package utils;

public class CounterLock {
    private int counterValue;

    public CounterLock() {
        this.counterValue = 0;
    }

    // kaip suprasti nedalomas?
    public synchronized void advance() {
        counterValue += 1;
        this.notifyAll();
    }

    // ar reikia ir kdl ant reado reikia sync?
    public synchronized int read() {
        return counterValue;
    }

    public synchronized void await(int value) throws InterruptedException {
        while (counterValue < value) {
            this.wait();
        }
    }
}
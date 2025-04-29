package utils.concurrency;

public class CounterLock {
    private int counterValue;

    public CounterLock() {
        this.counterValue = 0;
    }

    public synchronized void advance() {
        counterValue += 1;
        this.notifyAll();
    }

    public synchronized int read() {
        return counterValue;
    }

    public synchronized void await(int value) throws InterruptedException {
        while (counterValue < value) {
            this.wait();
        }
    }
}
package tasks;

import utils.CounterLock;

public class ConsumerTask implements Runnable {
    private Integer[] buffer;
    private int bufferSize;
    private CounterLock producedCounter;

    private int sum;

    public ConsumerTask(Integer[] buffer, CounterLock producedCounter) {
        this.buffer = buffer;
        this.bufferSize = buffer.length;
        this.producedCounter = producedCounter;
        this.sum = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < bufferSize; ++i) {
            try {
                producedCounter.await(i+1);
                int item = buffer[i];
                sum += item;
            }
            catch (InterruptedException ex) {
                System.out.println("Task was interrupted");
            }
        }

        System.out.println("Consumer finished working");
    }

    public int getSum() {
        return sum;
    }
}

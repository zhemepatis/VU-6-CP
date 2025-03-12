package tasks;

import utils.CounterLock;
import java.util.List;

public class ConsumerTask implements Runnable {
    private List<Integer> buffer;
    private int bufferSize;
    private CounterLock producedCounter;
    private int sum;

    public ConsumerTask(List<Integer> buffer, CounterLock producedCounter) {
        this.buffer = buffer;
        this.bufferSize = buffer.size();
        this.producedCounter = producedCounter;
        this.sum = 0;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public void run() {
        for (int i = 0; i < bufferSize; ++i) {
            try {
                producedCounter.await(i+1);
                int item = buffer.get(i);
                sum += item;
            }
            catch (InterruptedException ex) {
                System.out.println("Task was interrupted");
            }
        }
    } 
}

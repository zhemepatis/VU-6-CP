package tasks;

import utils.CounterLock;
import java.util.List;

public class ProducerTask implements Runnable {
    private List<Integer> buffer;
    private int bufferSize;
    private CounterLock producedCounter;


    public ProducerTask(List<Integer> buffer, CounterLock producedCounter) {
        this.buffer = buffer;
        this.bufferSize = buffer.size();
        this.producedCounter = producedCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < bufferSize; ++i) {
            buffer.set(i, 1);
            producedCounter.advance();
        }
    }
}

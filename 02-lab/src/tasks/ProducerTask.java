package tasks;

import utils.CounterLock;

public class ProducerTask implements Runnable {
    private Integer[] buffer;
    private int bufferSize;
    private CounterLock producedCounter;

    public ProducerTask(Integer[] buffer, CounterLock producedCounter) {
        this.buffer = buffer;
        this.bufferSize = buffer.length;
        this.producedCounter = producedCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < bufferSize; ++i) {
            buffer[i] = 1;
            producedCounter.advance();
        }

        System.out.println("Poducer finished working");
    }
}

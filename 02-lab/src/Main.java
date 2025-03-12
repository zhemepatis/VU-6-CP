import tasks.ProducerTask;
import utils.CounterLock;
import tasks.ConsumerTask;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int REQUIRED_PLAYERS = 25;

    public static void main(String[] args) throws Exception {
        List<Integer> buffer = new ArrayList<Integer>();
        initBuffer(buffer, 10);
        CounterLock producedCounter = new CounterLock();

        // creating tasks and threads
        ProducerTask producerTask = new ProducerTask(buffer, producedCounter);
        ConsumerTask consumerTask = new ConsumerTask(buffer, producedCounter);
        Thread producerThread = new Thread(producerTask);
        Thread consumerThread = new Thread(consumerTask);

        // running threads
        producerThread.start();
        consumerThread.start();

        // waiting for all threads to finish their tasks
        producerThread.join();
        consumerThread.join();

        // getting and printing results
        int consumerResult = consumerTask.getSum();
        System.out.println("Consumer result: " + consumerResult);
    }

    public static void initBuffer(List<Integer> buffer, int bufferSize) {
        for (int i = 0; i < bufferSize; ++i) {
            buffer.add(0);
        }
    }
}

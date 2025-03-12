import tasks.ProducerTask;
import utils.CounterLock;
import tasks.ConsumerTask;

public class Main {
    public static final int BUFFER_SIZE = 1000;

    public static void main(String[] args) throws Exception {
        // creating shared variables
        Integer[] buffer = new Integer[BUFFER_SIZE];

        CounterLock producedCounter = new CounterLock();

        // creating producer task and thread
        ProducerTask producerTask = new ProducerTask(buffer, producedCounter);
        Thread producerThread = new Thread(producerTask);

        // creating consumer tasks and threads
        int consumerNum = 5;
        ConsumerTask[] consumerTasks = new ConsumerTask[consumerNum];
        Thread[] consumerThreads = new Thread[consumerNum];

        for (int i = 0; i < consumerNum; ++i) {
            consumerTasks[i] = new ConsumerTask(buffer, producedCounter);
            consumerThreads[i] = new Thread(consumerTasks[i]);
        }

        // running threads
        producerThread.start();

        for (Thread thread : consumerThreads) {
            thread.start();
        }

        // waiting for all threads to finish their tasks
        producerThread.join();

        for (Thread thread : consumerThreads) {
            thread.join();
        }

        // getting and printing results
        for (int i = 0; i < consumerNum; ++i) {
            int taskResult = consumerTasks[i].getSum();
            System.out.println("Consumer task " + (i + 1) + " result: " + taskResult);
        }
    }
}

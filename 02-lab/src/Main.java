import utils.locks.ArrayLock;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayLock arrLock = new ArrayLock();

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread1 started.");

            try {

                System.out.println("Locking the range.");
                arrLock.lock(1, 5);

                System.out.println("Prepare to sleep.");
                Thread.sleep(5000);

                System.out.println("Unlocking the range.");
                arrLock.unlock(1, 5);
            }
            catch (InterruptedException ex) {
                System.out.println("Interruption in thread1");
            }

            System.out.println("Thread1 finished.");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread2 started.");

            try {
                System.out.println("Locking the range.");
                arrLock.lock(1, 5);
            }
            catch (InterruptedException ex) {
                System.out.println("Interruption in thread2");
            }

            System.out.println("Thread2 finished.");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

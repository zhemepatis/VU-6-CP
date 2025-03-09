import utils.locks.ArrayLock;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayLock arrLock = new ArrayLock();

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread started - Thread1");

            try {
                arrLock.lock(1, 5);
            }
            catch (InterruptedException ex) {}

            System.out.println("Thread finished - Thread1");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread started - Thread2");

            try {
                Thread.sleep(5000);
                arrLock.unlock(1, 5);
            }
            catch (InterruptedException ex) {}


            System.out.println("Thread finished - Thread2");
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("Thread started - Thread3");

            try {
                arrLock.lock(1, 5);
            }
            catch (InterruptedException ex) {}

            System.out.println("Thread finished - Thread3");
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }
}

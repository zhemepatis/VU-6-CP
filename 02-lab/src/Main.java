import utils.CounterLock;

public class Main {
    public static void main(String[] args) throws Exception {
       CounterLock lock = new CounterLock();
       
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread started - Thread1");

            try {
                lock.await(20);
            }
            catch (InterruptedException ex) {}

            System.out.println("Thread finished - Thread1");
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("Thread started - Thread3");

            try {
                lock.await(50);
            }
            catch (InterruptedException ex) {}

            System.out.println("Thread finished - Thread3");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread started - Thread2");

            for (int i = 0; i < 50; ++i) {
                lock.advance();
                System.out.println("i: " + i);
            }

            System.out.println("Thread finished - Thread2");
        });


       thread1.start();
       thread2.start();
       thread3.start();

       thread1.join();
       thread2.join();
       thread3.join();
    }
}

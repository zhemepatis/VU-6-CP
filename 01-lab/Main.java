import models.Restaurant;

public class Main {
    public static void main(String[] args) throws Exception {
        Restaurant restaurant = new Restaurant("Gusteu's", 3);

        // creating and starting threads
        Thread thread1 = new Thread(() -> restaurant.reserveTable(2, "user 1", 5000));
        Thread thread2 = new Thread(() -> restaurant.reserveTable(2, "user 2", 0));
        thread1.start();
        thread2.start();

        // waiting for threads to finish
        thread1.join();
        thread2.join();

        restaurant.printAvailability();
    }
}

import models.Restaurant;

public class Main {
    public static void main(String[] args) throws Exception {
        Restaurant restaurant = new Restaurant("Gusteu's", 5);

        Thread thread1 = new Thread(() -> restaurant.reserveTable(2, "user 1"));
        Thread thread2 = new Thread(() -> restaurant.reserveTable(2, "user 2"));
        
        thread1.start();
        thread2.start();

        restaurant.printAvailability();
    }
}

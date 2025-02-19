// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Pradinė funkcija, modeliuojanti situaciją,
// kai 2 žmonės nori užsirezervuoti tą patį staliuką.

import models.Restaurant;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean useLock = false;
        Restaurant restaurant = new Restaurant("Gusteu's", 100);

        // creating and starting threads
        Thread thread1 = new Thread(() -> restaurant.reserveTable(99, "user 1", useLock));
        Thread thread2 = new Thread(() -> restaurant.reserveTable(99, "user 2", useLock));
        thread1.start();
        thread2.start();

        // waiting for threads to finish
        thread1.join();
        thread2.join();

        restaurant.printAvailability();
    }
}

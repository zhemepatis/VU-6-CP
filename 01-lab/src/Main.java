// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Pradinė funkcija, modeliuojanti situaciją,
// kai 2 žmonės nori užsirezervuoti tą patį staliuką.

import models.Restaurant;
import utils.TableReservationTask;

public class Main {
    public static void main(String[] args) throws Exception {
        // setting demonstration options
        boolean useLock = false;
        int restaurantTableNum = 100000;

        Restaurant restaurant = new Restaurant("Gusteu's", restaurantTableNum);

        // creating and starting threads
        TableReservationTask task1 = new TableReservationTask(restaurant, "Thread1", useLock);
        TableReservationTask task2 = new TableReservationTask(restaurant, "Thread2", useLock);
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        // waiting for threads to finish
        thread1.join();
        thread2.join();
        
        // printing results
        // restaurant.printAvailability();

        task1.printResults();
        task2.printResults();

        System.out.println();
        System.out.println("Restaurant has available tables: " + restaurant.hasAvailableTable());
    }
}
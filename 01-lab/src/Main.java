// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Pradinė funkcija, modeliuojanti situaciją,
// kai 2 žmonės nori užsirezervuoti tą patį staliuką.

import models.Restaurant;

public class Main {
    public static void main(String[] args) throws Exception {
        // demonstration options
        boolean useLock = true;
        int restaurantTableNum = 1000;
        int threadTableTarget = 500;

        Restaurant restaurant = new Restaurant("Gusteu's", restaurantTableNum);
        
        // creating and starting threads
        Thread thread1 = new Thread(() -> startReservationProcess(restaurant, "Thread1", threadTableTarget, useLock));
        Thread thread2 = new Thread(() -> startReservationProcess(restaurant, "Thread2", threadTableTarget, useLock));
        thread1.start();
        thread2.start();
        
        // waiting for threads to finish
        thread1.join();
        thread2.join();
        
        // printing results
        restaurant.printAvailability();
    }

    static private void startReservationProcess(Restaurant restaurant, String reservationName, int tableTarget, boolean useLock) {
        int restaurantTableNum = restaurant.getTableNum();
        int currTable = 1;
        int reservedTables = 0;

        while (reservedTables < tableTarget && currTable <= restaurantTableNum) {
            boolean reservationSuccess = restaurant.reserveTable(currTable, reservationName, useLock);

            if (reservationSuccess) {
                reservedTables++;
            }

            currTable++;
        }

        System.out.println("Proccess finished for " + reservationName + ".");
        System.out.println("Number of last reserved table: " + (currTable - 1));
        System.out.println("Tables reserved: " + reservedTables);
    }
}
import models.Restaurant;
import utils.TableReservationTask;

public class Main {
    public static void main(String[] args) throws Exception {
        // setting demonstration options
        boolean useLock = true;
        int restaurantTableNum = 100000;

        Restaurant restaurant = new Restaurant("Gusteu's", restaurantTableNum);

        // creating and starting threads
        String reservationName1 = "Thread1";
        String reservationName2 = "Thread2";
        TableReservationTask task1 = new TableReservationTask(restaurant, reservationName1, useLock);
        TableReservationTask task2 = new TableReservationTask(restaurant, reservationName2, useLock);
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        // waiting for threads to finish
        thread1.join();
        thread2.join();

        // getting and printing the results
        int lastReservedTable1 = task1.getLastReservedTable();
        int reservationCount1 = task1.getReservationCount();
        printResults(reservationName1, lastReservedTable1, reservationCount1);

        int lastReservedTable2 = task2.getLastReservedTable();
        int reservationCount2 = task2.getReservationCount();
        printResults(reservationName2, lastReservedTable2, reservationCount2);

        // printing general results
        System.out.println();
        System.out.println("Reserved tables in total: " + (reservationCount1 + reservationCount2));
        System.out.println("Restaurant has available tables: " + restaurant.hasAvailableTable());
    }

    public static void printResults(String reservationName, int lastReservedTable, int reservationCount) {
        System.out.println();
        System.out.println(reservationName + " results:");
        System.out.println("Number of last reserved table: " + lastReservedTable);
        System.out.println("Tables reserved: " + reservationCount);
    }
}
package utils;

import models.Restaurant;

public class TableReservationTask implements Runnable {
    private Restaurant restaurant;
    private String reservationName;
    private boolean useLock;
    private int lastReservedTableNum;
    private int reservedTablesNum;

    public TableReservationTask(Restaurant restaurant, String reservationName, boolean useLock) {
        this.restaurant = restaurant;
        this.reservationName = reservationName;
        this.useLock = useLock;
    }
    
    @Override
    public void run() {
        int restaurantTableNum = restaurant.getTableNum();
        int currTable = 1;

<<<<<<< HEAD
        while (this.reservedTablesNum < tableTarget && currTable <= restaurantTableNum) {
=======
        while (currTable <= restaurantTableNum) {
>>>>>>> no-table-target
            boolean reservationSuccess = restaurant.reserveTable(currTable, reservationName, useLock);

            if (reservationSuccess) {
                this.reservedTablesNum++;
                this.lastReservedTableNum = currTable;
            }

            currTable++;
        }
    }

    public void printResults() {
        System.out.println();
        System.out.println(reservationName + " results:");
        System.out.println("Number of last reserved table: " + lastReservedTableNum);
        System.out.println("Tables reserved: " + reservedTablesNum);
    }
}

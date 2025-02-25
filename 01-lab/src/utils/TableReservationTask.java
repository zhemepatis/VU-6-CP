package utils;

import models.Restaurant;

public class TableReservationTask implements Runnable {
    private Restaurant restaurant;
    private String reservationName;
    private int tableTarget;
    private boolean useLock;
    private int lastReservedTableNum;
    private int reservedTablesNum;

    public TableReservationTask(Restaurant restaurant, String reservationName, int tableTarget, boolean useLock) {
        this.restaurant = restaurant;
        this.reservationName = reservationName;
        this.tableTarget = tableTarget;
        this.useLock = useLock;
    }
    
    @Override
    public void run() {
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

        this.lastReservedTableNum = currTable - 1;
        this.reservedTablesNum = reservedTables;
    }

    public void printResults() {
        System.out.println();
        System.out.println(reservationName + " results:");
        System.out.println("Number of last reserved table: " + lastReservedTableNum);
        System.out.println("Tables reserved: " + reservedTablesNum);
    }
}

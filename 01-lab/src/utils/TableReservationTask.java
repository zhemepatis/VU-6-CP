package utils;

import models.Restaurant;

public class TableReservationTask implements Runnable {
    private Restaurant restaurant;
    private String reservationName;
    private boolean useLock;
    private int lastReservedTable;
    private int reservationCount;

    public TableReservationTask(Restaurant restaurant, String reservationName, boolean useLock) {
        this.restaurant = restaurant;
        this.reservationName = reservationName;
        this.useLock = useLock;
    }
    
    @Override
    public void run() {
        int restaurantTableNum = restaurant.getTableNum();
        int currTable = 1;

        while (currTable <= restaurantTableNum) {
            boolean reservationSuccess = restaurant.reserveTable(currTable, reservationName, useLock);

            if (reservationSuccess) {
                this.reservationCount++;
                this.lastReservedTable = currTable;
            }

            currTable++;
        }
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public int getLastReservedTable() {
        return lastReservedTable;
    }
}

// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Restorano modelis, skirtas apibrėžti restorano staliukų rezervavimo logiką.

package models;

import java.util.HashMap;
import java.util.Map;

import utils.RestaurantPrinter;

public class Restaurant {
    private String name;
    private Map<Integer, String> tableReservations;

    public Restaurant(String name, int tableNum) {
        this.name = name;
        this.tableReservations = new HashMap<>();

        initialiseTables(tableNum);
    }

    private void initialiseTables(int tableNum) {
        for (var i = 1; i <= tableNum; ++i) {
            tableReservations.put(i, null);
        }
    }

    public String getName() {
        return name;
    }

    public void reserveTable(int number, String reservationName, int timeout, boolean useLock) {
        boolean reservationSuccess;
        if (useLock)
            reservationSuccess = addEntryWithLock(number, reservationName, timeout);
        else
            reservationSuccess = addEntryNoLock(number, reservationName, timeout);
        
        RestaurantPrinter.printReservationStatus(number, reservationName, reservationSuccess);
    }

    private boolean addEntryNoLock(int number, String reservationName, int timeout) {
        if (tableReservations.containsKey(number) && tableReservations.get(number) == null) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {

            }

            tableReservations.put(number, reservationName);
            return true;
        }

        return false;
    }

    private boolean addEntryWithLock(int number, String reservationName, int timeout) {
        synchronized (tableReservations) {
            if (tableReservations.containsKey(number) && tableReservations.get(number) == null) {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
    
                }
    
                tableReservations.put(number, reservationName);
                return true;
            }
        }

        return false;
    }

    public void printAvailability() {
        RestaurantPrinter.printAvailability(tableReservations);
    }
}

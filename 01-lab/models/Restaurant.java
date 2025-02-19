// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Restorano modelis, skirtas apibrėžti restorano staliukų rezervavimo logiką.

package models;

// import java.util.HashMap;
// import java.util.Map;
import java.util.*;
import models.Reservation;
import utils.RestaurantPrinter;

public class Restaurant {
    private String name;
    private List<Reservation> tableReservations;

    public Restaurant(String name, int tableNum) {
        this.name = name;
        this.tableReservations = new ArrayList<Reservation>(tableNum + 1);

        initialiseTables(tableNum);
    }

    public String getName() {
        return name;
    }

    private void initialiseTables(int tableNum) {
        for (var i = 0; i < tableNum; ++i) {
            Reservation reservation = new Reservation(false);
            tableReservations.add(i, reservation);
        }
    }

    public void reserveTable(int number, String reservationName, boolean useLock) {
        boolean reservationSuccess;
        if (useLock)
            reservationSuccess = addEntryWithLock(number, reservationName);
        else
            reservationSuccess = addEntryNoLock(number, reservationName);
        
        RestaurantPrinter.printReservationStatus(number, reservationName, reservationSuccess);
    }

    private boolean addEntryNoLock(int number, String reservationName) {
        int idx = number - 1;
        Reservation reservation = tableReservations.get(idx);
        if (!reservation.checkIfReserved()) {
            reservation.makeReservation(reservationName);
            tableReservations.set(idx, reservation);

            return true;
        }

        return false;
    }

    private boolean addEntryWithLock(int number, String reservationName) {
        int idx = number - 1;

        synchronized (tableReservations) {
            Reservation reservation = tableReservations.get(idx);
            if (!reservation.checkIfReserved()) {
                reservation.makeReservation(reservationName);
                tableReservations.set(idx, reservation);
    
                return true;
            }
        }

        return false;
    }


    public void printAvailability() {
        RestaurantPrinter.printAvailability(tableReservations);
    }
}

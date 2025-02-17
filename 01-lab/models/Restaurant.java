package models;

import java.util.HashMap;
import java.util.Map;

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

    public boolean reserveTable(int number, String reservationName) {
        if (tableReservations.get(number) == null) {
            tableReservations.put(number, reservationName);
            return true;
        }

        return false;
    }

    public void printAvailability() {
        for (var entry: tableReservations.entrySet()) {
            System.out.print("Table number " + entry.getKey() + ": ");

            String reservationName = entry.getValue();
            if (reservationName != null) System.out.println("is reserved by " + reservationName);
            else System.out.println("is available");
        }
    }
}

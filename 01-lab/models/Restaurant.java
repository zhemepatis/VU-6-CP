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
    
    public void reserveTable(int number, String reservationName, int timeout) {
        if (tableReservations.containsKey(number) && tableReservations.get(number) == null) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {

            }

            tableReservations.put(number, reservationName);

            System.out.println("Reserved table number " + number + " for " + reservationName);
            return;
        }

        System.out.println("Couldn't reserve table number " + number + " for " + reservationName);
    }

    public void printAvailability() {
        System.out.println("\nPrinting restaurant availability:");

        for (var entry: tableReservations.entrySet()) {
            System.out.print("Table number " + entry.getKey() + ": ");

            String reservationName = entry.getValue();
            if (reservationName != null)
                System.out.println("is reserved by " + reservationName);
            else
                System.out.println("is available");
        }
    }
}

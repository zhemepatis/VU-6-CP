package utils;

import java.util.Map;

public class RestaurantPrinter {
    public static void printReservationStatus(int number, String reservationName, boolean success) {
        if (success)
            System.out.println("Reserved table number " + number + " for " + reservationName);
        else
            System.out.println("Couldn't reserve table number " + number + " for " + reservationName);
    }

    public static void printAvailability(Map<Integer, String> tableReservations) {
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

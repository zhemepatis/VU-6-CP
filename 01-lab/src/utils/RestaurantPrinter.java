// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Pagalbinė klasė, spausdinanti rezervavimo būsenas.

package utils;

import java.util.*;
import models.Table;

public class RestaurantPrinter {
    public static void printReservationStatus(int number, String reservationName, boolean success) {
        if (success)
            System.out.println("Reserved table number " + number + " for " + reservationName);
        else
            System.out.println("Couldn't reserve table number " + number + " for " + reservationName);
    }

    public static void printAvailability(List<Table> tableReservations) {
        System.out.println("\nPrinting restaurant availability:");
        int tableNum = tableReservations.size();

        for (int i = 0; i < tableNum; ++i) {
            System.out.print("Table number " + (i + 1) + ": ");

            Table table = tableReservations.get(i);
            String reservationName = table.getReservationName();
            if (reservationName != null)
                System.out.println("is reserved by " + reservationName);
            else
                System.out.println("is available");
        }
    }
}

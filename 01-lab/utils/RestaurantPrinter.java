// Gabrielė Rinkevičiūtė, Informatika 3 k., 2 g.
// Pagalbinė klasė, spausdinanti rezervavimo būsenas.

package utils;

import java.util.*;
import models.Reservation;

public class RestaurantPrinter {
    public static void printReservationStatus(int number, String reservationName, boolean success) {
        if (success)
            System.out.println("Reserved table number " + number + " for " + reservationName);
        else
            System.out.println("Couldn't reserve table number " + number + " for " + reservationName);
    }

    public static void printAvailability(List<Reservation> tableReservations) {
        System.out.println("\nPrinting restaurant availability:");
        int tableNum = tableReservations.size();

        for (int i = 0; i < tableNum; ++i) {
            System.out.print("Table number " + (i + 1) + ": ");

            Reservation reservation = tableReservations.get(i);
            String reservationName = reservation.getReservationName();
            if (reservationName != null)
                System.out.println("is reserved by " + reservationName);
            else
                System.out.println("is available");
        }
    }
}

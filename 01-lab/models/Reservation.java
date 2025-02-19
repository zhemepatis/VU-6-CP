package models;

public class Reservation {
    private String reservationName;
    private boolean isReserved;

    public Reservation(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public void makeReservation(String reservationName) {
        this.reservationName = reservationName;
        isReserved = true;
    }

    public boolean checkIfReserved() {
        return isReserved;
    }

    public String getReservationName() {
        return reservationName;
    }
}

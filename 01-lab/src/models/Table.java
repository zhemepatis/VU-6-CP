package models;

public class Table {
    private boolean isReserved;
    private String reservationName;

    public Table() {
        this.isReserved = false;
    }

    public void reserve(String reservationName) {
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

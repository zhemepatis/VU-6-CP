package models;

import java.util.*;
import utils.RestaurantPrinter;

public class Restaurant {
    private String name;
    private int tableNum;
    private List<Table> tables;

    public Restaurant(String name, int tableNum) {
        this.name = name;
        this.tableNum = tableNum;
        this.tables = new ArrayList<Table>(tableNum + 1);

        initialiseTables(tableNum);
    }

    public String getName() {
        return name;
    }
    
    public int getTableNum() {
        return tableNum;
    }

    private void initialiseTables(int tableNum) {
        for (var i = 0; i < tableNum; ++i) {
            Table table = new Table();
            tables.add(i, table);
        }
    }

    public boolean reserveTable(int number, String reservationName, boolean useLock) {
        boolean reservationSuccess;
        if (useLock)
            reservationSuccess = addEntryWithLock(number, reservationName);
        else
            reservationSuccess = addEntryNoLock(number, reservationName);
        
        return reservationSuccess;
    }

    private boolean addEntryNoLock(int number, String reservationName) {
        int idx = number - 1;
        Table table = tables.get(idx);

        if (!table.isReserved()) {
            table.reserve(reservationName);
            return true;
        }

        return false;
    }

    private boolean addEntryWithLock(int number, String reservationName) {
        int idx = number - 1;

        Table table = tables.get(idx);
        synchronized (tables) {
            if (!table.isReserved()) {
                table.reserve(reservationName);
                return true;
            }
        }

        return false;
    }

    public boolean hasAvailableTable() {
        for(Table table: tables) {
            if(!table.isReserved())
                return true;
        }

        return false;
    }

    public void printAvailability() {
        RestaurantPrinter.printAvailability(tables);
    }
}

package com.example.calendarapp.database;

import com.example.calendarapp.calendar.Alert;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an Alert converted for database storage.
 * @author Osman Conger
 */
public class DatabaseAlert {

    public String date;
    public String time;
    public String id;

    /**
     * Constructor for class DatabaseAlert.
     *
     * @param  alert  the Alert object associated with this DatabaseAlert object
     */
    public DatabaseAlert(Alert alert) {
        this.date = alert.getDate().toString();
        this.time = alert.getTime().toString();
        this.id = alert.id;
    }

}

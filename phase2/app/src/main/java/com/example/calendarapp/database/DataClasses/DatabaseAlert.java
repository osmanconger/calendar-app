package com.example.calendarapp.database.DataClasses;

import com.example.calendarapp.calendar.DataClasses.Alert;

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

    public DatabaseAlert() {}

}

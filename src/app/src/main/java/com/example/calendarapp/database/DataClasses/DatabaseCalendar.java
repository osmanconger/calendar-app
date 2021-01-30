package com.example.calendarapp.database.DataClasses;

import com.example.calendarapp.calendar.DataClasses.Calendar;

/**
 * Represents an Calender object converted for database storage.
 * @author Vaibhav Holani
 */
public class DatabaseCalendar {

    public String id;
    public String name;
    public String username;

    /** Constructor for DatabaseCalendar.
     *
     * @param calendar The calendar object associated with the DatabaseCalendar object.
     */
    public DatabaseCalendar(Calendar calendar){
        this.id = Integer.toString(calendar.id);
        this.name = calendar.name;
        this.username = calendar.username;

    }

}

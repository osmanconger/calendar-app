package com.example.calendarapp.calendar.DataClasses;

import com.example.calendarapp.calendar.HandlerClasses.*;

import java.io.Serializable;

public class Calendar implements Serializable {

    /**
     *
     * Represents a user's calendar.
     * @author Vaibhav Holani
     */



    public String name;

    public int id;

    public String username;

    public EventHandler eventHandler;

    public AlertHandler alertHandler;

    public MemoHandler memoHandler;


    /** Constructor of the Calendar class.
     *
     * @param calendarName of the new calendar object.
     * @param username username of the user of this calendar object.
     */
    public Calendar(String calendarName, String username){
        this.name = calendarName;
        this.username = username;
        this.eventHandler = new EventHandler(username, calendarName);
        this.alertHandler = new AlertHandler(username, calendarName);
        this.memoHandler = new MemoHandler(username, calendarName);
    }
}

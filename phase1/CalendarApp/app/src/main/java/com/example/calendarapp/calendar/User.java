package com.example.calendarapp.calendar;

import com.example.calendarapp.calendar.AlertHandler;
import com.example.calendarapp.calendar.EventHandler;
import com.example.calendarapp.calendar.MemoHandler;

import java.io.Serializable;

/**
 * Represents an app user.
 * @author Wisam Mohiuddin
 */
public class User implements Serializable {

    private String name;
    /** Represents the username of User.
     */
    public String username;
    /** Represents the EventHandler associated with this User.
     */
    public EventHandler eventHandler;
    /** Represents the AlertHandler associated with this User.
     */
    public AlertHandler alertHandler;
    /** Represents the MemoHandler associated with this User.
     */
    public MemoHandler memoHandler;

    /**
     * Constructor for class User.
     *
     * @param  username  the String for username of User
     * @param  name      the String for name of User
     */
    public User(String username, String name) {
        this.username = username;
        this.name = name;
        this.eventHandler = new EventHandler(this.username);
        this.alertHandler = new AlertHandler(this.username);
        this.memoHandler = new MemoHandler(this.username);
    }
}

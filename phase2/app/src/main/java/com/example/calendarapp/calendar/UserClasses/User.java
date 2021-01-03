package com.example.calendarapp.calendar.UserClasses;

import com.example.calendarapp.calendar.HandlerClasses.*;

import java.io.Serializable;

/**
 *
 * Represents an app user.
 * @author Wisam Mohiuddin and Vaibhav Holani
 */

public class User implements Serializable {

    /**
     * Name of user
     */
    private String name;
    /** Represents the username of User.
     */
    public String username;

    public CalendarHandler calendarHandler;

    public User(String username, String name) {
        this.username = username;
        this.name = name;
        this.calendarHandler = new CalendarHandler(this.username);
    }
}

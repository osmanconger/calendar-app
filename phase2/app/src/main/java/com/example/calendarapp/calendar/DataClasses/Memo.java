package com.example.calendarapp.calendar.DataClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing a Memo object.
 */
public class Memo implements Serializable {

    public String message;
    public String id;

    private ArrayList<Event> events;

    public Memo(int id, String message_client) {
        this.message = message_client;
        this.id = String.valueOf(id);
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEventToMemo(Event event) {
        events.add(event);
    }

}

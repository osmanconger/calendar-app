package com.example.calendarapp.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class Memo implements Serializable {

    public String message;
    public String id;

    // every memo can have a number of events attached to it
    // store events
    private ArrayList<Event> events;
    // @osman had to include events array_list in the constructor to implement the database

    public Memo(int id, String message_client) {
        this.message = message_client;
        this.id = String.valueOf(id);
    }

    /**
     * Returns a String object that represents message attribute.
     * */
    public String getMessage() {
        return message;
    }

    // returning all events this memo is associated to
    // get Events
    /**
     * Returns the events arraylist.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    // add events
    /**
     * Returns Event object to the Memo .
     */
    public void addEventToMemo(Event event) {
        events.add(event);
    }

}


// we have to add events to the memo
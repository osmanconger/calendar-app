package com.example.calendarapp.database;

import com.example.calendarapp.calendar.Event;

/**
 * Represents an Event converted for database storage.
 * @author Wisam Mohiuddin
 */
public class DatabaseEvent {

    public String startDate;
    public String endDate;
    public String startTime;
    public String endTime;
    public String name;
    public String id;
    public String hashTags;

    /**
     * Constructor for class DatabaseEvent.
     *
     * @param  event  the Event object associated with this DatabaseEvent object
     */
    public DatabaseEvent(Event event) {
        this.startDate = event.startDate.toString();
        this.endDate = event.endDate.toString();
        this.startTime = event.startTime.toString();
        this.endTime = event.endTime.toString();
        this.name = event.getEventName();
        this.id = event.id;
        this.hashTags = event.hashTags;
    }

}

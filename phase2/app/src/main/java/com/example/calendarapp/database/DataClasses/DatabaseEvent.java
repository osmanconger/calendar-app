package com.example.calendarapp.database.DataClasses;

import com.example.calendarapp.calendar.DataClasses.Event;

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
    public Long count;
    public String location;
    public String category;

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
        this.location = event.location;
        this.category = event.category;
    }

    /** Default constructor for DatabaseEvent.
     *
     */
    public DatabaseEvent() {}

}

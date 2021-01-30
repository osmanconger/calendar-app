package com.example.calendarapp.calendar.DataClasses;

import com.example.calendarapp.calendar.DataClasses.Memo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/** Class modelling Events
 * @author Mohammad Hassan Amer
 */
public class Event implements Serializable {

    public LocalDate startDate;
    public LocalDate endDate;
    public LocalTime startTime;
    public LocalTime endTime;
    public String eventName;
    public String id;
    public String hashTags;
    public String location;
    public String category;

    public Event(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                 String name, String hashTags, int id, String location, String category) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = name;
        this.hashTags = hashTags;
        this.id = String.valueOf(id);
        this.location = location;
        this.category = category;
    }

    public LocalDate getEventStartDate() { return this.startDate; }

    public String getEventName() {
        return this.eventName;
    }

    public String getEventHashTags() { return this.hashTags; }

}

package com.example.calendarapp.calendar;

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
    public Memo memo;
    public String id;
    public String hashTags;

    /** Constructor for class Event
     * @param startDate of type LocalDate representing the date at which the event starts
     * @param endDate of type LocalDate representing the date at which the event ends
     * @param startTime of type LocalTime representing the time at which the event starts
     * @param endTime of type LocalTime representing the time at which the event ends
     * @param name of type String representing the name of the event
     * @param hashTags of type Array of Strings representing the tags associated with the event
     * @param id of type int which is a unique int calculated by the database
     */
    public Event(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                 String name, String hashTags, int id) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = name;
        this.hashTags = hashTags;
        this.id = String.valueOf(id);
    }

    /** Gets the start date of the event
     * @return A LocalDate representing the startdate of the event
     */
    public LocalDate getEventStartDate() { return this.startDate; }

    /** Gets the event name of the event
     * @return A String representing the name of the event
     */
    public String getEventName() {
        return this.eventName;
    }

    /** Gets the hashtags associated with the event
     * @return Array of Strings representing the hashtags tagged with the event
     */
    public String getEventHashTags() { return this.hashTags; }
    // add Memo - when were adding a memo, we assume it is either a new memo or an old one -  either way we dont care
    // because that is this classes memo
    public void setMemo(Memo memo) {
        this.memo = memo;
    }
    // get Memo
    public Memo getMemo() { return this.memo; }
}

package com.example.calendarapp.calendar.DataClasses;

import java.time.LocalDate;
import java.time.LocalTime;

/** Class that creates new Event, Memo and Alert objects.
 *
 */
public class DataManager {


    public Alert createAlert(LocalDate date, LocalTime time, String id) {
        return new Alert(date, time, id);
    }

    public Event createEvent(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                             String name, String hashTags, int id, String location, String category) {
        return new Event(startDate, endDate, startTime, endTime, name, hashTags, id, location, category);
    }

    public Memo createMemo(int id, String message_client) {
        return new Memo(id, message_client);
    }

    public Category createCategory(String name) {
        return new Category(name);
    }
}

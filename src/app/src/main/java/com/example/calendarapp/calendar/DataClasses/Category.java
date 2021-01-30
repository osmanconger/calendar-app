package com.example.calendarapp.calendar.DataClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Modelling Categories for Events.
 * @author Vaibhav Holani
 */
public class Category implements Serializable {

    private String name;
    private ArrayList<Event> events = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public void add(Event cat_event){
        events.add(cat_event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }
}

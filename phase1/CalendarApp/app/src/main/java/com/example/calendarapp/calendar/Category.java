package com.example.calendarapp.calendar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Modelling Categories for Events.
 * @author Vaibhav Holani
 */
public class Category implements Serializable {

    private String name;
    private ArrayList<Event> events = new ArrayList<>();

    /**
     *
     * @param name describes the name of the category
     */
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

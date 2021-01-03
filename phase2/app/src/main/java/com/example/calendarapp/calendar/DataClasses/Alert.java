package com.example.calendarapp.calendar.DataClasses;

/**
 * Class Modelling Alerts for Events.
 * @author Vaibhav Holani
 */

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Alert implements Serializable {

    private LocalDate date;
    private LocalTime time;
    public String id;

    public Alert(LocalDate date, LocalTime time, String id) {
      this.date = date;
      this.time = time;
      this.id = id;
    }

    /** Compares the time of this alert to another alert
     *
     * @param other alert object whose time will be compared.
     * @return 0 if time is the same and a negative integer if time is different.
     */
    public int compareTime(Alert other){
        return this.getTime().compareTo(other.getTime());
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

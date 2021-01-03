package com.example.calendarapp.calendar;

/**
 * Class Modelling Alerts for Events.
 * @author Vaibhav Holani
 */

import android.annotation.TargetApi;
import android.os.Build;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Alert implements Serializable {

    private LocalDate date;
    private LocalTime time;
    public String id;

    /**
     * Constructor for class Alert.
     *
     * @param  date  describing the date of the alert
     * @param time describing the time of the alert
     * @param id describing the ID of the related to the alert
     */

    public Alert(LocalDate date, LocalTime time, int id) {
      this.date = date;
      this.time = time;
      this.id = String.valueOf(id);
    }
    /*
    //Making Alerts Comparable to Other Alerts
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int compareTo(Alert other){
        if (this.getDate().compareTo(other.getDate()) == 0){
            return this.compareTime(other);
        }
        else {
            return this.getDate().compareTo(other.getDate());
        }
    }*/
    //Helper for Comparing
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

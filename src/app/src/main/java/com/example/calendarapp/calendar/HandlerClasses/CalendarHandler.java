package com.example.calendarapp.calendar.HandlerClasses;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.calendarapp.calendar.DataClasses.Calendar;
import com.google.firebase.database.*;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarHandler implements Serializable {

    private String username;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String TAG = "CalendarHandler";
    private ArrayList<String> calendarNames = new ArrayList<>();
    private Calendar currentCalendar;
    private String currentCalendarName;

    public CalendarHandler(String username){
        this.username = username;
        final DatabaseReference alertRef = mDatabase.child("calendars").child(username);
        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    calendarNames.add((String) alertSnapshot.getValue());
                    System.out.println((String) alertSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadCalendars:onCancelled", databaseError.toException());
            }
        });
        if (calendarNames.size() == 0) {
        currentCalendarName = "Work";
        currentCalendar = new Calendar(currentCalendarName, username);}
    }

    public void createCalendar(String name) {
        mDatabase.child("calendars").child(username).child(name).setValue(name);
    }

    public Calendar getCurrentCalendar(){
        return currentCalendar;
    }

    public String getCurrentCalendarName(){
        return this.currentCalendarName;
    }

    public void setCurrentCalendar(String name){
        currentCalendar = new Calendar(currentCalendarName, username);
    }
    public void setCurrentCalendarName(String name){
        currentCalendarName = name;
    }

    public ArrayList<String> getCalendarNames(){
        this.UpdateCalendarNames();
        return this.calendarNames;
    }

    public void UpdateCalendarNames(){
        this.calendarNames = new ArrayList<>();
        final DatabaseReference alertRef = mDatabase.child("calendars").child(username);
        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    calendarNames.add((String) alertSnapshot.getValue());
                    System.out.println((String) alertSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadCalendars:onCancelled", databaseError.toException());
            }
        });
    }
}

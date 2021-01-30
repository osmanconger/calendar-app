package com.example.calendarapp.calendar.HandlerClasses;

import com.example.calendarapp.database.CallbackClasses.HashtableCallback;

public abstract class SuperHandler {
    private String username;

    SuperHandler(String username) {}

    public void getWithId(String id, HashtableCallback callBack) {}

    public void deleteWithId(String id) {}

}

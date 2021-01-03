package com.example.calendarapp.database;

import com.example.calendarapp.calendar.Memo;

/**
 * Represents a Memo converted for database storage.
 * @author Osman Conger
 */
public class DatabaseMemo {

    public String message;
    public String id;

    public DatabaseMemo(Memo memo) {
        this.message = memo.getMessage();
        id = memo.id;
    }

}

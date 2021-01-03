package com.example.calendarapp.database.DataClasses;

import com.example.calendarapp.calendar.DataClasses.Memo;

/**
 * Represents a Memo converted for database storage.
 * @author Osman Conger
 */
public class DatabaseMemo {

    public String message;
    public String id;

    /**Constructor for DatabaseMemo.
     *
     * @param memo The memo object associated with DatabaseMemo.
     */
    public DatabaseMemo(Memo memo) {
        this.message = memo.getMessage();
        id = memo.id;
    }

    public DatabaseMemo() {}

}

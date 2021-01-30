package com.example.calendarapp.database.CallbackClasses;

import com.example.calendarapp.database.DataClasses.DatabaseEvent;

/** Interface for Event callbacks.
 *
 */
public interface DatabaseEventCallback {
    void onCallback(DatabaseEvent dbEvent);
}

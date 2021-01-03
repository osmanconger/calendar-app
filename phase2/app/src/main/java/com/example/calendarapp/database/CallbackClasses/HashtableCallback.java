package com.example.calendarapp.database.CallbackClasses;

import java.util.Hashtable;

/**
 * Interface for database callbacks.
 * @author Wisam Mohiuddin
 */
public interface HashtableCallback<T, V> {
    void onCallback(Hashtable<T, V> hashtable);
}

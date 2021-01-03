package com.example.calendarapp.database;

import java.util.List;

/**
 * Interface for database callbacks.
 * @author Wisam Mohiuddin
 */
public interface FirebaseCallBack<T> {
    void onCallback(List<T> list);
}


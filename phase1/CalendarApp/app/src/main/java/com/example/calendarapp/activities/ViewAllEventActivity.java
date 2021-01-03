package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.calendarapp.R;

import static com.example.calendarapp.activities.LogInPage.user;

public class ViewAllEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_event);
        user.eventHandler.printAllEvents();
    }
}
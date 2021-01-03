package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.calendarapp.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void createSingleEventButton(View view) {
        startActivity(new Intent(CreateEventActivity.this, CreateSingleEventActivity.class));
    }

    public void createEventByFreq(View view) {
        startActivity(new Intent(CreateEventActivity.this, CreateFreqEvent.class));
    }

    public void createRecurringEvent(View view) {
        startActivity(new Intent(CreateEventActivity.this, CreateRecurringEvent.class));
    }
}

package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.calendarapp.R;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
    }

    public void viewAllEventButton(View view) {
        startActivity(new Intent(ViewEventActivity.this, ViewAllEventActivity.class));
    }

    public void viewEventsBetweenDates(View view) {
        startActivity(new Intent(ViewEventActivity.this, ViewEventsBetweenDatesActivity.class));
    }

}

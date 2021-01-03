package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.calendarapp.R;

import static com.example.calendarapp.activities.LogInPage.user;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public void createEventButton (View view) {
        // initiates the create event activity class.
        startActivity(new Intent(Menu.this, CreateEventActivity.class));
    }


    public void viewEventButton (View view) {
        startActivity(new Intent(Menu.this, ViewEventActivity.class));
    }

    public void createAlertButton (View view) {
        startActivity(new Intent(Menu.this, CreateAlertActivity.class));
    }

    public void viewAlertButton(View view) {
        startActivity(new Intent(Menu.this, ViewAlertsActivity.class));
    }

    public void createMemoButton(View view) {
        startActivity(new Intent(Menu.this, CreateMemoActivity.class));
    }

    public void viewMemoButton(View view) {
        user.memoHandler.printAllMemos();
        startActivity(new Intent(Menu.this, ViewMemoActivity.class));
    }

}

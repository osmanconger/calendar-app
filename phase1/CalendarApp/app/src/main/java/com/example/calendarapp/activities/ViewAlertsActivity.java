package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.calendarapp.R;

public class ViewAlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);
    }

    public void viewAllAlertsButton(View view) {
        startActivity(new Intent(ViewAlertsActivity.this, ViewAllAlertsActivity.class));
    }

    public void viewAlertsBetweenDatesButton(View view) {
        startActivity(new Intent(ViewAlertsActivity.this, ViewAlertsBetweenDatesActivity.class));
    }
}

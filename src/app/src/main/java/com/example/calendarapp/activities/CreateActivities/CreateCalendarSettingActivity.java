package com.example.calendarapp.activities.CreateActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.calendarapp.R;
import com.example.calendarapp.activities.MenuActivities.Menu;
import com.example.calendarapp.activities.ViewActivities.ViewMemoActivity;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class CreateCalendarSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_settings);
    }
    public void AddCalendarButton(View view) {
        startActivity(new Intent(CreateCalendarSettingActivity.this, CreateCalenderActivity.class));
    }

    public void ChangeCalendarButton(View view) {
        startActivity(new Intent(CreateCalendarSettingActivity.this, ChangeCalendarActivity.class));
    }
}

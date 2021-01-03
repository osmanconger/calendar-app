package com.example.calendarapp.activities.CreateActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.calendarapp.R;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class CreateCalenderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_calendar);
    }
    public void createCalendarButton(View view) {
        EditText text1 = (EditText) findViewById(R.id.editText36);
        String name = text1.getText().toString();
        if (!name.equals("")){
        user.calendarHandler.createCalendar(name);}
        finish();
    }
}

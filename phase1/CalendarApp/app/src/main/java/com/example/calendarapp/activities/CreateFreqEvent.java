package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.calendarapp.R;
import android.view.View;
import android.widget.EditText;

import static com.example.calendarapp.activities.LogInPage.user;

public class CreateFreqEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_freq_event);
    }

    public void singleActivity(View view){
        EditText name = (EditText) findViewById(R.id.editText11);
        EditText category = (EditText) findViewById(R.id.editText12);
        EditText tag = (EditText) findViewById(R.id.editText13);
        EditText startDate = (EditText) findViewById(R.id.editText15);
        EditText endDate = (EditText) findViewById(R.id.editText16);
        EditText startTime = (EditText) findViewById(R.id.editText17);
        EditText endTime = (EditText) findViewById(R.id.editText18);
        EditText number_events = (EditText) findViewById(R.id.editText19);
        EditText days_week = (EditText) findViewById(R.id.editText20);
        EditText message = (EditText) findViewById(R.id.editText21);
        String eventName = name.getText().toString();
        String eventCategory = category.getText().toString();
        String eventTag = tag.getText().toString();
        String eventStartDate = startDate.getText().toString();
        String eventEndDate = endDate.getText().toString();
        String eventStartTime = startTime.getText().toString();
        String eventEndTime = endTime.getText().toString();
        String eventNumberEvents = number_events.getText().toString();
        String eventDaysWeek = days_week.getText().toString();
        String eventMessage = message.getText().toString();
        String[] tag_list = {eventTag};
        user.eventHandler.addSingleEvent(eventName, eventCategory, eventStartDate, eventEndDate, eventStartTime, eventEndTime, eventMessage, eventTag, user.memoHandler);

    }
}

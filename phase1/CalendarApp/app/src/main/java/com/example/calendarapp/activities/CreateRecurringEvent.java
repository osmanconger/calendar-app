package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.calendarapp.R;
import android.view.View;
import android.widget.EditText;

import static com.example.calendarapp.activities.LogInPage.user;

public class CreateRecurringEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recurring_event);
    }

    public void singleActivity(View view){
        EditText name = (EditText) findViewById(R.id.editText14);
        EditText category = (EditText) findViewById(R.id.editText22);
        EditText tag = (EditText) findViewById(R.id.editText23);
        EditText startDate = (EditText) findViewById(R.id.editText25);
        EditText endDate = (EditText) findViewById(R.id.editText26);
        EditText startTime = (EditText) findViewById(R.id.editText27);
        EditText endTime = (EditText) findViewById(R.id.editText28);
        EditText message = (EditText) findViewById(R.id.editText29);
        String eventName = name.getText().toString();
        String eventCategory = category.getText().toString();
        String eventTag = tag.getText().toString();
        String eventStartDate = startDate.getText().toString();
        String eventEndDate = endDate.getText().toString();
        String eventStartTime = startTime.getText().toString();
        String eventEndTime = endTime.getText().toString();
        String eventMessage = message.getText().toString();
        user.eventHandler.addSingleEvent(eventName, eventCategory, eventStartDate, eventEndDate, eventStartTime, eventEndTime, eventMessage, eventTag, user.memoHandler);

    }
}

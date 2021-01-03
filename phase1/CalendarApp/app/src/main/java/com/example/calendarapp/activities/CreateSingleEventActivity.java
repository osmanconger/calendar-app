package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.calendarapp.calendar.*;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.calendarapp.R;

import static com.example.calendarapp.activities.LogInPage.user;

public class CreateSingleEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_single_event);
    }
    public void singleActivity(View view){
        EditText name = (EditText) findViewById(R.id.editText3);
        EditText category = (EditText) findViewById(R.id.editText4);
        EditText tag = (EditText) findViewById(R.id.editText5);
        EditText startDate = (EditText) findViewById(R.id.editText6);
        EditText endDate = (EditText) findViewById(R.id.editText7);
        EditText startTime = (EditText) findViewById(R.id.editText8);
        EditText endTime = (EditText) findViewById(R.id.editText9);
        EditText message = (EditText) findViewById(R.id.editText10);
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

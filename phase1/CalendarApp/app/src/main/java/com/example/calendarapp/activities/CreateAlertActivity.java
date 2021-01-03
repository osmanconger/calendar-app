package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.calendarapp.R;
import com.example.calendarapp.calendar.Alert;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import static com.example.calendarapp.activities.LogInPage.user;

public class CreateAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);
    }
     public void createAlertButton(View view) {
         EditText text1 = (EditText) findViewById(R.id.editText3);
         EditText text2 = (EditText) findViewById(R.id.editText4);
         String date = text1.getText().toString();
         String time = text2.getText().toString();
         user.alertHandler.add(date, time);
     }
}

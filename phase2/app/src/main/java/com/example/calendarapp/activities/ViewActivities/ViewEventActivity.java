package com.example.calendarapp.activities.ViewActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.example.calendarapp.R;
import com.example.calendarapp.activities.MenuActivities.Menu;
import com.example.calendarapp.database.CallbackClasses.DatabaseEventCallback;
import com.example.calendarapp.database.DataClasses.DatabaseEvent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class ViewEventActivity extends AppCompatActivity {

    private static String eventId;
    private static int startHourOfDay;
    private static int startMinute;
    private static int endHourOfDay;
    private static int endMinute;
    private static int startYear;
    private static int startMonth;
    private static int startDay;
    private static int endYear;
    private static int endMonth;
    private static int endDay;
    private static boolean isStartTimeSet = false;
    private static boolean isStartDateSet = false;
    private static boolean isEndTimeSet = false;
    private static boolean isEndDateSet = false;
    private static DatabaseEvent staticDbEvent;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        shareDialog = new ShareDialog(this);

        Intent intent = this.getIntent();
        eventId = intent.getStringExtra("eventId");
        user.calendarHandler.getCurrentCalendar().eventHandler.getWithId(eventId, new DatabaseEventCallback() {
            @Override
            public void onCallback(DatabaseEvent dbEvent) {
                staticDbEvent = dbEvent;
                EditText eventName = (EditText) findViewById(R.id.editText33);
                EditText categoryName = (EditText) findViewById(R.id.editText5);
                EditText hashTags = (EditText) findViewById(R.id.editText35);
                EditText location = (EditText) findViewById(R.id.editText32);
                eventName.setText(dbEvent.name);
                hashTags.setText(dbEvent.hashTags);
                categoryName.setText(dbEvent.category);
                location.setText(dbEvent.location);
            }
        });
    }

    public void deleteEvent(View v) {
        user.calendarHandler.getCurrentCalendar().eventHandler.deleteWithId(eventId);
        this.finish();
    }

    public void saveEvent(View v) {
        if(isStartTimeSet) {
            LocalTime startTime = LocalTime.of(startHourOfDay, startMinute);
            staticDbEvent.startTime = startTime.toString();
        }
        if (isEndTimeSet) {
            LocalTime endTime = LocalTime.of(endHourOfDay, endMinute);
            staticDbEvent.endTime = endTime.toString();
        }
        if(isStartDateSet) {
            LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
            System.out.println("set start date:");
            System.out.println(startDate);
            staticDbEvent.startDate = startDate.toString();
            System.out.println("new start date:");
            System.out.println(staticDbEvent.startDate);
        }
        if(isEndDateSet) {
            LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
            staticDbEvent.endDate = endDate.toString();
        }
        EditText eventName = (EditText) findViewById(R.id.editText33);
        EditText categoryName = (EditText) findViewById(R.id.editText5);
        EditText hashTags = (EditText) findViewById(R.id.editText35);
        EditText location = (EditText) findViewById(R.id.editText32);
        staticDbEvent.name = eventName.getText().toString();
        staticDbEvent.hashTags = hashTags.getText().toString();
        staticDbEvent.category = categoryName.getText().toString();
        staticDbEvent.location = location.getText().toString();

        user.calendarHandler.getCurrentCalendar().eventHandler.updateEventWithId(staticDbEvent.id, staticDbEvent);
        this.finish();
        startActivity(new Intent(ViewEventActivity.this, Menu.class));
    }

    public void addUserToEvent(View view) {
        EditText et = findViewById(R.id.editText3);
        String username = et.getText().toString();
        if(!username.equals("")) {
            user.calendarHandler.getCurrentCalendar().eventHandler.addEventToUser(eventId, username);
        }
    }

    public void shareToFacebook(View view) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/"))
                    .setQuote("Please have a look at this Event created from the awesome CSC207 app!\n\n" +
                            "Name: " + staticDbEvent.name + "\n" +
                            "HashTags: " + staticDbEvent.hashTags + "\n" +
                            "From: " + staticDbEvent.startDate + " at " + staticDbEvent.startTime + "\n" +
                            "Till: " + staticDbEvent.endDate + " at: " + staticDbEvent.endTime + "\n\n" +
                            "You're all invited!")
                    .build();
            shareDialog.show(linkContent);
        }
    }

    public void shareToMail(View view) {
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.setType("message/rfc822");
        mail.putExtra(Intent.EXTRA_SUBJECT, "CSC207 App Event");
        mail.putExtra(Intent.EXTRA_TEXT, "Please have a look at this Event created from the awesome CSC207 app!\n\n" +
                "Name: " + staticDbEvent.name + "\n" +
                "HashTags: " + staticDbEvent.hashTags + "\n" +
                "From: " + staticDbEvent.startDate + " at " + staticDbEvent.startTime + "\n" +
                "Till: " + staticDbEvent.endDate + " at: " + staticDbEvent.endTime + "\n\n" +
                "You're invited!");
        startActivity(mail);
    }

    public void viewAlertsButton (View view) {
        Intent intent = new Intent(ViewEventActivity.this, ViewAlertsActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    public void viewMemosButton (View view) {
        Intent intent = new Intent(ViewEventActivity.this, ViewMemoActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "startTimePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "endTimePicker");
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "startDatePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "endDatePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            if (this.getTag().equals("startTimePicker")) {
                startHourOfDay = hourOfDay;
                startMinute = minute;
                isStartTimeSet = true;
            } else {
                endHourOfDay = hourOfDay;
                endMinute = minute;
                isEndTimeSet = true;
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if (this.getTag().equals("startDatePicker")) {
                startYear = year;
                startMonth = month+1;
                startDay = day;
                isStartDateSet = true;
            } else {
                endYear = year;
                endMonth = month+1;
                endDay = day;
                isEndDateSet = true;
            }
        }
    }

}

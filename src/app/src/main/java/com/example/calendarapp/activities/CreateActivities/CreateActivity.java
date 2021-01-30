package com.example.calendarapp.activities.CreateActivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.example.calendarapp.R;
import com.example.calendarapp.activities.MenuActivities.Menu;
import com.example.calendarapp.activities.ViewActivities.ViewEventActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class CreateActivity extends AppCompatActivity {

    private static String name;
    private static String location;
    private static String category;
    private static String hashTags;
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
    private static int days;
    private static int times;
    private static String memo;
    private static boolean isStartTimeSet = false;
    private static boolean isStartDateSet = false;
    private static boolean isEndTimeSet = false;
    private static boolean isEndDateSet = false;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Switch infinitelyRepeatingSwitch = findViewById(R.id.switch1);
        TableRow frequencyTableRow = findViewById(R.id.frequencyTableRow);

        infinitelyRepeatingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                frequencyTableRow.setVisibility(View.GONE);
            } else {
                frequencyTableRow.setVisibility(View.VISIBLE);
            }
        });

        String apiKey = "AIzaSyAPtG8GaSIof59yO1l9KMrAofkqhGM8PFU";
        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);

        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                location = place.getAddress();
            }
            @Override
            public void onError(@NonNull Status status) {

            }
        });
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

    public void createEvent(View v) {

        EditText textName = findViewById(R.id.editText321);
        EditText textCategory = findViewById(R.id.editText323);
        EditText textDays = findViewById(R.id.editText301);
        EditText textTimes = findViewById(R.id.editText30);
        EditText textHashTags = findViewById(R.id.editText3221);
        Switch infinitelyRepeatingSwitch = findViewById(R.id.switch1);

        name = textName.getText().toString();
        hashTags = textHashTags.getText().toString();
        category = textCategory.getText().toString();
        if(!"".equals(textDays.getText().toString()))
            days = Integer.parseInt(textDays.getText().toString());
        if(!"".equals(textTimes.getText().toString()))
            times = Integer.parseInt(textTimes.getText().toString());

        if(!name.equals("") && isStartTimeSet && isStartDateSet && isEndTimeSet && isEndDateSet) {
            LocalTime startTime = LocalTime.of(startHourOfDay, startMinute);
            LocalTime endTime = LocalTime.of(endHourOfDay, endMinute);
            LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
            LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

            if (days == 0) {
                user.calendarHandler.getCurrentCalendar().eventHandler.addSingleEvent(name, category, startDate,endDate,
                        startTime, endTime, "", hashTags, null, location);
            } else {
                if (infinitelyRepeatingSwitch.isChecked()) {
                    user.calendarHandler.getCurrentCalendar().eventHandler.addEventsByFrequency(name, category,
                            startDate, endDate, startTime, endTime, 50, days, memo, "",
                            user.calendarHandler.getCurrentCalendar().memoHandler, location);
                } else {
                    user.calendarHandler.getCurrentCalendar().eventHandler.addEventsByFrequency(name, category,
                            startDate, endDate, startTime, endTime, times, days, memo, "",
                            user.calendarHandler.getCurrentCalendar().memoHandler, location);
                }
            }
            this.finish();
            startActivity(new Intent(CreateActivity.this, Menu.class));
        } else {
            IncompleteEventDialogFragment dialog = new IncompleteEventDialogFragment();
            dialog.show(getSupportFragmentManager(), "incompleteEventDialog");
        }
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

    public static class IncompleteEventDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Error")
                    .setMessage("Please fill in all the required details")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
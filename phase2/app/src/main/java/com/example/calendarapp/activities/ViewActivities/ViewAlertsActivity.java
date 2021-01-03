package com.example.calendarapp.activities.ViewActivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.example.calendarapp.R;
import com.example.calendarapp.activities.CreateActivities.CreateActivity;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseAlert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Hashtable;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class ViewAlertsActivity extends AppCompatActivity {

    private static int hour;
    private static int min;
    private static boolean isTimeSet = false;
    private static int startYear;
    private static int startMonth;
    private static int startDay;
    private static boolean isDateSet = false;
    private static String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);
        Intent intent = this.getIntent();
        eventId = intent.getStringExtra("eventId");
        init();
    }

    private void init() {
        TableLayout tl = findViewById(R.id.table);
        Typeface type = Typeface.SANS_SERIF;
        user.calendarHandler.getCurrentCalendar().alertHandler.getWithId(eventId, new HashtableCallback() {
            int i = 0;
            @Override
            public void onCallback(Hashtable hashtable) {
                hashtable.forEach((k, v) -> {
                    DatabaseAlert dbAlert = (DatabaseAlert) v;
                    TableRow row = new TableRow(ViewAlertsActivity.this);
                    TableRow row2 = new TableRow(ViewAlertsActivity.this);
                    TableRow row3 = new TableRow(ViewAlertsActivity.this);
                    TextView tv = new TextView(ViewAlertsActivity.this);
                    tv.setText("\nDate: " + dbAlert.date + "; Time: " + dbAlert.time + "\n");
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setMaxLines(5);
                    tv.setMaxWidth(tl.getWidth() - 10);
                    tv.setTypeface(type);
                    tv.setTextSize(18);
                    Button delete = new Button(ViewAlertsActivity.this, null, R.style.Widget_AppCompat_Light_ActionButton);
                    delete.setText("DELETE");
                    delete.setTextColor(Color.RED);
                    delete.setGravity(Gravity.CENTER_HORIZONTAL);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            user.calendarHandler.getCurrentCalendar().alertHandler.deleteWithId((String) k);
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    row.addView(tv);
                    row2.addView(delete);
                    TextView tv2 = new TextView(ViewAlertsActivity.this);
                    tv2.setText("________________________________________________________");
                    row3.addView(tv2);
                    tl.addView(row, i);
                    i = i + 1;
                    tl.addView(row2, i);
                    i = i + 1;
                    tl.addView(row3, i);
                    i = i + 1;
                });
            }
        });
    }

    public void addAlert(View v) {
        if (isTimeSet && isDateSet) {
            LocalTime time = LocalTime.of(hour, min);
            String timeString = time.toString();
            LocalDate date = LocalDate.of(startYear, startMonth, startDay);
            String dateString = date.toString();
            user.calendarHandler.getCurrentCalendar().alertHandler.add(dateString, timeString, eventId);
            finish();
            startActivity(getIntent());
        } else {
            CreateActivity.IncompleteEventDialogFragment dialog = new CreateActivity.IncompleteEventDialogFragment();
            dialog.show(getSupportFragmentManager(), "incompleteEventDialog");
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new ViewAlertsActivity.TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new ViewAlertsActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
            hour = hourOfDay;
            min = minute;
            isTimeSet = true;
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
            startYear = year;
            startMonth = month+1;
            startDay = day;
            isDateSet = true;
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

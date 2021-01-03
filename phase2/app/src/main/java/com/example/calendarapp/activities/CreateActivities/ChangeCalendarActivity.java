package com.example.calendarapp.activities.CreateActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.calendarapp.R;
import com.example.calendarapp.activities.MenuActivities.Menu;
import com.example.calendarapp.activities.ViewActivities.ViewMemoActivity;

import java.util.ArrayList;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class ChangeCalendarActivity extends AppCompatActivity {

    private ArrayList<String> names = user.calendarHandler.getCalendarNames();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_change);
        SetSpinner();
    }

    public void ChangeButtonPressed(View view){
        Spinner calendar_lst = (Spinner) findViewById(R.id.spinner1);
        String  selectedName = calendar_lst.getSelectedItem().toString();
        user.calendarHandler.setCurrentCalendarName(selectedName);
        user.calendarHandler.setCurrentCalendar(selectedName);
        System.out.println(user.calendarHandler.getCurrentCalendarName());
        finish();
    }

    private void SetSpinner(){
            Spinner calendar_lst = (Spinner) findViewById(R.id.spinner1);
            ArrayAdapter<String> calender_data;
            calender_data = new ArrayAdapter<String>(ChangeCalendarActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
            calender_data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            calendar_lst.setAdapter(calender_data);
        int spinnerPosition = names.indexOf("work");
        calendar_lst.setSelection(spinnerPosition);


//            calendar_lst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                    // your code here
//                    String selectedName = names.get(position);
//
////                    user.calendarHandler.setCurrentCalendarName(selectedName);
////                    user.calendarHandler.setCurrentCalendar(selectedName);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parentView) {
//                    user.calendarHandler.setCurrentCalendarName("work");
//                    user.calendarHandler.setCurrentCalendar("work");;
//                }



//            });
        }
    }


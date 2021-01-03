package com.example.calendarapp.activities.MenuActivities;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calendarapp.R;
import com.example.calendarapp.activities.CreateActivities.CreateActivity;
import com.example.calendarapp.activities.CreateActivities.CreateCalendarSettingActivity;
import com.example.calendarapp.activities.ViewActivities.ViewAlertsActivity;
import com.example.calendarapp.activities.ViewActivities.ViewEventActivity;
import com.example.calendarapp.activities.ViewActivities.ViewMemoActivity;
import com.example.calendarapp.calendar.DataClasses.Calendar;
import com.example.calendarapp.calendar.DataClasses.Event;
import com.example.calendarapp.calendar.UserClasses.User;
import com.example.calendarapp.database.CallbackClasses.FirebaseCallBack;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseAlert;
import com.example.calendarapp.database.DataClasses.DatabaseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Menu extends AppCompatActivity {
    List todayEvent = new ArrayList();
    List todayAlert = new ArrayList();
    LayoutInflater layoutInflater;
    LayoutInflater layoutInflater2;
    User user = com.example.calendarapp.activities.LoggingActivities.LogInPage.user;
    LinearLayout horViewCards;
    LinearLayout horViewCards2;
    String events[];
    String eventIds[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        layoutInflater = LayoutInflater.from(this);
        layoutInflater2 = LayoutInflater.from(this);
        horViewCards = this.findViewById(R.id.HorizontalCardEvents);
        horViewCards2 = this.findViewById(R.id.HorizontalCardAlerts);
        if(user != null){
            user.calendarHandler.getCurrentCalendar().eventHandler.getEventsForDate(LocalDate.now(), new FirebaseCallBack(){
                @Override
                public void onCallback(List list) {
                    setTodayEvent(list);
                }
            });
        }

        CalendarView mainCal = findViewById(R.id.mainCalendar);
        mainCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                horViewCards.removeAllViews();
                user.calendarHandler.getCurrentCalendar().eventHandler.getEventsForDate(LocalDate.of(year,month+1,dayOfMonth), new FirebaseCallBack(){
                    @Override
                    public void onCallback(List list) {
                        setTodayEvent(list);
                    }
                });
                TextView newEventText = findViewById(R.id.eventText);
                if (LocalDate.of(year,month+1,dayOfMonth).compareTo(LocalDate.now())==0)
                {
                    newEventText.setText("Today's event");
                }
                else
                {
                    newEventText.setText(LocalDate.of(year,month+1,dayOfMonth).toString());
                }
            }
        });



        CardView alertCard = findViewById(R.id.AlertCard);
        CardView memoCard = findViewById(R.id.MemoCard);
        alertCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ViewAlertsActivity.class));
            }
        });
        memoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ViewMemoActivity.class));
            }
        });

/*        user.calendarHandler.getCurrentCalendar().eventHandler.getEventsForSearch(new HashtableCallback() {
            @Override
            public void onCallback(Hashtable hashtable) {
                int i = 0;
                for(Object key : hashtable.keySet()) {
                    if(hashtable.get(key).toString() != null) {
                        events[i] = hashtable.get(key).toString();
                    }
                    if (key.toString() != null) {
                        eventIds[i] = key.toString();
                    }
                    i++;
                }
            }
        });
        AutoCompleteTextView eventSearch = findViewById(R.id.eventSearch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, events);
        eventSearch.setAdapter(adapter); */

    }
    public void CalendarButton(View view) {
        startActivity(new Intent(Menu.this, CreateCalendarSettingActivity.class));
    }
    public void createEventButton(View extra){
        startActivity(new Intent(Menu.this, CreateActivity.class));
    }
    public void setTodayEvent(List event){
        todayEvent = event;
        addTheView();
    }
    public void setTodayAlert(List alert){
        todayAlert.add(alert);
        addTheView2();
    }
    void addTheView(){
        for(int i=0; i<todayEvent.size();i++){
            View menuCards = layoutInflater.inflate(R.layout.activity_menu_cards, null, false);
            TextView TextHorCard = menuCards.findViewById(R.id.TextHorCard);
            TextHorCard.setText(((DatabaseEvent)(todayEvent.get(i))).name);
            int finalI = i;
            menuCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu.this, ViewEventActivity.class);
                    intent.putExtra("eventId", ((DatabaseEvent)(todayEvent.get(finalI))).id);
                    startActivity(intent);
                }
            });
            horViewCards.addView(menuCards);
        }
        if(user != null){
            user.calendarHandler.getCurrentCalendar().alertHandler.getAlertsForDate(LocalDate.now(), new FirebaseCallBack(){
                @Override
                public void onCallback(List list) {
                    setTodayAlert(list);
                }
            });
        }
    }
    void addTheView2(){
        for(int i=0; i<todayAlert.size();i++){
            for(int e=0; e<((ArrayList)(todayAlert.get(i))).size();e++) {
                View menuCards = layoutInflater2.inflate(R.layout.activity_menu_cards, null, false);
                TextView TextHorCard = menuCards.findViewById(R.id.TextHorCard);
                TextHorCard.setText(((DatabaseAlert) (((ArrayList) todayAlert.get(i)).get(e))).time);
                int finalI = i;
                menuCards.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Menu.this, ViewAlertsActivity.class);
                        intent.putExtra("eventId", ((DatabaseEvent)(todayEvent.get(finalI))).id);
                        startActivity(intent);
                    }
                });
                horViewCards2.addView(menuCards);
            }
        }
    }
}


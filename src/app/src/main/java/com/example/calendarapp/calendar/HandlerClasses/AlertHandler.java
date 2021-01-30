package com.example.calendarapp.calendar.HandlerClasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.calendarapp.calendar.DataClasses.Alert;
import com.example.calendarapp.calendar.DataClasses.DataManager;
import com.example.calendarapp.database.CallbackClasses.BooleanCallback;
import com.example.calendarapp.database.CallbackClasses.FirebaseCallBack;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseAlert;
import com.example.calendarapp.database.DataClasses.DatabaseEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Class Responsible for creating alerts and storing them through database interaction.
 * @author Vaibhav Holani, Wisam Mohiuddin
 */
public class AlertHandler extends SuperHandler implements Serializable {
    private String username;
    private int count;
    private DataManager dataManager = new DataManager();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String TAG = "AlertHandler";
    private String calendarName;
    //private ControllerAlertHandler controllerAlertHandler;

    public AlertHandler(String username, String calendarName) {
        super(username);
        this.username = username;
        this.calendarName = calendarName;
        updateCount(new com.example.calendarapp.database.CallbackClasses.BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool)
                    Log.d(TAG, "AlertHandler count updated");
            }
        });
    }

    void updateCountOnDB() {
        mDatabase.child("alerts").child(username).child(calendarName).child("count").setValue(count);
    }

    void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("alerts").child(username).child(calendarName).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("alerts").child(username).child(calendarName).child("count").setValue(count);
                    Log.d(TAG, "added count to db");
                } else {
                    count = ((Long) dataSnapshot.getValue()).intValue();
                    Log.d(TAG, "updated count to " + count);
                }
                callback.onCallback(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        countRef.addListenerForSingleValueEvent(eventListener);
    }

    private void addAlertToDatabase(Alert alert) {
        DatabaseAlert dbAlert = new DatabaseAlert(alert);
        mDatabase.child("alerts").child(username).child(calendarName).child(String.valueOf(count)).setValue(dbAlert);
    }

    public void getWithId(String id, HashtableCallback callBack) {
        Hashtable<String, DatabaseAlert> dbAlerts = new Hashtable<>();

        final DatabaseReference alertsRef = mDatabase.child("alerts").child(username).child(calendarName);
        alertsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    if(alertSnapshot.child("id").getValue() != null) {
                        if(alertSnapshot.child("id").getValue().equals(id)) {
                            dbAlerts.put(alertSnapshot.getKey(), alertSnapshot.getValue(DatabaseAlert.class));
                        }
                    }
                }
                callBack.onCallback(dbAlerts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadAlerts:onCancelled", databaseError.toException());
            }
        });
    }

    public void getAlertsForDate(LocalDate date, FirebaseCallBack callBack) {
        ArrayList<DatabaseAlert> dbAlerts = new ArrayList<>();
        String dateString = date.toString();

        final DatabaseReference eventsRef = mDatabase.child("alerts").child(username).child(calendarName);
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    if(alertSnapshot.child("date").getValue() != null) {
                        if(alertSnapshot.child("date").getValue().equals(dateString)) {
                            dbAlerts.add(alertSnapshot.getValue(DatabaseAlert.class));
                        }
                    }
                }
                callBack.onCallback(dbAlerts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadAlert:onCancelled", databaseError.toException());
            }
        });
    }


    public void deleteWithId(String id) {
        final DatabaseReference event = mDatabase.child("alerts").child(username).child(calendarName).child(id);
        event.removeValue();
    }

    /**
     * Adds Alert to database.
     *
     * @param  dateString the string representation of date
     * @param  timeString the string representation of time
     * @param  eventId Id of the event associated with this alert
     */
    public void add(String dateString, String timeString, String eventId) {
        try{
            LocalDate date = LocalDate.parse(dateString);
            LocalTime time = LocalTime.parse(timeString);
            Alert alert = dataManager.createAlert(date, time, eventId);
            addAlertToDatabase(alert);
            this.count += 1;
            updateCountOnDB();
        } catch (Exception e) {
            Log.d(TAG, "wrong format");
        }
    }

}

package com.example.calendarapp.calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.calendarapp.database.BooleanCallback;
import com.example.calendarapp.database.DatabaseAlert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Class Responsible for creating alerts and storing them through database interaction.
 * @author Vaibhav Holani, Wisam Mohiuddin
 */
public class AlertHandler implements Serializable {
    private String username;
    private int count;

    //private ArrayList<Alert> alerts = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String TAG = "AlertHandler";

    /**
     *
     * @param username The username of the user Alerts are stored for!
     */
    public AlertHandler(String username) {
        this.username = username;
        this.updateCount(new BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool)
                    Log.d(TAG, "AlertHandler count updated");
            }
        });
    }

    /**
     * Prints all Alerts from database to System.out
     *
     */
    public void printAllAlerts() {
        final DatabaseReference alertRef = mDatabase.child("alerts").child(username);
        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Alerts:");
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    System.out.println(alertSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadAlert:onCancelled", databaseError.toException());
            }
        });
    }

    private void updateCountOnDB() {
        mDatabase.child("alerts").child(username).child("count").setValue(count);
    }

    private void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("alerts").child(username).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("alerts").child(username).child("count").setValue(count);
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
        mDatabase.child("alerts").child(username).child(dbAlert.id).setValue(dbAlert);
    }

    /**
     * Adds Alert.
     *
     * @param  dateString the string representation of date
     * @param  timeString the string representation of time
     */
    public void add(String dateString, String timeString) {
        try{
            LocalDate date = LocalDate.parse(dateString);
            LocalTime time = LocalTime.parse(timeString);
            Alert alert = new Alert(date, time, count);
            //alerts.add(alert);
            addAlertToDatabase(alert);
            this.count += 1;
            updateCountOnDB();
        } catch (Exception e) {
            Log.d(TAG, "wrong format");
        }

        /*if (alerts.size() == 0) {
            alerts.add(alert);
        } else {
            int count = 0;
            boolean placed = false;

            // Finding the correct position in the list to store the alert
            while (!placed && count < alerts.size()) {
                Alert curr = alerts.get(count);
                if (alert.compareTo(curr) >= 0){
                alerts.add(count, alert);
                placed = true;
                }
                else {
                    count++;
                }
            }
            // If this alert is scheduled for the most further date
            if (!placed) {
                alerts.add(alert);
            }
        }*/
    }

}

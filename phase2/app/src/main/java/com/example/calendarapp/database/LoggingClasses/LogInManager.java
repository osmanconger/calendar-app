package com.example.calendarapp.database.LoggingClasses;

import android.util.Log;

import com.example.calendarapp.calendar.UserClasses.User;
import com.example.calendarapp.database.CallbackClasses.UserCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Manager to Log-In User checking credentials from the database.
 * @author Wisam Mohiuddin
 */
public class LogInManager {
    private DatabaseReference mDatabase;
    private String TAG = "LogIn";
    public User user;

    /**
     * Returns User object if username and password exist and are correct.
     *
     * @param  username  the username String
     * @param  password  the password String
     * @param  callback  a UserCallback object
     */
    public void tryLogIn(String username, final String password, UserCallback callback) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userNameRef = mDatabase.child("users").child(username);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("password").exists()) {
                    if (dataSnapshot.child("password").getValue(String.class).equals(password)) {
                        Log.d(TAG, "Logged in successfully");
                        user = new User(username, (String) dataSnapshot.child("name").getValue());
                        callback.onCallback(user);
                    } else {
                        Log.d(TAG, "Incorrect password");
                    }
                } else {
                    Log.d(TAG, "Incorrect username");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

}

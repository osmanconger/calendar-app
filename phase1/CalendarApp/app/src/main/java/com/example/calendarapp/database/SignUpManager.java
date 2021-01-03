package com.example.calendarapp.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Manager to sign-up User adding credentials to the database.
 * @author Wisam Mohiuddin
 */
public class SignUpManager {

    private DatabaseReference mDatabase;
    private String TAG = "SignUp";

    /**
     * Sign's up User with username, name and password if username does not exist.
     *
     * @param  username  the username String
     * @param  name      the name String
     * @param  password  the password String
     * @param  callback  a BooleanCallback object
     */
    public void signUpUser(final String username, final String name, final String password, BooleanCallback callback) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userNameRef = mDatabase.child("users").child(username);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If username does not exist
                if (!dataSnapshot.exists()) {
                    addUserToDB(username, name, password);
                    Log.d(TAG, "successfully signed up");
                    callback.onCallback(true);
                } else {
                    Log.d(TAG, "username already exists");
                    callback.onCallback(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

    private void addUserToDB(String username, String name, String password) {
        mDatabase.child("users").child(username).child("username").setValue(username);
        mDatabase.child("users").child(username).child("name").setValue(name);
        mDatabase.child("users").child(username).child("password").setValue(password);
    }
}

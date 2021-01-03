package com.example.calendarapp.calendar.HandlerClasses;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.calendarapp.calendar.DataClasses.DataManager;
import com.example.calendarapp.calendar.DataClasses.Memo;
import com.example.calendarapp.database.CallbackClasses.BooleanCallback;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseMemo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Hashtable;

public class MemoHandler extends SuperHandler implements Serializable {
    private String username;
    private int count;
    private DataManager dataManager = new DataManager();

    /**
     * Represents the ID of the calendar the Event Handler is associated with.
     */
    private String calendarName;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String TAG = "MemoHandler";
    //private ControllerMemoHandler controllerMemoHandler;

    public MemoHandler(String username, String calendarName) {
        super(username);
        this.username = username;
        this.calendarName = calendarName;
        updateCount(new com.example.calendarapp.database.CallbackClasses.BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool)
                    Log.d(TAG, "MemoHandler count updated");
            }
        });
    }

    void updateCountOnDB() {
        mDatabase.child("memos").child(username).child(calendarName).child("count").setValue(count);
    }

    void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("memos").child(username).child(calendarName).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("memos").child(username).child(calendarName).child("count").setValue(count);
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

    private void addMemoToDatabase(Memo memo) {
        DatabaseMemo dbMemo = new DatabaseMemo(memo);
        System.out.println("id:");
        System.out.println(memo.id);
        mDatabase.child("memos").child(username).child(calendarName).child(String.valueOf(this.count)).setValue(dbMemo);
    }

    public Memo createMemo(String message, int id) {
        Memo newMemo = dataManager.createMemo(id, message);
        addMemoToDatabase(newMemo);
        this.count += 1;
        updateCountOnDB();
        return newMemo;
    }

    public void getWithId(String id, HashtableCallback callBack) {
        Hashtable<String, DatabaseMemo> dbMemos = new Hashtable<>();

        final DatabaseReference memosRef = mDatabase.child("memos").child(username).child(calendarName);
        memosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    if(alertSnapshot.child("id").getValue() != null) {
                        if(alertSnapshot.child("id").getValue().equals(id)) {
                            dbMemos.put(alertSnapshot.getKey(), alertSnapshot.getValue(DatabaseMemo.class));
                        }
                    }
                }
                callBack.onCallback(dbMemos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadMemos:onCancelled", databaseError.toException());
            }
        });
    }

    public void deleteWithId(String id) {
        final DatabaseReference event = mDatabase.child("memos").child(username).child(calendarName).child(id);
        event.removeValue();
    }

}
package com.example.calendarapp.calendar;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.calendarapp.database.BooleanCallback;
import com.example.calendarapp.database.DatabaseMemo;
import com.example.calendarapp.database.FirebaseCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class MemoHandler implements Serializable {
    private String username;
    private int count;

    //private static ArrayList<Memo> memos = new ArrayList<Memo>();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String TAG = "MemoHandler";

    // constructor


    public MemoHandler(String username) {
        this.username = username;
        this.updateCount(new BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool)
                    Log.d(TAG, "MemoHandler count updated");
            }
        });
    }

    private void updateCountOnDB() {
        mDatabase.child("memos").child(username).child("count").setValue(count);
    }

    private void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("memos").child(username).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("memos").child(username).child("count").setValue(count);
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
        mDatabase.child("memos").child(username).child(memo.id).setValue(dbMemo);
    }

    private void getMemosFromDatabase(FirebaseCallBack firebaseCallBack) {
        DatabaseReference memosRef = mDatabase.child("memos").child(username);
        memosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //memos.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    DatabaseMemo dbMemo = keyNode.getValue(DatabaseMemo.class);
                    //Memo memo = dbMemo.getMemo();
                    //memos.add(memo);
                }
                //firebaseCallBack.onCallback(memos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    /**
     * Returns a newly created Memo object with message as its message.
     *
     * @param  message  the username String
     */
    // create memo
    // returns a new memo or an existing one if it has already been created
    public Memo createMemo(String message) {
        /*String newMemoStringStripped = message.replaceAll("s+", "");
        for (Memo curr: memos) {
            String currentMemo = curr.getMessage();
            String strippedAlreadyPresentMemo;
            strippedAlreadyPresentMemo = currentMemo.replaceAll("s+", "");
            if (newMemoStringStripped.equals(strippedAlreadyPresentMemo)) {
                return curr;
            }
        }*/
        // only when the memo isnt in the array memo already we return it to be set as an attribute
//      @osman when I included the array list events in memo's constructor, this error popped up since there isn't an array list in this constructor call
        Memo newMemo = new Memo(this.count, message);
        this.addMemo(newMemo);
        System.out.println("count:");
        System.out.println(count);
        this.count += 1;
        updateCountOnDB();
        return newMemo;
    }

    // to be used in createMemo to store the newly created memo in the array
    /**
     * Adds a memo to the database inorder to persist it.
     *
     * @param  memo  a Memo object.
     */
    private void addMemo(Memo memo) {
        //memos.add(memo);
        addMemoToDatabase(memo);
    }

    // setting events attribute called memo
    // adding
    /**
     * Adds Event object to the Memo Object.
     *
     * @param  memo the Memo object
     * @param event the Event object
     */
    public void addEventToMemo(Memo memo, Event event) {
        // event.setMemo(memo) code can be inside the method creating the event
        // for now lets keep it here
        event.setMemo(memo);
        memo.addEventToMemo(event);
    }

    /**
     * Prints all Memos from database to System.out
     *
     */
    public void printAllMemos() {
        final DatabaseReference alertRef = mDatabase.child("memos").child(username);
        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Memos:");
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    System.out.println(alertSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadMemo:onCancelled", databaseError.toException());
            }
        });
    }

}
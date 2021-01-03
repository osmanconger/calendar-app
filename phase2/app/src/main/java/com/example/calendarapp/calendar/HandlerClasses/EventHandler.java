package com.example.calendarapp.calendar.HandlerClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.calendarapp.calendar.DataClasses.Category;
import com.example.calendarapp.calendar.DataClasses.DataManager;
import com.example.calendarapp.calendar.DataClasses.Event;
import com.example.calendarapp.calendar.DataClasses.Memo;
import com.example.calendarapp.database.CallbackClasses.BooleanCallback;
import com.example.calendarapp.database.CallbackClasses.DatabaseEventCallback;
import com.example.calendarapp.database.CallbackClasses.FirebaseCallBack;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

/** Class which creates instances of events for a particular user
 * A User can only create one instance of this class
 */
public class EventHandler extends SuperHandler implements Serializable {
    private String username;
    private int count;
    private DataManager dataManager = new DataManager();
    private String calendarName;
    private String TAG = "EventHandler";
    private ArrayList<Category> Categories = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    //private ControllerEventHandler controllerEventHandler;
    public boolean didUpdateCount = false;

    public EventHandler(String username, String calendarName) {
        super(username);
        this.username = username;
        this.calendarName = calendarName;
        updateCount(new com.example.calendarapp.database.CallbackClasses.BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool) {
                    Log.d(TAG, "EventHandler count updated to " + count);
                    didUpdateCount = true;
                }
            }
        });
    }

    void updateCountOnDB() {
        mDatabase.child("events").child(username).child(calendarName).child("count").setValue(count);
    }

    public void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("events").child(username).child(calendarName).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("events").child(username).child(calendarName).child("count").setValue(count);
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

    private void addEventToDatabase(Event event) {
        DatabaseEvent dbEvent = new DatabaseEvent(event);
        mDatabase.child("events").child(username).child(calendarName).child(event.id).setValue(dbEvent);
    }


    //public void addCategoryToDatabase(Category category) {
      //  mDatabase.child("categories").child(username).child(calendarName).child(category.getName()).setValue(category);
    //}

    public void getCategoriesFromDatabase(FirebaseCallBack firebaseCallBack) {
        DatabaseReference categoriesRef = mDatabase.child(username).child("categories");

        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Categories.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Category category = keyNode.getValue(Category.class);
                    Categories.add(category);
                }
                firebaseCallBack.onCallback(Categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private boolean categoryInArray(String name) {
        for (Category category: this.Categories) {
            if (name.equals(category.getName())) {
                return true;
            }
        }
        return false;
    }

    private int getIndexOfCategory(String name) {
        int index = 0;
        for (Category category: this.Categories) {
            if (name.equals(category.getName())) {
                return index;
            }
            index++;
        }
        return index;
    }

    /** Adds recurring events to the Events instance variable
     * @param eventName of type String representing the name of the event
     * @param categoryName of type String representing the category of the event
     * @param startDate of type LocalDate representing the start date of the event
     * @param endDate of type LocalDate representing the end date of the event
     * @param startTime of type LocalTime representing the start time of the event
     * @param endTime of type LocalTime representing the end time of the event
     * @param numberOfEvents of type int representing the number of times the event has to repeat
     * @param days every number of days the event repeats
     * @param message of type String representing the contents of memo
     * @param hashTags of type Array of String representing the hashtags associated with the event
     * @param memoHandler instance of MemoHandler
     */
    public void addEventsByFrequency(String eventName, String categoryName, LocalDate startDate, LocalDate endDate,
                                     LocalTime startTime, LocalTime endTime, int numberOfEvents, int days,
                                     String message, String hashTags, MemoHandler memoHandler, String location) {
        // To add infinite events, change numberOfEvents to 50
        for(int i=0; i<numberOfEvents; i++) {
            addSingleEvent(eventName, categoryName, startDate, endDate, startTime, endTime, message, hashTags, memoHandler, location);
            startDate = startDate.plusDays(days);
            endDate = endDate.plusDays(days);
        }
    }

    /** Adds a single event to the Events instance variable
     * @param eventName of type String representing the name of the event
     * @param categoryName of type String representing the category of the event
     * @param startDate of type LocalDate representing the start date of the event
     * @param endDate of type LocalDate representing the end date of the event
     * @param startTime of type LocalTime representing the start time of the event
     * @param endTime of type LocalTime representing the end time of the event
     * @param message of type String representing the contents of memo
     * @param hashTags of type Array of String representing the hashTags associated with the event
     * @param memoHandler instance of MemoHandler
     */
    public void addSingleEvent(String eventName, String categoryName, LocalDate startDate, LocalDate endDate,
                               LocalTime startTime, LocalTime endTime, String message, String hashTags,
                               MemoHandler memoHandler, String location) {
        Category newCategory;
        Event newEvent = new Event(startDate, endDate, startTime, endTime, eventName, hashTags, count, location, categoryName);
        addEventToDatabase(newEvent);
        this.count = this.count + 1;
        updateCountOnDB();
        if (memoHandler != null && message != null && !message.equals("")) {
            Memo memo = memoHandler.createMemo(message, count);
        }

        if (this.categoryInArray(categoryName)) {
            int z = this.getIndexOfCategory(categoryName);
            newCategory = this.Categories.get(z);
            newCategory.add(newEvent);
            this.Categories.set(z, newCategory);
        } else {
            newCategory = dataManager.createCategory(categoryName);
            this.Categories.add(newCategory);
        }
    }

    /** Returns the event with the specific id.
     *
     * @param id of the event to be returned
     * @param callBack - A callback object for the database
     */

    public void getWithId(String id, DatabaseEventCallback callBack) {
        final DatabaseReference alertRef = mDatabase.child("events").child(username).child(calendarName).child(id);
        alertRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callBack.onCallback(dataSnapshot.getValue(DatabaseEvent.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    /** Adds the event with the specific id to user with username.
     *
     * @param id of the event to be returned
     * @param username of the user to be added to the event
     */
    public void addEventToUser(String id, String username) {

        final DatabaseReference alertRef = mDatabase.child("events").child(this.username).child(calendarName).child(id);
        alertRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EventHandler newUserEventHandler = new EventHandler(username, "Work");
                DatabaseEvent dbEvent = dataSnapshot.getValue(DatabaseEvent.class);
                LocalDate startDate = LocalDate.parse(dbEvent.startDate);
                LocalDate endDate = LocalDate.parse(dbEvent.endDate);
                LocalTime startTime = LocalTime.parse(dbEvent.startTime);
                LocalTime endTime = LocalTime.parse(dbEvent.endTime);
                newUserEventHandler.updateCount(new BooleanCallback() {
                    @Override
                    public void onCallback(Boolean bool) {
                        newUserEventHandler.addSingleEvent(dbEvent.name, "", startDate, endDate, startTime,
                                endTime, "", dbEvent.hashTags, null, dbEvent.location);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    public void getEventsForSearch(HashtableCallback callback) {
        Hashtable<String, String> dbEvents = new Hashtable<>();

        final DatabaseReference eventsRef = mDatabase.child("events").child(username).child(calendarName);
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    if(alertSnapshot != null && alertSnapshot.getKey()!= null && alertSnapshot.child("name").getValue() != null) {
                        dbEvents.put(alertSnapshot.getKey(), (String) alertSnapshot.child("name").getValue());
                    }
                }
                callback.onCallback(dbEvents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    public void getEventsForDate(LocalDate date, FirebaseCallBack callBack) {
        ArrayList<DatabaseEvent> dbEvents = new ArrayList<>();
        String dateString = date.toString();

        final DatabaseReference eventsRef = mDatabase.child("events").child(username).child(calendarName);
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    if(alertSnapshot.child("startDate").getValue() != null) {
                        if(alertSnapshot.child("startDate").getValue().equals(dateString)) {
                            dbEvents.add(alertSnapshot.getValue(DatabaseEvent.class));
                        }
                    }
                }
                callBack.onCallback(dbEvents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    public void deleteWithId(String id) {
        final DatabaseReference event = mDatabase.child("events").child(username).child(calendarName).child(id);
        event.removeValue();
    }

    /** Updates event with the given ID with dbEvent.
     *
     * @param id of the event to be updated.
     * @param dbEvent Object with which the event should be updated.
     */
    public void updateEventWithId(String id, DatabaseEvent dbEvent) {
        final DatabaseReference event = mDatabase.child("events").child(username).child(calendarName).child(id);
        event.setValue(dbEvent);
    }
}

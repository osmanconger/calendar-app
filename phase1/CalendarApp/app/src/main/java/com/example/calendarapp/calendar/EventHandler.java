package com.example.calendarapp.calendar;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.calendarapp.database.BooleanCallback;
import com.example.calendarapp.database.FirebaseCallBack;
import com.example.calendarapp.database.DatabaseEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Class which creates instances of events for a particular user
 * A User can only create one instance of this class
 */
public class EventHandler implements Serializable {
    private String username;
    private int count;
    /**Represents the events which are fetched from the database
     */
    private ArrayList<Event> Events = new ArrayList<>();

    private String TAG = "EventHandler";

    /**Represents the categories of events which are fetched from the database
     */
    private ArrayList<Category> Categories = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    /** Constructor for class EventHandler
     *
     * @param username of type String which represents the unique username of a user
     */
    public EventHandler(String username) {
        this.username = username;
        this.updateCount(new BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if (bool)
                    Log.d(TAG, "EventHandler count updated");
            }
        });
    }

    private void updateCountOnDB() {
        mDatabase.child("events").child(username).child("count").setValue(count);
    }

    private void updateCount(BooleanCallback callback) {
        final DatabaseReference countRef = mDatabase.child("events").child(username).child("count");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If count does not exist
                if (!dataSnapshot.exists()) {
                    mDatabase.child("events").child(username).child("count").setValue(count);
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
        mDatabase.child("events").child(username).child(event.id).setValue(dbEvent);
    }

    public void addCategoryToDatabase(Category category) {
        mDatabase.child("categories").child(username).child(category.getName()).setValue(category);
    }

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
     * @param first_date of type LocalDate representing the start date of the event
     * @param second_date of type LocalDate representing the end date of the event
     * @param first_time of type LocalTime representing the start time of the event
     * @param second_time of type LocalTime representing the end time of the event
     * @param numberOfEvents of type int representing the number of times the event has to repeat
     * @param daysOfWeek of type ArrayList representing the days of the week that the event has to repeat on
     * @param message of type String representing the contents of memo
     * @param hashTags of type Array of String representing the hashtags associated with the event
     * @param memoHandler instance of MemoHandler
     */
    public void addEventsByFrequency(String eventName, String categoryName,
                                     String first_date, String second_date,
                                     String first_time, String second_time, int numberOfEvents,
                                     ArrayList<Integer> daysOfWeek, String message, String hashTags, MemoHandler memoHandler) {
        LocalDate startDate = LocalDate.parse(first_date);
        LocalTime startTime = LocalTime.parse(first_time);
        LocalDate endDate = LocalDate.parse(second_date);
        LocalTime endTime = LocalTime.parse(second_time);
        int dayIncrementer = 1;
        LocalDate startDatePointer = startDate;
        LocalDate endDatePointer = endDate;
        int currentDay = startDatePointer.getDayOfWeek().getValue();
        Category newCategory;
        while (dayIncrementer <= numberOfEvents) {
            if (daysOfWeek.contains(currentDay)) {
                Memo memo = memoHandler.createMemo(message);
                Event newEvent = new Event(startDatePointer, endDatePointer, startTime, endTime, eventName, hashTags, count);
                addEventToDatabase(newEvent);
                this.count += 1;
                updateCountOnDB();
                this.addEventToEvents(newEvent);
                newEvent.setMemo(memo);
                memo.addEventToMemo(newEvent);
                dayIncrementer++;
                if (this.categoryInArray(categoryName)) {
                    int categoryPointer = this.getIndexOfCategory(categoryName);
                    newCategory = this.Categories.get(categoryPointer);
                    newCategory.add(newEvent);
                    this.Categories.set(categoryPointer, newCategory);
                } else {
                    newCategory = new Category(categoryName);
                    this.Categories.add(newCategory);
                }
            }
            startDatePointer = startDatePointer.plusDays(1);
            endDatePointer = endDatePointer.plusDays(1);
            currentDay = startDatePointer.getDayOfWeek().getValue();
        }
    }

    /** Adds a single event to the Events instance variable
     * @param eventName of type String representing the name of the event
     * @param categoryName of type String representing the category of the event
     * @param first_date of type LocalDate representing the start date of the event
     * @param second_date of type LocalDate representing the end date of the event
     * @param first_time of type LocalTime representing the start time of the event
     * @param second_time of type LocalTime representing the end time of the event
     * @param message of type String representing the contents of memo
     * @param hashTags of type Array of String representing the hashtags associated with the event
     * @param memoHandler instance of MemoHandler
     */
    public void addSingleEvent(String eventName, String categoryName, String first_date,
                               String second_date, String first_time, String second_time,
                               String message, String hashTags, MemoHandler memoHandler) {
        LocalDate startDate = LocalDate.parse(first_date);
        LocalTime startTime = LocalTime.parse(first_time);
        LocalDate endDate = LocalDate.parse(second_date);
        LocalTime endTime = LocalTime.parse(second_time);
        Category newCategory;
        Memo memo = memoHandler.createMemo(message);
        Event newEvent = new Event(startDate, endDate, startTime, endTime, eventName, hashTags, count);
        addEventToDatabase(newEvent);
        this.count += 1;
        updateCountOnDB();
        this.addEventToEvents(newEvent);
        newEvent.setMemo(memo);
        //memo.addEventToMemo(newEvent);
        if (this.categoryInArray(categoryName)) {
            int z = this.getIndexOfCategory(categoryName);
            newCategory = this.Categories.get(z);
            newCategory.add(newEvent);
            this.Categories.set(z, newCategory);
        } else {
            newCategory = new Category(categoryName);
            this.Categories.add(newCategory);
        }
    }

    /**
     *
     * @param eventName of type String representing the name of the event
     * @param categoryName of type String representing the category of the event
     * @param hashTags of type Array of String representing the hashtags associated with the event
     * @param first_date of type LocalDate representing the start date of the event
     * @param second_date of type LocalDate representing the end date of the event
     * @param first_time of type LocalTime representing the start time of the event
     * @param second_time of type LocalTime representing the start time of the event
     * @param daysOfWeek of type ArrayList representing the days of the week that the event has to repeat on
     * @param message of type String representing the contents of memo
     * @param memoHandler instance of MemoHandler
     */
    private void addInfiniteEvents(String eventName, String categoryName,
                                   String hashTags, String first_date, String second_date,
                                   String first_time, String second_time, ArrayList<Integer> daysOfWeek,
                                   String message, MemoHandler memoHandler) {
        LocalDate startDate = LocalDate.parse(first_date);
        LocalTime startTime = LocalTime.parse(first_time);
        LocalDate endDate = LocalDate.parse(second_date);
        LocalTime endTime = LocalTime.parse(second_time);
        int year = startDate.getYear();
        String year1 = String.valueOf(year);
        String str = year1 + "-12-31";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endOfYear = LocalDate.parse(str, formatter);
        LocalDate startDatePointer = startDate;
        LocalDate endDatePointer = endDate;
        int currentDay = startDatePointer.getDayOfWeek().getValue();
        Category newCategory;
        while (startDatePointer.isBefore(endOfYear)) {
            if (daysOfWeek.contains(currentDay)) {
                Memo memo = memoHandler.createMemo(message);
                Event newEvent = new Event(startDatePointer, endDatePointer, startTime, endTime, eventName, hashTags, count);
                addEventToDatabase(newEvent);
                this.count += 1;
                updateCountOnDB();
                if (this.Events.size() == 0) {
                    //newEvent.SetEventId(0);
                } else {
                    int lastindex = (this.Events.size()-1);
                    //int newId = this.Events.get(lastindex).GetEventId() + 1;
                    //newEvent.SetEventId(newId);
                }
                this.addEventToEvents(newEvent);
                newEvent.setMemo(memo);
                //memo.addEventToMemo(newEvent);
                if (this.categoryInArray(categoryName)) {
                    int z = this.getIndexOfCategory(categoryName);
                    newCategory = this.Categories.get(z);
                    newCategory.add(newEvent);
                    this.Categories.set(z, newCategory);
                } else {
                    newCategory = new Category(categoryName);
                    this.Categories.add(newCategory);
                }
            }
            startDatePointer = startDatePointer.plusDays(1);
            endDatePointer = endDatePointer.plusDays(1);
            currentDay = startDatePointer.getDayOfWeek().getValue();
        }
    }

    /** Gets events by their name
     * @param name of type String representing the name of the event to be searced
     * @return ArrayList of type Event which includes all the events with the specified name
     */
    private ArrayList<Event> getEventsByName(String name) {
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event event : this.Events) {
            if (event.getEventName().equals(name)) {
                newEvents.add(event);
            }
        }
        return newEvents;
    }

    /** Gets events in between the dates given
     * @param dateLowerBound of type LocalDate representing the lowerbound of the date to be searched for
     * @param dateUpperBound of type LocalDate representing the upperbound of the date to be searched for
     * @return ArrayList of type Event which includes all the events within the given dates
     */
    private ArrayList<Event> getEventsBetweenDates(LocalDate dateLowerBound,
                                                   LocalDate dateUpperBound) {
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event event: this.Events) {
            if ((event.getEventStartDate().isAfter(dateLowerBound)) && (event.getEventStartDate().isBefore(dateUpperBound))) {
                newEvents.add(event);
            }
        }
        return newEvents;
    }

    /** Gets events by their hashtags
     * @param wordsToSearch of type String representing the hashtags to be searched for with commas separated values
     * @return ArrayList of type Event which includes all the events with the hashtags input
     */
    private ArrayList<Event> getEventsByHashTags(String wordsToSearch) {
            ArrayList<Event> newEvents = new ArrayList<Event>();
            for (Event event: this.Events) {
                String[] words = wordsToSearch.split(",");
                ArrayList<String> wordsToSearchAList = new ArrayList<>();
                for (String word : words) {
                    wordsToSearchAList.add(word.trim());
                }
                ArrayList<String> HashTagsArrayList = new ArrayList<>();
                Collections.addAll(HashTagsArrayList, event.getEventHashTags());
                if (areHashTagsSimilar(HashTagsArrayList, wordsToSearchAList)) {
                    newEvents.add(event);
                }
            }
            return newEvents;
    }
    private boolean areHashTagsSimilar(ArrayList<String> list1, ArrayList<String> list2) {
        for (String item : list1) {
            if (list2.contains(item)) {
                return true;
            }
        }
        return false;
    }
    private ArrayList<Event> getAllEvents() {
        return this.Events;
    }

    // need to add in chronological manner - HASSAN
    private void addEventToEvents(Event event) {
        this.Events.add(event);
    }

    /** Sorts the Events instance variable ArrayList by startdate
     */
    public void sortEventsArrayList() {
        Collections.sort(this.Events, new Comparator<Event>() {
            @Override
            public int compare(Event eventCurrent, Event eventOther) {
                return eventCurrent.getEventStartDate().compareTo(eventOther.getEventStartDate());
            }
        });
    }

    /** Sorts the Categories instance variable ArrayList by alphabetical order
     */
    public void sortCategoriesArrayList() {
        Collections.sort(this.Categories, Comparator.comparing(Category::getName));
    }

    /**
     * Prints all Events from database to System.out
     *
     */
    public void printAllEvents() {
        final DatabaseReference alertRef = mDatabase.child("events").child(username);
        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Events:");
                for (DataSnapshot alertSnapshot: dataSnapshot.getChildren()) {
                    System.out.println(alertSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

}

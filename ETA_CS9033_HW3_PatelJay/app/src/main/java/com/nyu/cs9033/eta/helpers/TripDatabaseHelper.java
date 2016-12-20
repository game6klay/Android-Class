package com.nyu.cs9033.eta.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

public class TripDatabaseHelper extends SQLiteOpenHelper {

    // Name of the database
    private static final String DATABASE_NAME = "eta.db";
    private static final int DATABASE_VERSION = 1;
    // Table columns
    public static final String TABLE_TRIPS = "trips";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRIP_NAME = "trip_name";
    public static final String COLUMN_MEETUP_LOCATION = "meetup_location";
    public static final String COLUMN_TRIP_DESTINATION = "trip_destination";
    public static final String COLUMN_MEETING_TIME = "meeting_time";
    public static final String COLUMN_TRIP_DATE = "trip_date";
    public static final String TABLE_MEMBERS = "members";
    public static final String COLUMN_MEMBER_NAME = "name";
    public static final String COLUMN_TRIP_ID = "trip_id";

    // Database creation sql statement
    private static final String TABLE_TRIPS_CREATE = "create table "
            + TABLE_TRIPS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TRIP_NAME
            + " text not null, " + COLUMN_MEETUP_LOCATION + " text not null, " + COLUMN_TRIP_DESTINATION + " text not null, " + COLUMN_MEETING_TIME + " text not null, " + COLUMN_TRIP_DATE + " text not null);";

    private static final String TABLE_PERSONS_CREATE = "create table "
            + TABLE_MEMBERS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TRIP_ID
            + " integer not null, " + COLUMN_MEMBER_NAME + " text not null);";

    // Database helper constructor
    public TripDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(TABLE_TRIPS_CREATE);
        db.execSQL(TABLE_PERSONS_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        onCreate(db);

    }

    // Adding new trip
    public long addTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_NAME, trip.getNameOfTrip()); // Trip Name
        values.put(COLUMN_MEETUP_LOCATION, trip.getMeetingLocation()); // Trip Meeting location
        values.put(COLUMN_TRIP_DESTINATION, trip.getTripDestination()); // Trip Destination
        values.put(COLUMN_MEETING_TIME, trip.getTimeOfTrip()); // Trip time
        values.put(COLUMN_TRIP_DATE, trip.getDateToTrip()); // Trip date

        // Inserting Row
        long id = db.insert(TABLE_TRIPS, null, values);

        for (Person person : trip.getTripMembers()) {
            values = new ContentValues();
            values.put(COLUMN_MEMBER_NAME, person.getName()); // Person Name
            values.put(COLUMN_TRIP_ID, id); // Trip ID
            db.insert(TABLE_MEMBERS, null, values);
        }

        db.close(); // Closing database connection

        return id;
    }

    // Getting single trip
    public Trip getTrip(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Grab a trip based on name
        Cursor cursor = db.query(TABLE_TRIPS, new String[]{COLUMN_ID,
                        COLUMN_TRIP_NAME, COLUMN_TRIP_DATE}, COLUMN_TRIP_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String meetUpLocation = cursor.getString(1);
        String tripDestination = cursor.getString(2);
        String tripTime = cursor.getString(3);
        String tripDate = cursor.getString(4);
        String tripName = cursor.getString(0);

        // Grab all members of the grabbed trip
        cursor = db.query(TABLE_MEMBERS, new String[] { COLUMN_ID,
                        COLUMN_MEMBER_NAME, COLUMN_TRIP_ID }, COLUMN_TRIP_ID + "=?",
                new String[] { String.valueOf(tripName) }, null, null, null, null);

        ArrayList<Person> tripMembers = new ArrayList<Person>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Person person = new Person(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            tripMembers.add(person);
        }

        Trip trip = new Trip(tripName, meetUpLocation, tripDestination, tripTime, tripDate, tripMembers);
        return trip;
    }

    private List<Person> getTripMembers(int tripId) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Grab all members of the grabbed trip
        Cursor cursor = db.query(TABLE_MEMBERS, new String[] { COLUMN_ID,
                        COLUMN_MEMBER_NAME, COLUMN_TRIP_ID }, COLUMN_TRIP_ID + "=?",
                new String[] { String.valueOf(tripId) }, null, null, null, null);

        ArrayList<Person> tripMembers = new ArrayList<Person>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Log.d("Retrival", cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME)));
            Person person = new Person(cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME)), "", "");
            tripMembers.add(person);
        }
        return tripMembers;
    }

    // Getting All Trips
    public ArrayList<Trip> getAllTrips() {
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Trip trip = new Trip(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(3), cursor.getString(5), getTripMembers(cursor.getInt(0)));
            tripList.add(trip);
        }
        cursor.close();
        return tripList;
    }

    // Getting trips Count
    public int getTripCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRIPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


}

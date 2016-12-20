package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.models.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CreateTripActivity extends Activity {

    private static final String TAG = "CreateTripActivity";

    private TextView enterDetails;
    private EditText nameOfTrip;
    private EditText locationToMeet;
    private EditText tripDestination;
    private EditText timeToMeet;
    private EditText dateForMeet;
    private TextView friendsAttending;
    private CheckBox larryBox;
    private CheckBox sundarBox;
    private CheckBox markBox;
    private CheckBox jeffBox;
    private CheckBox timBox;
    private CheckBox elonBox;
    private Button saveTripCreateActivity;
    private Button cancelTripCreatelActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_trip);

        // TODO - fill in here

        initiateComponents();
        addEventTrackers();
    }

    private void initiateComponents() {

        enterDetails = (TextView) findViewById(R.id.enterDetailsText);
        nameOfTrip = (EditText) findViewById(R.id.tripName);
        locationToMeet = (EditText) findViewById(R.id.meetupLocation);
        tripDestination = (EditText) findViewById(R.id.tripDestination);
        timeToMeet = (EditText) findViewById(R.id.meetupTime);
        dateForMeet = (EditText) findViewById(R.id.meetupDate);
        friendsAttending = (TextView) findViewById(R.id.attendees);
        larryBox = (CheckBox) findViewById(R.id.larryPage);
        sundarBox = (CheckBox) findViewById(R.id.sundarPichai);
        markBox = (CheckBox) findViewById(R.id.markZuckerberg);
        jeffBox = (CheckBox) findViewById(R.id.jeffBezoz);
        timBox = (CheckBox) findViewById(R.id.timCook);
        elonBox = (CheckBox) findViewById(R.id.elonMusk);
        saveTripCreateActivity = (Button) findViewById(R.id.saveTripForCreateActivity);
        cancelTripCreatelActivity = (Button) findViewById(R.id.cancelTripForCreateActivity);

    }

    private void addEventTrackers() {

        saveTripCreateActivity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                persistTrip(createTrip());
            }
        });

        cancelTripCreatelActivity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelTrip();
            }
        });

    }

    public boolean saveTrip(Trip trip)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("new_trip", trip);
        try
        {
            this.setResult(RESULT_OK, intent);
            this.finish();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error! " + e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    public boolean persistTrip(Trip trip) {
        if (trip == null)
            return false;
        else {
            getIntent().putExtra("trip", trip);
            setResult(RESULT_OK, getIntent());
            finish();
            return true;
        }
    }

    public Trip createTrip() {

        // TODO - fill in here

        String nameTrip = String.valueOf(nameOfTrip.getText());
        String meetLocation = String.valueOf(locationToMeet.getText());
        String reachLocation = String.valueOf(tripDestination.getText());
        String time = String.valueOf(timeToMeet.getText());
        String date = String.valueOf(dateForMeet.getText());
        ArrayList<Person> people = new ArrayList<Person>();

        if (larryBox.isChecked())
            people.add(new Person("Larry Page ","larry@alph.com", " 383-333-0000"));
        if (sundarBox.isChecked())
            people.add(new Person("Sundar Pichai"," sundar@goo.com", " 400-333-9999"));
        if (markBox.isChecked())
            people.add(new Person("Mark Zuckerberg"," mark@fb.com"," 500-000-9999"));
        if (jeffBox.isChecked())
            people.add(new Person("Jeff Bezoz"," jbk@ama.com"," 500-990-1111"));
        if (timBox.isChecked())
            people.add(new Person("Tim Cook"," mk@fb.com"," 500-990-2222"));
        if (elonBox.isChecked())
            people.add(new Person("Elon Musk"," elonk@fb.com"," 500-990-9222"));

        try
        {
            return new Trip(nameTrip, meetLocation, reachLocation, time, date, people);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }

    }

    public void cancelTrip() {

        // TODO - fill in here

        setResult(RESULT_CANCELED, getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelTrip();
        super.onBackPressed();
    }
}

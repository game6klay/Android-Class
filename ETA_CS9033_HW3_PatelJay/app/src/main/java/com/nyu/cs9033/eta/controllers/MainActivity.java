package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.helpers.TripDatabaseHelper;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView welcomeText;
    private TextView createTripText;
    private TextView viewTripText;
    private Button createTripButton;
    private Button viewButton;
    private boolean tripExist;
    private Trip trip;
    private final int CREATE_TRIP = 1;
    private final int VIEW_TRIP = 2;
    private static final int TRIP_HISTORY = 2001;
    private TripDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TripDatabaseHelper(this);
        initiateComponents();
        addEventTrackers();
    }

    private void initiateComponents() {
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        createTripText = (TextView) findViewById(R.id.createTripText);
        viewTripText = (TextView) findViewById(R.id.viewTripText);
        createTripButton = (Button) findViewById(R.id.createTripButton);
        viewButton = (Button) findViewById(R.id.viewButton);
    }

    private void addEventTrackers() {

        createTripButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startCreateTripActivity();
            }
        });

        viewButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startViewTripActivity();
            }
        });

    }


    /**
     * This method should start the
     * Activity responsible for creating
     * a Trip.
     */
    public void startCreateTripActivity() {
        // TODO - fill in here
        Intent intentToStartCreatingTrip = new Intent(this,
                CreateTripActivity.class);
        startActivityForResult(intentToStartCreatingTrip, CREATE_TRIP);
    }

    /**
     * This method should start the
     * Activity responsible for viewing
     * a Trip.
     */
    public void startViewTripActivity() {

        //Intent i = new Intent(this,ViewTripActivity.class);
        //startActivity(i);
       /* Intent viewTripHistory = new Intent(this, TripHistoryActivity.class);
        try
        {
            startActivity(viewTripHistory);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Exception in startViewActivity: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }*/


        if (dbHelper.getTripCount() > 0)
            tripExist = true;
        else
            tripExist = false;

        if (tripExist) {
            Intent intentToViewTripHistory = new Intent(this,
                    TripHistoryActivity.class);
            startActivityForResult(intentToViewTripHistory, TRIP_HISTORY);
        } else
            Toast.makeText(this, "First Trip is yet to be  created!!",
                    Toast.LENGTH_LONG).show();

    }


}



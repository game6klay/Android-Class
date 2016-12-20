package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
    private boolean tripExist = false;
    private Trip trip;
    private final int CREATE_TRIP = 1;
    private final int VIEW_TRIP = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO - fill in here
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
		
		// TODO - fill in here

        if (tripExist) {
            Intent intentToViewTrip = new Intent(this, ViewTripActivity.class);
            intentToViewTrip.putExtra("trip", trip);
            startActivityForResult(intentToViewTrip, VIEW_TRIP);
        } else
            Toast.makeText(this, "First trip is not created yet!!",
                    Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Receive result from CreateTripActivity here.
	 * Can be used to save instance of Trip object
	 * which can be viewed in the ViewTripActivity.
	 * 
	 * Note: This method will be called when a Trip
	 * object is returned to the main activity. 
	 * Remember that the Trip will not be returned as
	 * a Trip object; it will be in the persisted
	 * Parcelable form. The actual Trip object should
	 * be created and saved in a variable for future
	 * use, i.e. to view the trip.
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO - fill in here

        switch (requestCode) {
            case CREATE_TRIP:
                if (resultCode == RESULT_OK) {
                    tripExist = true;
                    trip = data.getParcelableExtra("trip");
                } else
                    tripExist = false;
                break;
            case VIEW_TRIP:
                tripExist = true;
                break;
            default:
                tripExist = false;
                break;
        }
	}
    /*public void onReceivedTrip(Trip trip) {

        // TODO - fill in here
        tripExist = true;
    }*/


}

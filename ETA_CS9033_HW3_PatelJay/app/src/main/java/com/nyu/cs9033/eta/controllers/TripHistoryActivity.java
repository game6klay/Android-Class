package com.nyu.cs9033.eta.controllers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.helpers.TripDatabaseHelper;

public class TripHistoryActivity extends Activity {

    private static final String TAG = "TripHistoryActivity";

    private ListView memberList;

    private ArrayList<String> trips;
    private ArrayList<Trip> tripRaw;
    private ArrayAdapter<String> adapter;
    private Button mainMenu;

    private TripDatabaseHelper dbHelper;

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        dbHelper = new TripDatabaseHelper(this);

        initiateComponents();
        addEventListeners();

    }

    /**
     * This Method initiate all components required for the class.
     */
    private void initiateComponents() {

        tripRaw = dbHelper.getAllTrips();

        trips = new ArrayList<String>();

        for (Trip trip  : tripRaw) {
            trips.add(trip.getNameOfTrip());
        }

        mainMenu = (Button) findViewById(R.id.mainMenu);
        memberList = (ListView) findViewById(R.id.list_of_trips);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, trips);
        memberList.setAdapter(adapter);

    }

    /**
     * This method adds all the event listeners for the class .
     */
    private void addEventListeners() {

        memberList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    Trip trip = tripRaw.get(position);
                    Intent intentToViewTrip = new Intent(activity, ViewTripActivity.class);
                    intentToViewTrip.putExtra("trip", trip);
                    startActivityForResult(intentToViewTrip, 101);
                }

                catch (Exception e)
                {
                    Toast.makeText(TripHistoryActivity.this, "Exception in setOnItemClickListener: "
                            + e.toString(), Toast.LENGTH_LONG).show();
                }

            }

        });

        mainMenu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
    }


    /**
     * This method should start the Activity responsible for viewing a Trip.
     */
    public void startTripViewer(int tripId) {

    }

}



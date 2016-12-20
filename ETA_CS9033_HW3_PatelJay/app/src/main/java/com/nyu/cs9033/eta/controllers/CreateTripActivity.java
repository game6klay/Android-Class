package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.helpers.TripDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.provider.ContactsContract.Contacts;
import android.net.Uri;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class CreateTripActivity extends Activity {

    private static final String TAG = "CreateTripActivity";
    private static final int CONTACT_SELECTOR_RESULT = 1001;
    private static final int LOCATION_API_RESULT = 1002;
    private static Intent intent = null;

    private TextView enterDetails;
    private EditText nameOfTrip;
    private EditText locationToMeet;
    private EditText tripDestination;
    private EditText searchArea;
    private EditText timeToMeet;
    private EditText dateForMeet;
    private TextView friendsAttending;
    private Button addTripMember;
    private Button searchLocation;
    private ListView memberList;
    private Button saveTripCreateActivity;
    private Button cancelTripCreateActivity;

    private TripDatabaseHelper dbHelper;
    private List<Trip> tripList;
    private List<Person> personList = new ArrayList<Person>();
    private String friendsList;



    private Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        dbHelper = new TripDatabaseHelper(activity);
        initiateComponents();
        addEventTrackers();
    }

    private void initiateComponents() {
        enterDetails = (TextView) findViewById(R.id.enterDetailsText);
        nameOfTrip = (EditText) findViewById(R.id.tripName);
        locationToMeet = (EditText) findViewById(R.id.meetupLocation);
        tripDestination = (EditText) findViewById(R.id.tripDestination);
        searchArea = (EditText) findViewById(R.id.searchArea);
        timeToMeet = (EditText) findViewById(R.id.meetupTime);
        dateForMeet = (EditText) findViewById(R.id.meetupDate);
        friendsAttending = (TextView) findViewById(R.id.attendees);
        addTripMember = (Button) findViewById(R.id.addTripMember);
        searchLocation = (Button) findViewById(R.id.searchLocation);
        memberList = (ListView) findViewById(R.id.memberList);
        saveTripCreateActivity = (Button) findViewById(R.id.saveTripForCreateActivity);
        cancelTripCreateActivity = (Button) findViewById(R.id.cancelTripForCreateActivity);

        /*intent = getIntent();

        if (intent != null) {
            String intentAction = intent.getAction();
            if (intentAction != null
                    && intentAction.equalsIgnoreCase(Intent.ACTION_SEND)) {
                locationToMeet.setText(intent.getStringExtra("android.intent.extra.TEXT"));
            }
        }*/

    }

    private void addEventTrackers() {

        saveTripCreateActivity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                createTrip();
            }
        });

        searchLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                loadLocations();
            }
        });

        addTripMember.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent contactSelectorIntent = new Intent(Intent.ACTION_PICK,
                        Contacts.CONTENT_URI);
                startActivityForResult(contactSelectorIntent, CONTACT_SELECTOR_RESULT);
            }
        });

        cancelTripCreateActivity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelTrip();
            }
        });

    }

    public void loadLocations()
    {
        String tripDestination = String.valueOf(searchArea.getText());
        String sample = "nyu,brooklyn::engineering";
        Uri u = Uri.parse("location://com.example.nyu.hw3api");
        Intent forLocation = new Intent(Intent.ACTION_VIEW, u);
        if(tripDestination.equals(""))
            tripDestination = sample;
        forLocation.putExtra("searchVal", tripDestination);

        startActivityForResult(forLocation, LOCATION_API_RESULT);
    }

    /**
     * Method to handle data from other activities.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK && resultCode != Activity.RESULT_FIRST_USER) return;

        if(requestCode==CONTACT_SELECTOR_RESULT){
            if (data != null) {
                Uri uri = data.getData();

                if (uri != null) {
                    Cursor c = null;
                    try {

                        c = getContentResolver().query(uri, null, null, null, null);

                        if (c != null && c.moveToFirst()) {
                            String displayName = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
                            // String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.d("Contact", displayName);
                            Person obj = new Person(displayName, "", "");
                            personList.add(obj);
                            List<String> tripMembers = new ArrayList<>();
                            for(Person person: personList) {
                                tripMembers.add(person.getName());
                            }
                            ListView memberList = (ListView)findViewById(R.id.memberList);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tripMembers);
                            memberList.setAdapter(adapter);

                        }
                    } finally {
                        if (c != null) {
                            c.close();
                        }

                    }
                }
            }

        }
        else if(requestCode==LOCATION_API_RESULT){
            ArrayList<String> locDet = data.getStringArrayListExtra("retVal");
            String location = locDet != null && !locDet.isEmpty() ? locDet.get(0) : "not found";
            tripDestination.setText(location);
        }
    }



    public boolean saveTrip(Trip trip)
    {
        dbHelper.addTrip(trip);
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

    // this method is used to invoke the trip model that is been created

    public Trip createTrip() {

        String nameTrip = String.valueOf(nameOfTrip.getText());
        String meetLocation = String.valueOf(locationToMeet.getText());
        String reachLocation = String.valueOf(tripDestination.getText());
        String time = String.valueOf(timeToMeet.getText());
        String date = String.valueOf(dateForMeet.getText());

        Trip t = new Trip(nameTrip, meetLocation, reachLocation, time, date, personList);

        saveTrip(t);

        try
        {
            return t;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }

    }


    // below two methods are used to cancel trips while creating the trip or entering the details

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

package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ViewTripActivity extends Activity {

	private static final String TAG = "ViewTripActivity";

	private TextView viewTripName;
	private TextView viewMeetLocation;
	private TextView viewTripDestination;
	private TextView viewTripTime;
	private TextView viewTripDate;
	private TextView viewAttendees;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO - fill in here
		setContentView(R.layout.activity_view_trip);
		initiateComponents();
		addEventTrackers();
		Intent newIntent = getIntent();
		Trip trip = getTrip(newIntent);
		viewTrip(trip);
	}

	private void initiateComponents(){

		viewMeetLocation = (TextView) findViewById(R.id.view_meetup_place_text);
		viewTripName = (TextView) findViewById(R.id.view_trip_name_text);
		viewTripDestination = (TextView) findViewById(R.id.destination_text);
		viewTripTime = (TextView) findViewById(R.id.time_text);
		viewTripDate = (TextView) findViewById(R.id.view_date_text);
		viewAttendees = (TextView) findViewById(R.id.view_attendees);

	}

	private void addEventTrackers() {

		Button mainMenu = (Button) findViewById(R.id.viewActivityMainMenu);

		mainMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});


	}

	
	/**
	 * Create a Trip object via the recent trip that
	 * was passed to TripViewer via an Intent.
	 * 
	 * @param i The Intent that contains
	 * the most recent trip data.
	 * 
	 * @return The Trip that was most recently
	 * passed to TripViewer, or null if there
	 * is none.
	 */
	public Trip getTrip(Intent i) {
		// TODO - fill in here
		return (Trip) i.getExtras().getParcelable("trip");
	}

	/**
	 * Populate the View using a Trip model.
	 * 
	 * @param trip The Trip model used to
	 * populate the View.
	 */
	public void viewTrip(Trip trip) {
		viewMeetLocation.setText(trip.getMeetingLocation());
		viewTripName.setText(trip.getNameOfTrip());
		viewTripDestination.setText(trip.getTripDestination());
		viewTripTime.setText(trip.getTimeOfTrip());
		viewTripDate.setText(trip.getDateToTrip());
		if(trip.getTripMembers() != null && !trip.getTripMembers().isEmpty()) {
			List<Person> persons = trip.getTripMembers();
			String totalAttendee = "";
			for (Person person: persons) {
				String attendee = person.getName() + person.getEmail() + person.getPhone() + "\n";
				totalAttendee += attendee;
			}
			viewAttendees.setText(totalAttendee);
		}

	}
}

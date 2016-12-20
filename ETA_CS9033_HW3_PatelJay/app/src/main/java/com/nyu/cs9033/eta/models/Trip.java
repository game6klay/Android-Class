package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Trip implements Parcelable {

    private String nameOfTrip;
    private String meetingLocation;
    private String tripDestination;
    private String timeOfTrip;
    private String dateToTrip;
    private List<Person> tripMembers;

    // Member fields should exist here, what else do you need for a trip?
    // Please add additional fields

    public Trip(String nameOfTrip, String meetingLocation, String tripDestination, String timeOfTrip, String dateToTrip, List<Person> tripMembers) {
        this.nameOfTrip = nameOfTrip;
        this.meetingLocation = meetingLocation;
        this.tripDestination = tripDestination;
        this.timeOfTrip = timeOfTrip;
        this.dateToTrip = dateToTrip;
        this.tripMembers = tripMembers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameOfTrip);
        dest.writeString(this.meetingLocation);
        dest.writeString(this.tripDestination);
        dest.writeString(this.timeOfTrip);
        dest.writeString(this.dateToTrip);
        dest.writeTypedList(tripMembers);
    }

    protected Trip(Parcel in) {
        this.nameOfTrip = in.readString();
        this.meetingLocation = in.readString();
        this.tripDestination = in.readString();
        this.timeOfTrip = in.readString();
        this.dateToTrip = in.readString();
        this.tripMembers = in.createTypedArrayList(Person.CREATOR);
    }

    /**
     * Parcelable creator. Do not modify this function.
     */

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };


    // setter and getters of different fields

    public String getNameOfTrip() {
        return nameOfTrip;
    }

    public void setNameOfTrip(String nameOfTrip) {
        this.nameOfTrip = nameOfTrip;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTimeOfTrip() {
        return timeOfTrip;
    }

    public void setTimeOfTrip(String timeOfTrip) {
        this.timeOfTrip = timeOfTrip;
    }

    public String getDateToTrip() {
        return dateToTrip;
    }

    public void setDateToTrip(String dateToTrip) {
        this.dateToTrip = dateToTrip;
    }

    public List<Person> getTripMembers() {
        return tripMembers;
    }

    public void setTripMembers(List<Person> tripMembers) {
        this.tripMembers = tripMembers;
    }

}
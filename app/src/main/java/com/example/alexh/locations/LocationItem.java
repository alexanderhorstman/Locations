/*
 * A.J. De La Costa
 * This is the locationItem class for Locations App.
 * This will construct the locationItem object.
 */

package com.example.alexh.locations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocationItem implements Serializable{
    private Alarm alarm;
    private String name;	//The name of the location.
    private String address;	//Address of the location.
    private String reminderMessage;
    private double latitude;	//Latitude of the location.
    private double longitude;	//Longitude of the location.
    //private Calendar timeAdded;	//Calendar object that has not been set.
    private List<String> notes = new ArrayList<>();	//New String array that can hold 5 strings.
    private int numNotes = 0;	//An int to keep track of how many notes have been added.
    private Time reminderTime;	//A time object for the reminderTime.
    private Date reminderDate;	//A date object for the reminderDate.

    /*
     * Constructors
     */
    public LocationItem(){}

    public LocationItem(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationItem(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public LocationItem(String name, double longitude, double latitude, Alarm alarm) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.alarm = alarm;
    }

    public LocationItem(String name, String address, Alarm alarm) {
        this.name = name;
        this.address = address;
        this.alarm = alarm;
    }

    /*

     //To remove later

    public LocationItem(String name ,double longitude, double latitude, String reminderMessage, Time reminderTime, Date reminderDate) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.reminderMessage = reminderMessage;
        this.reminderTime = reminderTime;
        this.reminderDate = reminderDate;
    }

    public LocationItem(String name, String address, String reminderMessage, Time reminderTime, Date reminderDate) {
        this.name = name;
        this.address = address;
        this.reminderMessage = reminderMessage;
        this.reminderTime = reminderTime;
        this.reminderDate = reminderDate;
    }
    */
	
	/*
	 * Methods:
	 * This will include all of the setters and getters along with the manipulation of notes as well.
	 */
	
	/*
	 * Getters:
	 * 	getName()
	 * 	getAddress()
	 * 	getLatitude()
	 * 	getLongitude()
	 * 	getTimeAdded()
	 * 	getNotes()
	 * 	getReminderTime()
	 * 	getReminderDate()
	 */

    public Alarm getAlarm() {
        return alarm;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNote(int index) {
        return notes.get(index);
    }

    public List<String> getNotes() {
        return notes;
    }

    public String getReminderText() {
        return reminderMessage;
    }

    public Time getReminderTime() {
        return reminderTime;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    /*
    public Calendar getTimeAdded() {
        return timeAdded;
    }
    */

	/*
	 * Setters:
	 * 	setName()
	 * 	setAddress()
	 * 	setLatitude()
	 * 	setLongitude()
	 * 	setTimeAdded()
	 * 	setReminderTime()
	 * 	setReminderDate()
	 */

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /*
    public void setTimeAdded(Calendar timeAdded) {
        this.timeAdded = timeAdded;
    }
    */

    public void setReminderTime(Time reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }
	
	/*
	 * Note functions:
	 * 	addNote()	Will add a note into the array.
	 * 	removeNote()	Will make the note blank in the array by referencing it's location in the array. 
	 */

    public void addNote(String note) {
        if(numNotes < 5)	//Make sure the array isn't full
        {
            notes.add(note);	//Add the note in the most current position.
            numNotes++;
        }
        else	//If the array is full.
            System.out.println("You cannot add anymore notes.");
    }

    public boolean hasNotes() {
        return (numNotes > 0);
    }

    public void removeNote(int noteNumber) {
        notes.remove(noteNumber);
    }

}


package com.example.alexh.locations.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocationItem implements Serializable {
    private String name;	//The name of the location.
    private String address = "";	//Address of the location.
    private double latitude;	//Latitude of the location.
    private double longitude;	//Longitude of the location.
    private List<String> notes = new ArrayList<String>();	//New String array that can hold 5 strings.
    private int numNotes = 0;	//An int to keep track of how many notes have been added.

    /*
     * Constructors
     */
    public LocationItem(){}

    public LocationItem(String name, double latitude, double longitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationItem(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
	
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
	 */

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

	/*
	 * Setters:
	 * 	setName()
	 * 	setAddress()
	 * 	setLatitude()
	 * 	setLongitude()
	 * 	setTimeAdded()
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

    public void addNotes(List<String> notes) {
        if(notes.size() > 5) {
            throw new IllegalArgumentException();
        }
        else {
            this.notes = notes;
            numNotes = notes.size();
        }

    }

    public boolean hasAddress() {
        return !address.equals("");
    }

    public boolean hasNotes() {
        numNotes = notes.size();
        return (numNotes > 0);
    }

    public void removeNote(int noteNumber) {
        notes.remove(noteNumber);
    }

}

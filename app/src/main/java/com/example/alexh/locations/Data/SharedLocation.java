package com.example.alexh.locations.Data;

import java.io.Serializable;

public class SharedLocation extends LocationItem implements Serializable{

    String sender;

    //needed for firebase
    public SharedLocation() {}

    public SharedLocation(String sender, LocationItem item) {
        super(item.getName(),item.getLatitude(),item.getLongitude());
        if(item.hasAddress()) {
            setAddress(item.getAddress());
        }
        addNotes(item.getNotes());
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}

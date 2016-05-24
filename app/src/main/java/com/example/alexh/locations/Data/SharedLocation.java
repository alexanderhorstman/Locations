package com.example.alexh.locations.Data;

public class SharedLocation extends LocationItem {

    String sender;

    //needed for firebase
    public SharedLocation() {}

    public SharedLocation(String sender, LocationItem item) {
        super(item.getName(),item.getLatitude(),item.getLongitude());
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}

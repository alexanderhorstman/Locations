package com.example.alexh.locations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ObjectOutputStream;
import java.util.List;

public class ViewLocation extends FragmentActivity {

    private int index;
    private Holder viewHolder;
    private LocationItem item;

    //fired when the back button is pressed
    @Override
    public void onBackPressed() {
        //leave this activity
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_location);
        Intent intent = getIntent();
        index = intent.getIntExtra("position", 0);
        item = Globals.locationArray.get(index);
        viewHolder = new Holder(index);
        //move to initialize() method
        if(item.getAddress().equals("")) {
            viewHolder.addressView.setVisibility(View.GONE);
        }
        setUpMapIfNeeded();
        viewHolder.locationName.setText(item.getName());
    }

    public void deleteLocation(View view) {
        //get verification using a dialog box
        //delete the item if positive button is pressed
        //if negative button is pressed do nothing
        Globals.locationArray.remove(index);
        saveLocationsList();
        finish();
    }

    public void returnToMain(View view) {
        //leave this activity
        finish();
    }

    //used to save the list to file
    public void saveLocationsList() {
        //opens the list file and writes the list to it without append
        try{ObjectOutputStream listOutput =
                new ObjectOutputStream(openFileOutput(Globals.saveFileName, Context.MODE_PRIVATE));
            listOutput.writeObject(Globals.locationArray);
            listOutput.close();
        }
        catch(Exception e){e.printStackTrace();}
    }

    //set marker location to current location
    private void setUpMap() {
        double latitude = item.getLatitude();
        double longitude = item.getLongitude();
        viewHolder.map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(latitude, longitude)));
        viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
        viewHolder.map.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).title(item.getName()));
        viewHolder.map.setMyLocationEnabled(true);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (viewHolder.map == null) {
            // Try to obtain the map from the SupportMapFragment.
            viewHolder.map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (viewHolder.map != null) {
                setUpMap();
            }
        }
    }

    private class Holder {

        TextView locationName;
        LinearLayout addressView;
        TextView addressText;
        LinearLayout mapView;
        LinearLayout notesView;
        TextView note1;
        TextView note2;
        TextView note3;
        TextView note4;
        TextView note5;
        GoogleMap map;

        public Holder(int itemIndex) {
            locationName = (TextView) findViewById(R.id.locationNameViewLocation);
            addressView = (LinearLayout) findViewById(R.id.addressView);
            addressText = (TextView) findViewById(R.id.locationAddressViewLocation);
            mapView = (LinearLayout) findViewById(R.id.mapView);
            notesView = (LinearLayout) findViewById(R.id.notesLayoutViewLocation);
            note1 = (TextView) findViewById(R.id.note1TextViewLocation);
            note2 = (TextView) findViewById(R.id.note2TextViewLocation);
            note3 = (TextView) findViewById(R.id.note3TextViewLocation);
            note4 = (TextView) findViewById(R.id.note4TextViewLocation);
            note5 = (TextView) findViewById(R.id.note5TextViewLocation);

            //move to "initialize()" method
            /*
            if(item.getAddress() == null) {
                addressView.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
            }
            else {
                addressView.setVisibility(View.VISIBLE);
                addressText.setText(item.getAddress());
                mapView.setVisibility(View.VISIBLE);
            }
            */
            if(item.hasNotes()) {
                notesView.setVisibility(View.VISIBLE);
                List<String> notes = item.getNotes();
                int numNotes = notes.size();
                if(numNotes == 5) {
                    note1.setVisibility(View.VISIBLE);
                    note1.setText(notes.get(0));
                    note2.setVisibility(View.VISIBLE);
                    note2.setText(notes.get(1));
                    note3.setVisibility(View.VISIBLE);
                    note3.setText(notes.get(2));
                    note4.setVisibility(View.VISIBLE);
                    note4.setText(notes.get(3));
                    note5.setVisibility(View.VISIBLE);
                    note5.setText(notes.get(4));
                }
                else if(numNotes == 4) {
                    note1.setVisibility(View.VISIBLE);
                    note1.setText(notes.get(0));
                    note2.setVisibility(View.VISIBLE);
                    note2.setText(notes.get(1));
                    note3.setVisibility(View.VISIBLE);
                    note3.setText(notes.get(2));
                    note4.setVisibility(View.VISIBLE);
                    note4.setText(notes.get(3));
                    note5.setVisibility(View.GONE);
                }
                else if(numNotes == 3) {
                    note1.setVisibility(View.VISIBLE);
                    note1.setText(notes.get(0));
                    note2.setVisibility(View.VISIBLE);
                    note2.setText(notes.get(1));
                    note3.setVisibility(View.VISIBLE);
                    note3.setText(notes.get(2));
                    note4.setVisibility(View.GONE);
                    note5.setVisibility(View.GONE);
                }
                else if(numNotes == 2) {
                    note1.setVisibility(View.VISIBLE);
                    note1.setText(notes.get(0));
                    note2.setVisibility(View.VISIBLE);
                    note2.setText(notes.get(1));
                    note3.setVisibility(View.GONE);
                    note4.setVisibility(View.GONE);
                    note5.setVisibility(View.GONE);
                }
                else if(numNotes == 1) {
                    note1.setVisibility(View.VISIBLE);
                    note1.setText(notes.get(0));
                    note2.setVisibility(View.GONE);
                    note3.setVisibility(View.GONE);
                    note4.setVisibility(View.GONE);
                    note5.setVisibility(View.GONE);
                }
                else if(numNotes == 0) {
                    note1.setVisibility(View.GONE);
                    note2.setVisibility(View.GONE);
                    note3.setVisibility(View.GONE);
                    note4.setVisibility(View.GONE);
                    note5.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getBaseContext(), "Error occurred: numNotes error. numNotes = "
                            + numNotes, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                notesView.setVisibility(View.GONE);
            }
        }
    }
}

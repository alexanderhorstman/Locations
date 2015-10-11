package com.example.alexh.locations;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.util.List;

public class EditLocation extends FragmentActivity{

    boolean usesCurrentLocation = true;
    Holder viewHolder;
    Marker currentLocationMarker;
    String locationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_location);
        viewHolder = new Holder();
        Intent previousActivity = getIntent();
        locationType = previousActivity.getStringExtra("location type");
        if(locationType.equals("current location")) {
            try {
                usesCurrentLocation = true;
                setUpMapIfNeeded();
                viewHolder.mapView.setVisibility(View.VISIBLE);
                viewHolder.addressView.setVisibility(View.GONE);
            }
            catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        else if(locationType.equals("new location")) {
            usesCurrentLocation = false;
            viewHolder.mapView.setVisibility(View.VISIBLE);
            viewHolder.addressView.setVisibility(View.VISIBLE);
        }
        //exit if the location type is not one of the two expected values
        else {
            Toast.makeText(this, "Error creating new location.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void addNote(View view) {
        if(viewHolder.note1Layout.getVisibility() != View.VISIBLE) {
            //open dialog box
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
            dialogBox.setTitle("New Note");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager= (InputMethodManager)
                                    EditLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            input.requestFocus();
            dialogBox.setView(input);
            dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().trim().equals("")) {
                        //set note text to the dialog box text
                        viewHolder.note1View.setText(input.getText().toString().trim());
                        //make note visible
                        viewHolder.note1Layout.setVisibility(View.VISIBLE);
                    }
                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            dialogBox.show();
        }
        else if(viewHolder.note2Layout.getVisibility() != View.VISIBLE) {
            //open dialog box
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
            dialogBox.setTitle("New Note");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager= (InputMethodManager)
                                    EditLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            input.requestFocus();
            dialogBox.setView(input);
            dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().trim().equals("")) {
                        //set note text to the dialog box text
                        viewHolder.note2View.setText(input.getText().toString());
                        //make note visible
                        viewHolder.note2Layout.setVisibility(View.VISIBLE);
                        viewHolder.titleBarLayout.requestFocus();
                    }
                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            dialogBox.show();
        }
        else if(viewHolder.note3Layout.getVisibility() != View.VISIBLE) {
            //open dialog box
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
            dialogBox.setTitle("New Note");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager= (InputMethodManager)
                                    EditLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            input.requestFocus();
            dialogBox.setView(input);
            dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().trim().equals("")) {
                        //set note text to the dialog box text
                        viewHolder.note3View.setText(input.getText().toString());
                        //make note visible
                        viewHolder.note3Layout.setVisibility(View.VISIBLE);
                        viewHolder.titleBarLayout.requestFocus();
                    }

                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            dialogBox.show();
        }
        else if(viewHolder.note4Layout.getVisibility() != View.VISIBLE) {
            //open dialog box
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
            dialogBox.setTitle("New Note");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager= (InputMethodManager)
                                    EditLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            input.requestFocus();
            dialogBox.setView(input);
            dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().trim().equals("")) {
                        //set note text to the dialog box text
                        viewHolder.note4View.setText(input.getText().toString());
                        //make note visible
                        viewHolder.note4Layout.setVisibility(View.VISIBLE);
                        viewHolder.titleBarLayout.requestFocus();
                    }

                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            dialogBox.show();
        }
        else if(viewHolder.note5Layout.getVisibility() != View.VISIBLE) {
            //open dialog box
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
            dialogBox.setTitle("New Note");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager= (InputMethodManager)
                                    EditLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            input.requestFocus();
            dialogBox.setView(input);
            dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().trim().equals("")) {
                        //set note text to the dialog box text
                        viewHolder.note5View.setText(input.getText().toString());
                        //make note visible
                        viewHolder.note5Layout.setVisibility(View.VISIBLE);
                        viewHolder.noteAddButton.setVisibility(View.GONE);
                        viewHolder.titleBarLayout.requestFocus();
                    }

                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            dialogBox.show();
        }
    }

    public void deleteNote(View view) {
        int buttonId = view.getId();
        switch (buttonId) {
            case R.id.deleteNote1Button:
                //move the other notes up in the line
                if(viewHolder.note2Layout.getVisibility() == View.VISIBLE) {
                    viewHolder.note1View.setText(viewHolder.note2View.getText().toString());
                    if(viewHolder.note3Layout.getVisibility() == View.VISIBLE) {
                        viewHolder.note2View.setText(viewHolder.note3View.getText().toString());
                        if(viewHolder.note4Layout.getVisibility() == View.VISIBLE) {
                            viewHolder.note3View.setText(viewHolder.note4View.getText().toString());
                            if(viewHolder.note5Layout.getVisibility() == View.VISIBLE) {
                                viewHolder.note4View.setText(viewHolder.note5View.getText().toString());
                                viewHolder.note5Layout.setVisibility(View.GONE);
                            }
                            else {
                                viewHolder.note4Layout.setVisibility(View.GONE);
                            }
                        }
                        else {
                            viewHolder.note3Layout.setVisibility(View.GONE);
                        }
                    }
                    else {
                        viewHolder.note2Layout.setVisibility(View.GONE);
                    }
                }
                else {
                    viewHolder.note1Layout.setVisibility(View.GONE);
                }
                break;
            case R.id.deleteNote2Button:
                if(viewHolder.note3Layout.getVisibility() == View.VISIBLE) {
                    viewHolder.note2View.setText(viewHolder.note3View.getText().toString());
                    if(viewHolder.note4Layout.getVisibility() == View.VISIBLE) {
                        viewHolder.note3View.setText(viewHolder.note4View.getText().toString());
                        if(viewHolder.note5Layout.getVisibility() == View.VISIBLE) {
                            viewHolder.note4View.setText(viewHolder.note5View.getText().toString());
                            viewHolder.note5Layout.setVisibility(View.GONE);
                        }
                        else {
                            viewHolder.note4Layout.setVisibility(View.GONE);
                        }
                    }
                    else {
                        viewHolder.note3Layout.setVisibility(View.GONE);
                    }
                }
                else {
                    viewHolder.note2Layout.setVisibility(View.GONE);
                }
                break;
            case R.id.deleteNote3Button:
                if(viewHolder.note4Layout.getVisibility() == View.VISIBLE) {
                    viewHolder.note3View.setText(viewHolder.note4View.getText().toString());
                    if(viewHolder.note5Layout.getVisibility() == View.VISIBLE) {
                        viewHolder.note4View.setText(viewHolder.note5View.getText().toString());
                        viewHolder.note5Layout.setVisibility(View.GONE);
                    }
                    else {
                        viewHolder.note4Layout.setVisibility(View.GONE);
                    }
                }
                else {
                    viewHolder.note3Layout.setVisibility(View.GONE);
                }
                break;
            case R.id.deleteNote4Button:
                if(viewHolder.note5Layout.getVisibility() == View.VISIBLE) {
                    viewHolder.note4View.setText(viewHolder.note5View.getText().toString());
                    viewHolder.note5Layout.setVisibility(View.GONE);
                }
                else {
                    viewHolder.note4Layout.setVisibility(View.GONE);
                }
                break;
            case R.id.deleteNote5Button:
                viewHolder.note5Layout.setVisibility(View.GONE);
                break;
            default:
                Toast.makeText(this, "Nothing happened.", Toast.LENGTH_SHORT).show();
        }
        viewHolder.noteAddButton.setVisibility(View.VISIBLE);

    }

    public void finishLocation(View view) {
        LocationItem newLocationItem;
        //check to make sure all fields are filled in
        //check for name field
        if(viewHolder.locationName.getText().toString().trim().equals("")) {
            Toast.makeText(this, "The location must have a name.", Toast.LENGTH_SHORT).show();
            return;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //check for address field if current location is not being used --------------------------review this for lat&long from address
        if(!usesCurrentLocation) {
            Geocoder geocoder = new Geocoder(this);
            List<Address> possibleAddresses;
            String address = viewHolder.address.getText().toString().trim();

            if(!Geocoder.isPresent()) {
                Toast.makeText(this, "There is no geocoder backend service available.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Geocoder is working correctly.", Toast.LENGTH_SHORT).show();
            }
            if(address.equals("")) {
                Toast.makeText(this, "The location must have an address.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                possibleAddresses = geocoder.getFromLocationName(address, 5);
                if(possibleAddresses == null) {
                    Toast.makeText(this, "The address given does not match any real address.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Address firstAddress = possibleAddresses.get(0);
                    LatLng addressLatLng = new LatLng(firstAddress.getLatitude(), firstAddress.getLongitude());
                    viewHolder.currentMarkerOptions.position(addressLatLng);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        //create location item
        String locationName = viewHolder.locationName.getText().toString().trim();
        if (usesCurrentLocation) {
            LatLng latLng = currentLocationMarker.getPosition();
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            newLocationItem = new LocationItem(locationName, longitude, latitude);
        }
        else {
            String address = viewHolder.address.getText().toString();
            newLocationItem = new LocationItem(locationName, address);
        }
        //add notes to item if it has notes
        if(viewHolder.note1Layout.getVisibility() == View.VISIBLE) {
            newLocationItem.addNote(viewHolder.note1View.getText().toString().trim());
        }
        if(viewHolder.note2Layout.getVisibility() == View.VISIBLE) {
            newLocationItem.addNote(viewHolder.note2View.getText().toString().trim());
        }
        if(viewHolder.note3Layout.getVisibility() == View.VISIBLE) {
            newLocationItem.addNote(viewHolder.note3View.getText().toString().trim());
        }
        if(viewHolder.note4Layout.getVisibility() == View.VISIBLE) {
            newLocationItem.addNote(viewHolder.note4View.getText().toString().trim());
        }
        if(viewHolder.note5Layout.getVisibility() == View.VISIBLE) {
            newLocationItem.addNote(viewHolder.note5View.getText().toString().trim());
        }
        //add new location to list
        Globals.locationArray.add(newLocationItem);
        //save new list
        saveLocationsList();
        //return to main activity
        finish();
    }

    public void refreshMap(View view) {
        if(locationType.equals("current location")) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(latitude, longitude)));
            viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
            viewHolder.currentMarkerOptions.position(new LatLng(latitude, longitude));
        }
        else if(locationType.equals("new location")) {
            Geocoder geocoder = new Geocoder(this);
            List<Address> possibleAddresses;
            String address = viewHolder.address.getText().toString().trim();

            //check to make sure Geocoder is available
            if(!Geocoder.isPresent()) {
                Toast.makeText(this, "There is no geocoder backend service available.", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(this, "Geocoder is working correctly.", Toast.LENGTH_SHORT).show();
            }
            //check to make sure there is an address being analyzed
            if(address.equals("")) {
                Toast.makeText(this, "The location must have an address.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                possibleAddresses = geocoder.getFromLocationName(address, 5);
                if(possibleAddresses == null) {
                    Toast.makeText(this, "The address given does not match any real address.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(possibleAddresses.get(0) == null) {
                    Toast.makeText(this, "The address given does not match any real address.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Address firstAddress = possibleAddresses.get(0);
                    LatLng addressLatLng = new LatLng(firstAddress.getLatitude(), firstAddress.getLongitude());
                    viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
                    viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
                    viewHolder.currentMarkerOptions = new MarkerOptions().position(addressLatLng).title("My Location");
                    currentLocationMarker = viewHolder.map.addMarker(viewHolder.currentMarkerOptions);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();
        viewHolder.map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(latitude, longitude)));
        viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
        viewHolder.currentMarkerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Location");
        currentLocationMarker = viewHolder.map.addMarker(viewHolder.currentMarkerOptions);
        viewHolder.map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                currentLocationMarker.setPosition(latLng);
            }
        });
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        while (viewHolder.map == null) {
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
        EditText locationName;
        EditText address;
        GoogleMap map;
        LinearLayout mapView;
        LinearLayout addressView;
        RelativeLayout titleBarLayout;
        TextView noteAddButton;
        TextView note1View;
        TextView note2View;
        TextView note3View;
        TextView note4View;
        TextView note5View;
        RelativeLayout note1Layout;
        RelativeLayout note2Layout;
        RelativeLayout note3Layout;
        RelativeLayout note4Layout;
        RelativeLayout note5Layout;
        MarkerOptions currentMarkerOptions;

        public Holder() {
            //initialize all of the views
            locationName = (EditText) findViewById(R.id.locationNameEditLocation);
            address = (EditText) findViewById(R.id.locationAddressEditLocation);
            mapView = (LinearLayout) findViewById(R.id.mapView);
            noteAddButton = (TextView) findViewById(R.id.noteAddButton);
            addressView = (LinearLayout) findViewById(R.id.addressView);
            note1View = (TextView) findViewById(R.id.note1EditLocation);
            note2View = (TextView) findViewById(R.id.note2EditLocation);
            note3View = (TextView) findViewById(R.id.note3EditLocation);
            note4View = (TextView) findViewById(R.id.note4EditLocation);
            note5View = (TextView) findViewById(R.id.note5EditLocation);
            note1Layout = (RelativeLayout) findViewById(R.id.note1Layout);
            note2Layout = (RelativeLayout) findViewById(R.id.note2Layout);
            note3Layout = (RelativeLayout) findViewById(R.id.note3Layout);
            note4Layout = (RelativeLayout) findViewById(R.id.note4Layout);
            note5Layout = (RelativeLayout) findViewById(R.id.note5Layout);
            titleBarLayout = (RelativeLayout) findViewById(R.id.titleBarEditLocation);
        }
    }
}
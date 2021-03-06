package com.example.alexh.locations.Activities;

import com.example.alexh.locations.Data.LocationItem;
import com.example.alexh.locations.Managers.ListManager;
import com.example.alexh.locations.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateLocation extends AppCompatActivity{

    boolean usesCurrentLocation = false;
    Holder viewHolder;
    Marker currentLocationMarker;
    List<String> notesToAdd = new ArrayList<>();
    ListManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_location);
        setAppBarColor();
        manager = ListManager.getManager(this);
        viewHolder = new Holder();
        setUpMapIfNeeded();
        viewHolder.mapView.setVisibility(View.VISIBLE);
        viewHolder.addressView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.finish_location) {
            finishLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNote(View view) {
        if(notesToAdd.size() < 5) {
            //open dialog
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
                                    CreateLocation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    //add note to list
                    if(!input.getText().toString().trim().equals("")) {
                        notesToAdd.add(input.getText().toString().trim());
                    }
                    //make new note visible
                    if(notesToAdd.size() == 1) {
                        viewHolder.note1Layout.setVisibility(View.VISIBLE);
                        viewHolder.note1View.setText(notesToAdd.get(0));
                    }
                    else if(notesToAdd.size() == 2) {
                        viewHolder.note2Layout.setVisibility(View.VISIBLE);
                        viewHolder.note2View.setText(notesToAdd.get(1));
                    }
                    else if(notesToAdd.size() == 3) {
                        viewHolder.note3Layout.setVisibility(View.VISIBLE);
                        viewHolder.note3View.setText(notesToAdd.get(2));
                    }
                    else if(notesToAdd.size() == 4) {
                        viewHolder.note4Layout.setVisibility(View.VISIBLE);
                        viewHolder.note4View.setText(notesToAdd.get(3));
                    }
                    else if(notesToAdd.size() == 5) {
                        viewHolder.note5Layout.setVisibility(View.VISIBLE);
                        viewHolder.note5View.setText(notesToAdd.get(4));
                        viewHolder.noteAddButton.setVisibility(View.GONE);
                    }

                }
            });
            dialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            dialogBox.show();
        }
    }

    public void deleteNote(View view) {
        int buttonId = view.getId();
        if(buttonId == R.id.deleteNote1Button) {
            notesToAdd.remove(0);
        }
        else if(buttonId == R.id.deleteNote2Button) {
            notesToAdd.remove(1);
        }
        else if(buttonId == R.id.deleteNote3Button) {
            notesToAdd.remove(2);
        }
        else if(buttonId == R.id.deleteNote4Button) {
            notesToAdd.remove(3);
        }
        else if(buttonId == R.id.deleteNote5Button) {
            notesToAdd.remove(4);
        }
        //hide all of the note views
        viewHolder.note1Layout.setVisibility(View.GONE);
        viewHolder.note2Layout.setVisibility(View.GONE);
        viewHolder.note3Layout.setVisibility(View.GONE);
        viewHolder.note4Layout.setVisibility(View.GONE);
        viewHolder.note5Layout.setVisibility(View.GONE);
        //make visible all the ones that are actually needed
        if(notesToAdd.size() > 0) {
            viewHolder.note1View.setText(notesToAdd.get(0));
            viewHolder.note1Layout.setVisibility(View.VISIBLE);
        }
        if(notesToAdd.size() > 1) {
            viewHolder.note2View.setText(notesToAdd.get(1));
            viewHolder.note2Layout.setVisibility(View.VISIBLE);
        }
        if(notesToAdd.size() > 2) {
            viewHolder.note3View.setText(notesToAdd.get(2));
            viewHolder.note3Layout.setVisibility(View.VISIBLE);
        }
        if(notesToAdd.size() > 3) {
            viewHolder.note4View.setText(notesToAdd.get(3));
            viewHolder.note4Layout.setVisibility(View.VISIBLE);
        }
        //should never execute, but included just in case
        if(notesToAdd.size() > 4) {
            viewHolder.note5View.setText(notesToAdd.get(4));
            viewHolder.note5Layout.setVisibility(View.VISIBLE);
        }
        viewHolder.noteAddButton.setVisibility(View.VISIBLE);
    }

    public void finishLocation() {
        LocationItem newLocationItem;
        //check to make sure all fields are filled in
        //check for name field
        if(viewHolder.locationName.getText().toString().trim().equals("")) {
            Toast.makeText(this, "The location must have a name.", Toast.LENGTH_SHORT).show();
            return;
        }
        //check for address field if current location is not being used
        if(!usesCurrentLocation) {
            Geocoder geocoder = new Geocoder(this);
            List<Address> possibleAddresses;
            String address = viewHolder.address.getText().toString().trim();

            if(!Geocoder.isPresent()) {
                Toast.makeText(this, "There is no geocoder backend service available.", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(this, "Geocoder is working correctly.", Toast.LENGTH_SHORT).show();
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
                    currentLocationMarker.setPosition(addressLatLng);
                }
            }
            catch (Exception e) {
                Toast.makeText(this, "Something is broken.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
        }
        //create location item
        String locationName = viewHolder.locationName.getText().toString().trim();
        LatLng latLng = currentLocationMarker.getPosition();
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        if (usesCurrentLocation) {
            newLocationItem = new LocationItem(locationName, latitude, longitude);
        }
        else {
            //add lat and long to item
            //change item to include lat and long with address
            String address = viewHolder.address.getText().toString();
            newLocationItem = new LocationItem(locationName, address, latitude, longitude);
        }
        //add notes to item if it has notes
        newLocationItem.addNotes(notesToAdd);
        //add new location to list
        manager.getMyLocations().add(newLocationItem);
        //save new list
        manager.saveMyLocations();
        //return to main activity
        finish();
    }

    public void refreshMap(View view) {
        if(viewHolder.address.getText().toString().contains("Lat:") && viewHolder.address.getText()
                .toString().contains("Long:")) {
            //move camera to center on the marker if there is one
            viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(currentLocationMarker.getPosition()));
            //update with current location
            /*
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
            viewHolder.currentMarkerOptions.position(new LatLng(latitude, longitude));
            currentLocationMarker.setPosition(new LatLng(latitude, longitude));
            */
        }
        else {
            usesCurrentLocation = false;
            //update with address
            Geocoder geocoder = new Geocoder(this);
            List<Address> possibleAddresses;
            String address = viewHolder.address.getText().toString().trim();
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

                    if(viewHolder.currentMarkerOptions == null) {
                        viewHolder.currentMarkerOptions = new MarkerOptions().position(addressLatLng).title("My Location");
                        currentLocationMarker = viewHolder.map.addMarker(viewHolder.currentMarkerOptions);
                    }
                    else {
                        viewHolder.currentMarkerOptions.position(addressLatLng);
                        currentLocationMarker.setPosition(addressLatLng);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //set marker location to current location
    private void setUpMap() {
        int fineLocationCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(fineLocationCheck == PackageManager.PERMISSION_DENIED && coarseLocationCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 12);
        }
        else if(fineLocationCheck == PackageManager.PERMISSION_GRANTED && coarseLocationCheck == PackageManager.PERMISSION_GRANTED) {
            setUpMapWithCurrentLocation();
        }
    }

    private void setUpMapWithCurrentLocation() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            viewHolder.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(latitude, longitude)));
            viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(12));
            viewHolder.transparentImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(true);
                            // Disable touch on transparent view
                            return false;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(false);
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(true);
                            return false;

                        default:
                            return true;
                    }
                }
            });
        }
        catch (SecurityException e) {
            Toast.makeText(this, "Could not use current location. " +
                    "Please use an address to make a new location.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case 12: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpMapWithCurrentLocation();
                }
                else {
                    Toast.makeText(getBaseContext(), "Could not use current location. " +
                            "Please use an address to make a new location.", Toast.LENGTH_LONG).show();
                    viewHolder.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    viewHolder.transparentImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int action = event.getAction();
                            switch (action) {
                                case MotionEvent.ACTION_DOWN:
                                    // Disallow ScrollView to intercept touch events.
                                    viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(true);
                                    // Disable touch on transparent view
                                    return false;

                                case MotionEvent.ACTION_UP:
                                    // Allow ScrollView to intercept touch events.
                                    viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(false);
                                    return true;

                                case MotionEvent.ACTION_MOVE:
                                    viewHolder.mainScrollView.requestDisallowInterceptTouchEvent(true);
                                    return false;

                                default:
                                    return true;
                            }
                        }
                    });
                }
            }
        }
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

    private void setAppBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getSupportActionBar().setTitle("Create Location");
    }

    public void useCurrentLocation(View view) {
        usesCurrentLocation = true;
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location currentLocation;
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            final LatLng addressLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            if(viewHolder.currentMarkerOptions == null) {
                viewHolder.currentMarkerOptions = new MarkerOptions().position(addressLatLng).title("My Location");
                currentLocationMarker = viewHolder.map.addMarker(viewHolder.currentMarkerOptions);
            }
            else {
                viewHolder.currentMarkerOptions.position(addressLatLng);
                currentLocationMarker.setPosition(addressLatLng);
            }
            viewHolder.map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    DecimalFormat latLngFormat = new DecimalFormat("#.000");
                    currentLocationMarker.setPosition(latLng);
                    String latitude = latLngFormat.format(latLng.latitude);
                    String longitude = latLngFormat.format(latLng.longitude);
                    viewHolder.address.setText("Lat: " + latitude + " Long: " + longitude);
                }
            });
            viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
            viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
            DecimalFormat latLngFormat = new DecimalFormat("#.000");
            String lat = latLngFormat.format(latitude);
            String lon = latLngFormat.format(longitude);
            viewHolder.address.setText("Lat: " + lat + " Long: " + lon);
        }
        catch(SecurityException f) {
            Toast.makeText(this, "Unable to get current location. Make sure GPS is enabled for this app.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private class Holder {
        EditText locationName;
        EditText address;
        GoogleMap map;
        LinearLayout mapView;
        LinearLayout addressView;
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
        ImageView transparentImage;
        ScrollView mainScrollView;

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
            transparentImage = (ImageView) findViewById(R.id.transparent_image);
            mainScrollView = (ScrollView) findViewById(R.id.mainLocationInfo);
        }
    }
}
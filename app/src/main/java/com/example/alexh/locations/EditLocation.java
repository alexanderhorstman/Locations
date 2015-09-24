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
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class EditLocation extends FragmentActivity{

    boolean usesCurrentLocation = true;
    Holder viewHolder;
    DatePickerFragment datePickerFragment;
    TimePickerFragment timePickerFragment;
    Marker currentLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_location);
        viewHolder = new Holder();
        Intent previousActivity = getIntent();
        String locationType = previousActivity.getStringExtra("location type");
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
            viewHolder.mapView.setVisibility(View.GONE);
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
                    //set note text to the dialog box text
                    viewHolder.note1View.setText(input.getText().toString());
                    //make note visible
                    viewHolder.note1Layout.setVisibility(View.VISIBLE);
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
                    //set note text to the dialog box text
                    viewHolder.note2View.setText(input.getText().toString());
                    //make note visible
                    viewHolder.note2Layout.setVisibility(View.VISIBLE);
                    viewHolder.titleBarLayout.requestFocus();
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
                    //set note text to the dialog box text
                    viewHolder.note3View.setText(input.getText().toString());
                    //make note visible
                    viewHolder.note3Layout.setVisibility(View.VISIBLE);
                    viewHolder.titleBarLayout.requestFocus();
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
                    //set note text to the dialog box text
                    viewHolder.note4View.setText(input.getText().toString());
                    //make note visible
                    viewHolder.note4Layout.setVisibility(View.VISIBLE);
                    viewHolder.titleBarLayout.requestFocus();
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
                    //set note text to the dialog box text
                    viewHolder.note5View.setText(input.getText().toString());
                    //make note visible
                    viewHolder.note5Layout.setVisibility(View.VISIBLE);
                    viewHolder.noteAddButton.setVisibility(View.GONE);
                    viewHolder.titleBarLayout.requestFocus();
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

    //fires when date picker button is clicked
    public void chooseReminderDay(View view) {
        //creates a new date picker dialog
        datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    //fires when time picker button is clicked
    public void chooseReminderTime(View view) {
        //creates a new time picker dialog
        timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getFragmentManager(), "timePicker");
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
        //check for address field if current location is not being used
        if(!usesCurrentLocation) {
            Geocoder geocoder = new Geocoder(this);
            List<Address> address;

            if(viewHolder.address.getText().toString().trim().equals("")) {
                Toast.makeText(this, "The location must have an address.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                address = geocoder.getFromLocationName(viewHolder.locationName.getText().toString().trim(), 5);
                if(address == null) {
                    Toast.makeText(this, "The address given does not match any real address.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        //check for reminder message, time, and date if reminder is enabled
        if(viewHolder.reminderCheckbox.isChecked()) {
            if(viewHolder.reminderText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Reminder message cannot be blank if a reminder is enabled.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else if(viewHolder.timeButton.getText().equals("Time")) {
                Toast.makeText(this, "Reminder time cannot be blank if a reminder is enabled.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else if(viewHolder.dateButton.getText().equals("Date")) {
                Toast.makeText(this, "Reminder date cannot be blank if a reminder is enabled.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //create location item
        String locationName = viewHolder.locationName.getText().toString().trim();
        boolean hasReminder = viewHolder.reminderCheckbox.isChecked();
        if(usesCurrentLocation && hasReminder) {
            String message = viewHolder.reminderText.getText().toString().trim();
            Time time = timePickerFragment.getTime();
            Date date = datePickerFragment.getDate();
            LatLng latLng = currentLocationMarker.getPosition();
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            newLocationItem = new LocationItem(locationName, longitude, latitude, message, time, date);
        }
        else if (usesCurrentLocation && !hasReminder) {
            LatLng latLng = currentLocationMarker.getPosition();
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            newLocationItem = new LocationItem(locationName, longitude, latitude);
        }
        else if(!usesCurrentLocation && hasReminder) {
            String address = viewHolder.address.getText().toString();
            String message = viewHolder.reminderText.getText().toString().trim();
            Time time = timePickerFragment.getTime();
            Date date = datePickerFragment.getDate();
            newLocationItem = new LocationItem(locationName, address, message, time, date);
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
        currentLocationMarker = viewHolder.map.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("My Location"));
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

    //fired when the reminder check box is clicked
    //will toggle back and forth between having and not having a reminder
    public void toggleReminderStatus(View view) {
        //if the two reminder buttons are grayed out and unavailable
        if(viewHolder.reminderCheckbox.isChecked()) {
            //sets their colors to black
            viewHolder.timeButton.setTextColor(Color.BLACK);
            viewHolder.dateButton.setTextColor(Color.BLACK);
            viewHolder.timeButton.setBackgroundColor(getResources().getColor(R.color.appBlue));
            viewHolder.dateButton.setBackgroundColor(getResources().getColor(R.color.appBlue));
            //makes them clickable
            viewHolder.timeButton.setClickable(true);
            viewHolder.dateButton.setClickable(true);
            viewHolder.reminderText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        }
        //if the two reminder buttons are black and available
        else {
            //sets their colors to gray
            viewHolder.timeButton.setTextColor(Color.DKGRAY);
            viewHolder.dateButton.setTextColor(Color.DKGRAY);
            viewHolder.timeButton.setBackgroundColor(Color.GRAY);
            viewHolder.dateButton.setBackgroundColor(Color.GRAY);
            //makes them unclickable
            viewHolder.timeButton.setClickable(false);
            viewHolder.dateButton.setClickable(false);
            viewHolder.reminderText.setInputType(InputType.TYPE_NULL);
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
        CheckBox reminderCheckbox;
        Button timeButton;
        Button dateButton;
        EditText reminderText;

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
            reminderCheckbox = (CheckBox) findViewById(R.id.includeReminderCheckbox);
            timeButton = (Button) findViewById(R.id.reminderTimePickerButton);
            dateButton = (Button) findViewById(R.id.reminderDayPickerButton);
            reminderText = (EditText) findViewById(R.id.reminderEditTextEditLocation);
            //set the buttons to their default status
            timeButton.setTextColor(Color.DKGRAY);
            dateButton.setTextColor(Color.DKGRAY);
            timeButton.setBackgroundColor(Color.GRAY);
            dateButton.setBackgroundColor(Color.GRAY);
            timeButton.setClickable(false);
            dateButton.setClickable(false);
            //set reminder edit text box to default status
            reminderText.setInputType(InputType.TYPE_NULL);
        }
    }
}
package com.example.alexh.locations.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexh.locations.Data.LocationArray;
import com.example.alexh.locations.Data.LocationItem;
import com.example.alexh.locations.Data.SharedLocation;
import com.example.alexh.locations.Managers.ListManager;
import com.example.alexh.locations.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ObjectOutputStream;
import java.util.List;

public class ViewSharedLocation extends AppCompatActivity {

    private int index;
    private Holder viewHolder;
    private SharedLocation item;
    private LocationArray locationArray;
    private Context context;

    //fired when the back button is pressed
    @Override
    public void onBackPressed() {
        //leave this activity
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_shared_location);
        setAppBarColor();
        context = this;
        Intent intent = getIntent();
        index = intent.getIntExtra("position", 0);
        locationArray = ListManager.getManager(this).getSharedLocations();
        item = (SharedLocation) locationArray.get(index);
        viewHolder = new Holder();

        //move to initialize() method
        if(item.getAddress().equals("")) {
            viewHolder.addressView.setVisibility(View.GONE);
        }
        else {
            viewHolder.addressText.setText(item.getAddress());
        }
        viewHolder.senderText.setText(item.getSender().replace(',', '.'));
        setUpMapIfNeeded();
        viewHolder.locationName.setText(item.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.route_location_settings) {
            //start an intent to run navigation in Google Maps
            double latitude = this.item.getLatitude();
            double longitude = this.item.getLongitude();
            Uri uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mapIntent.setPackage("com.google.android.apps.maps");
            this.startActivity(mapIntent);
            return true;
        }
        else if(id == R.id.edit_location_settings) {
            Toast.makeText(this, "Not yet implemented.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.share_location_settings) {
            //start share location activity
            Intent intent = new Intent(context, ShareLocation.class);
            intent.putExtra("item", this.item);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.delete_location_settings) {
            deleteLocation();
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteLocation() {
        //get verification using a dialog box
        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
        dialogBox.setTitle("Confirmation");
        final TextView text = new TextView(this);
        text.setText("Do you want to delete the location \"" + item.getName() + "\"?");
        text.setPadding(15, 0, 0, 0);
        text.setTextSize(15);
        dialogBox.setView(text);
        dialogBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                locationArray.remove(index);
                saveSharedList();
                finish();
            }
        });
        dialogBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogBox.show();
    }

    //used to save the list to file
    public void saveSharedList() {
        //opens the list file and writes the list to it without append
        try{ObjectOutputStream listOutput =
                new ObjectOutputStream(openFileOutput(ListManager.getManager(this).getSharedLocationsFileName(), Context.MODE_PRIVATE));
            listOutput.writeObject(locationArray);
            listOutput.close();
        }
        catch(Exception e){e.printStackTrace();}
    }

    //set marker location to current location
    private void setUpMap() {
        double latitude = item.getLatitude();
        double longitude = item.getLongitude();
        viewHolder.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
            viewHolder.map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentShared))
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
        getSupportActionBar().setTitle("Location");
    }

    private class Holder {

        TextView locationName;
        LinearLayout addressView;
        TextView addressText;
        TextView senderText;
        LinearLayout mapView;
        LinearLayout notesView;
        TextView note1;
        TextView note2;
        TextView note3;
        TextView note4;
        TextView note5;
        GoogleMap map;

        public Holder() {
            locationName = (TextView) findViewById(R.id.locationNameViewSharedLocation);
            addressView = (LinearLayout) findViewById(R.id.addressViewSharedLocation);
            addressText = (TextView) findViewById(R.id.locationAddressViewSharedLocation);
            senderText = (TextView) findViewById(R.id.sourceViewSharedLocation);
            mapView = (LinearLayout) findViewById(R.id.mapViewSharedLocation);
            notesView = (LinearLayout) findViewById(R.id.notesLayoutViewSharedLocation);
            note1 = (TextView) findViewById(R.id.note1TextViewSharedLocation);
            note2 = (TextView) findViewById(R.id.note2TextViewSharedLocation);
            note3 = (TextView) findViewById(R.id.note3TextViewSharedLocation);
            note4 = (TextView) findViewById(R.id.note4TextViewSharedLocation);
            note5 = (TextView) findViewById(R.id.note5TextViewSharedLocation);

            if(item.hasNotes()) {
                Toast.makeText(context,"Has notes", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context,"Has no notes", Toast.LENGTH_SHORT).show();
                notesView.setVisibility(View.GONE);
            }
        }
    }
}

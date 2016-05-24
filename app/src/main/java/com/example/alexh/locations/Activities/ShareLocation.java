package com.example.alexh.locations.Activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexh.locations.Data.LocationItem;
import com.example.alexh.locations.Data.SharedLocation;
import com.example.alexh.locations.Managers.FirebaseManager;
import com.example.alexh.locations.Managers.UserManager;
import com.example.alexh.locations.R;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class ShareLocation extends AppCompatActivity {

    Holder viewHolder;
    LocationItem itemToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_location);
        Firebase.setAndroidContext(this);
        viewHolder = new Holder();
        setAppBarColor();
        Intent intent = getIntent();
        itemToShare = (LocationItem) intent.getSerializableExtra("item");
        setUpMapIfNeeded();
        viewHolder.locationName.setText(itemToShare.getName());
    }

    public void cancelShare(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void sendLocation(View view) {
        //create SharedLocation
        UserManager manager = UserManager.getManager(this);
        String myEmail = manager.getLastUser().getEmail();
        SharedLocation locationToSend = new SharedLocation(myEmail, itemToShare);
        //get firebase reference
        FirebaseManager firebaseManager = FirebaseManager.getManager();
        Firebase ref = firebaseManager.getReference("sharedLocations/" + viewHolder.sendToEmail.getText().toString() + "/");
        //write to firebase
        Map<String, Object> shared = new HashMap<>();
        shared.put(viewHolder.sendToEmail.getText().toString(), locationToSend);
        ref.push().setValue(shared);
    }


    private void setUpMap() {
        double latitude = itemToShare.getLatitude();
        double longitude = itemToShare.getLongitude();
        viewHolder.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        viewHolder.map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(latitude, longitude)));
        viewHolder.map.moveCamera(CameraUpdateFactory.zoomTo(15));
        viewHolder.map.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).title(itemToShare.getName()));
        viewHolder.map.setMyLocationEnabled(true);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (viewHolder.map == null) {
            // Try to obtain the map from the SupportMapFragment.
            FragmentManager support = getSupportFragmentManager();
            SupportMapFragment frag = (SupportMapFragment) support.findFragmentById(R.id.mapFragmentShareLocation);
            viewHolder.map = frag.getMap();
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
        getSupportActionBar().setTitle("Share Location");
    }


    private class Holder {
        EditText sendToEmail = (EditText) findViewById(R.id.sendToEmailShareLocation);
        TextView locationName = (TextView) findViewById(R.id.locationNameShareLocation);
        GoogleMap map;

    }
}

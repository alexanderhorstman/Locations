package com.example.alexh.locations.Activities;

import android.content.Context;
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
import android.widget.Toast;

import com.example.alexh.locations.Data.LocationItem;
import com.example.alexh.locations.Data.SharedLocation;
import com.example.alexh.locations.Data.User;
import com.example.alexh.locations.Managers.FirebaseManager;
import com.example.alexh.locations.Managers.UserManager;
import com.example.alexh.locations.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_location);
        context = this;
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
        final SharedLocation locationToSend = new SharedLocation(myEmail, itemToShare);
        //get firebase reference
        final FirebaseManager firebaseManager = FirebaseManager.getManager(getBaseContext());

        //write to firebase
        //check if target email is a valid user
        Firebase emailCheckRef = firebaseManager.getReference("users/" +
                viewHolder.sendToEmail.getText().toString().replace('.',',') + "/");
        emailCheckRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final Firebase ref = firebaseManager.getReference("sharedLocations/" +
                            viewHolder.sendToEmail.getText().toString().replace('.',',') + "/").push();
                    ref.setValue(locationToSend);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    //email does not exist
                    viewHolder.sendToEmail.setText("");
                    viewHolder.sendToEmail.setHint("This email does not belong to a user");
                    viewHolder.sendToEmail.setHintTextColor(ContextCompat.getColor(context, R.color.red));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
        EditText sendToEmail;
        TextView locationName;
        GoogleMap map;

        private Holder() {
            sendToEmail = (EditText) findViewById(R.id.sendToEmailShareLocation);
            locationName = (TextView) findViewById(R.id.locationNameShareLocation);
        }
    }
}

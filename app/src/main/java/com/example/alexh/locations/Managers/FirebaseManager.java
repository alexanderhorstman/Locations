package com.example.alexh.locations.Managers;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.example.alexh.locations.Data.LocationArray;
import com.example.alexh.locations.Data.SharedLocation;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseManager {

    private static FirebaseManager manager;
    private static Context context;

    Firebase ref;

    private FirebaseManager() {
        Firebase.setAndroidContext(context);
        ref = new Firebase("https://luminous-torch-8516.firebaseio.com/");
    }

    public static FirebaseManager getManager(Context con) {
        context = con;
        if(manager == null) {
            manager = new FirebaseManager();
        }
        return manager;
    }

    public Firebase getBaseReference() {
        return ref;
    }

    public Firebase getReference(String child) {
        return ref.child(child);
    }

    public void singleRead(String path) {

        final Firebase singleRef = ref.child(path);

        singleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()) {
                    SharedLocation sharedLocation = snap.getValue(SharedLocation.class);
                    LocationArray sharedList = ListManager.getManager(context).getSharedLocations();
                    sharedList.add(sharedLocation);
                    ListManager.getManager(context).saveSharedLocations();
                }
                singleRef.setValue(null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addListener(String path) {
        final Firebase listenerRef = ref.child(path);

        listenerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SharedLocation sharedLocation = dataSnapshot.getValue(SharedLocation.class);
                LocationArray sharedList = ListManager.getManager(context).getSharedLocations();
                sharedList.add(sharedLocation);
                listenerRef.setValue(null);
                ListManager.getManager(context).saveSharedLocations();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

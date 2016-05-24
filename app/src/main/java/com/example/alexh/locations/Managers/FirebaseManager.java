package com.example.alexh.locations.Managers;

import com.firebase.client.Firebase;

public class FirebaseManager {

    private static FirebaseManager manager;
    Firebase ref = new Firebase("https://luminous-torch-8516.firebaseio.com/");

    private FirebaseManager() {

    }

    public static FirebaseManager getManager() {
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
}

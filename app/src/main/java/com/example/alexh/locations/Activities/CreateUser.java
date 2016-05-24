package com.example.alexh.locations.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexh.locations.Data.User;
import com.example.alexh.locations.Managers.FirebaseManager;
import com.example.alexh.locations.Managers.UserManager;
import com.example.alexh.locations.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class CreateUser extends AppCompatActivity {

    Holder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        Firebase.setAndroidContext(this);
        viewHolder = new Holder();
        setAppBarColor();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void finishUser(View view) {
        String name = viewHolder.name.getText().toString();
        String email = viewHolder.email.getText().toString();
        String password = viewHolder.password.getText().toString();
        if(name.trim().equals("")) {
            Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(email.trim().equals("")) {
            Toast.makeText(this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(password.trim().equals("")) {
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
        }
        final String validEmail = email.replace('.',',');
        final User newUser = new User(name, email, password);
        Firebase firebaseRef = FirebaseManager.getManager().getReference("users/" + validEmail + "/");
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    FirebaseManager fireManager = FirebaseManager.getManager();
                    Firebase ref = fireManager.getReference("users/" + validEmail + "/");
                    ref.setValue(newUser);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(CreateUser.this, "This email is already used. Please use a different email.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void setAppBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getSupportActionBar().setTitle("Create Account");
    }

    private class Holder {
        private EditText name;
        private EditText email;
        private EditText password;

        private Holder() {
            name = (EditText) findViewById(R.id.createUserNameEditText);
            email = (EditText) findViewById(R.id.createUserEmailEditText);
            password = (EditText) findViewById(R.id.createUserPasswordEditText);
        }
    }
}

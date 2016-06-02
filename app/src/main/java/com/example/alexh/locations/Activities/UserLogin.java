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


public class UserLogin extends AppCompatActivity {

    public Holder viewHolder;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_log_in);
        context = this;
        Firebase.setAndroidContext(this);
        viewHolder = new Holder();
        setAppBarColor();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"You must login to use the app. Or create a new account.", Toast.LENGTH_SHORT).show();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateUser.class);
        startActivityForResult(intent, 0);
    }

    public void attemptSignIn(View view) {
        String email = viewHolder.email.getText().toString();
        final String password = viewHolder.password.getText().toString();

        final String validEmail = email.replace(".", ",");
        Firebase firebaseRef = FirebaseManager.getManager(context).getReference("users/" + validEmail + "/");
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getOuterClass(), "test", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.exists()) {
                    if (password.equals(dataSnapshot.child("password/").getValue())) {
                        String name = (String) dataSnapshot.child("name/").getValue();
                        User newUser = new User(name, validEmail, password);
                        UserManager manager = UserManager.getManager(context);
                        manager.saveLastUser(newUser);
                        Intent intent = new Intent();
                        intent.putExtra("user", newUser);
                        setResult(RESULT_OK, intent);
                        FirebaseManager.getManager(context).singleRead("sharedLocations/" + validEmail + "/");
                        FirebaseManager.getManager(context).addListener("sharedLocations/" + validEmail + "/");
                        finish();
                    } else {
                        //incorrect password
                        viewHolder.password.setText("");
                        viewHolder.password.setHint("Incorrect password");
                        viewHolder.password.setHintTextColor(ContextCompat.getColor(context, R.color.red));
                    }
                } else {
                    //incorrect email
                    viewHolder.email.setText("");
                    viewHolder.email.setHint("Incorrect email");
                    viewHolder.email.setHintTextColor(ContextCompat.getColor(context, R.color.red));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private UserLogin getOuterClass() {
        return this;
    }

    private void setAppBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getSupportActionBar().setTitle("Login");
    }

    private class Holder {
        private EditText email;
        private EditText password;

        private Holder() {
            email = (EditText) findViewById(R.id.userLoginEmailEditText);
            password = (EditText) findViewById(R.id.userLoginPasswordEditText);
            email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        email.setHint("");
                    }
                }
            });
            password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        password.setHint("");
                    }
                }
            });
        }
    }
}

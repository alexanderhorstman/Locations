package com.example.alexh.locations.Managers;

import android.content.Context;
import android.widget.Toast;

import com.example.alexh.locations.Data.LocationArray;
import com.example.alexh.locations.Data.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Alex H on 5/10/2016.
 */
public class UserManager {

    public static final int LOGIN_USER_ACTIVITY = 1;
    public static final int CREATE_USER_ACTIVITY = 2;

    private static UserManager manager;
    private String userFileName = "lastUser.data";
    private User lastUser;
    static Context context;


    private UserManager() {

    }

    static public UserManager getManager(Context con) {
        context = con;
        if(manager == null) {
            manager = new UserManager();
        }
        return manager;
    }

    public void readLastUser() {
        try {
            //creates object input stream using preset file name
            ObjectInputStream userInput = new ObjectInputStream(context.openFileInput(userFileName));
            //reads list in from file
            lastUser = (User) userInput.readObject();
            //closes file
            userInput.close();
        }
        //if the file was not found
        catch(FileNotFoundException e){
            //make a new file object using preset file name
            File listFile = new File(context.getFilesDir().getAbsolutePath(), userFileName);
            //try to create a new file, quit if system cannot create new file
            try{listFile.createNewFile();}
            catch(Exception f){}
        }
        catch(Exception f){
            f.printStackTrace();
        }
    }

    public User getLastUser() {
        return lastUser;
    }

    public boolean saveLastUser(User newLastUser) {
        try {
            ObjectOutputStream listOutput =
                    new ObjectOutputStream(context.openFileOutput(userFileName, Context.MODE_PRIVATE));
            listOutput.writeObject(newLastUser);
            listOutput.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

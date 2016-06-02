package com.example.alexh.locations.Managers;

import android.content.Context;
import android.widget.Toast;

import com.example.alexh.locations.Data.LocationArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListManager {

    static private ListManager manager;
    private LocationArray myLocations;
    private LocationArray sharedLocations;
    private String myLocationsFileName = "myloc.data";
    private String sharedLocationsFileName = "sharedloc.data";
    private static Context context;

    private ListManager() {
        loadSavedLists();
        if(myLocations == null) {
            myLocations = new LocationArray();
        }
        if(sharedLocations == null) {
            sharedLocations = new LocationArray();
        }
    }

    static public ListManager getManager(Context newContext) {
        if(manager == null) {
            manager = new ListManager();
        }
        context = newContext;
        return manager;
    }

    public LocationArray getSharedLocations() {
        return sharedLocations;
    }

    public void setSharedLocations(LocationArray sharedLocations) {
        this.sharedLocations = sharedLocations;
    }

    public LocationArray getMyLocations() {
        return myLocations;
    }

    public void setMyLocations(LocationArray myLocations) {
        this.myLocations = myLocations;
    }

    public String getMyLocationsFileName() {
        return myLocationsFileName;
    }

    public String getSharedLocationsFileName() {
        return sharedLocationsFileName;
    }

    public boolean loadSavedLists() {
        return readMyLocations() && readSharedLocations();
    }
    
    private boolean readMyLocations() {
        try {
            //creates object input stream using preset file name
            ObjectInputStream listInput = new ObjectInputStream(context.openFileInput(myLocationsFileName));
            //reads list in from file
            myLocations = (LocationArray) listInput.readObject();
            //closes file
            listInput.close();
        }
        //if the file was not found
        catch(FileNotFoundException e){
            //make a new file object using preset file name
            File listFile = new File(context.getFilesDir().getAbsolutePath(), myLocationsFileName);
            //try to create a new file, quit if system cannot create new file
            try{listFile.createNewFile();}
            catch(Exception f){}
        }
        catch(Exception f){
            f.printStackTrace();
        }
        return true;
    }

    private boolean readSharedLocations() {
        try {
            //creates object input stream using preset file name
            ObjectInputStream listInput = new ObjectInputStream(context.openFileInput(sharedLocationsFileName));
            //reads list in from file
            sharedLocations = (LocationArray) listInput.readObject();
            //closes file
            listInput.close();
        }
        //if the file was not found
        catch(FileNotFoundException e){
            //make a new file object using preset file name
            File listFile = new File(context.getFilesDir().getAbsolutePath(), sharedLocationsFileName);
            //try to create a new file, quit if system cannot create new file
            try{listFile.createNewFile();}
            catch(Exception f){
                return false;
                /*try {
                    wait(2000);
                }
                catch(Exception a){}*/
            }
        }
        catch(Exception f){
            f.printStackTrace();
        }
        return true;
    }

    public boolean saveMyLocations() {
        try {
            ObjectOutputStream listOutput =
                    new ObjectOutputStream(context.openFileOutput(myLocationsFileName, Context.MODE_PRIVATE));
            listOutput.writeObject(manager.getMyLocations());
            listOutput.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveSharedLocations() {
        try {
            ObjectOutputStream listOutput =
                    new ObjectOutputStream(context.openFileOutput(sharedLocationsFileName, Context.MODE_PRIVATE));
            listOutput.writeObject(manager.getSharedLocations());
            listOutput.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

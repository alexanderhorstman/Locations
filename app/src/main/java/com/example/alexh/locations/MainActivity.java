package com.example.alexh.locations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;


public class MainActivity extends Activity {

    ListViewAdapter adapter;
    ListView listView;
    Context context;

    //fires when the app returns to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //updates the adapter with the global list
        updateAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Globals.locationArray = new LocationArray();
        readSavedLocationsList();
        listView = (ListView) findViewById(R.id.mainListView);
        adapter = new ListViewAdapter(this, Globals.locationArray.toStringArray(), Globals.locationArray);
        listView.setAdapter(adapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout secondaryButtons = (LinearLayout) findViewById(R.id.secondaryButtons);
                Globals.locationArray.get(position).toggleSelected();
                if(Globals.locationArray.get(position).isSelected()) {
                    secondaryButtons.setVisibility(View.VISIBLE);
                }
                else {
                    secondaryButtons.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }

    public void addCurrentLocation(View view) {
        Intent intent = new Intent(this, EditLocation.class);
        intent.putExtra("location type", "current location");
        startActivity(intent);
    }

    public void addNewLocation(View view) {
        Intent intent = new Intent(this, EditLocation.class);
        intent.putExtra("location type", "new location");
        startActivity(intent);
    }

    //tries to read from the list file and assign that list to the global list (main list)
    private void readSavedLocationsList() {
        try {
            //creates object input stream using preset file name
            ObjectInputStream listInput = new ObjectInputStream(openFileInput(Globals.saveFileName));
            //reads list in from file
            Globals.locationArray = (LocationArray) listInput.readObject();
            //closes file
            listInput.close();
        }
        //if the file was not found
        catch(FileNotFoundException e){
            //make a new file object using preset file name
            File listFile = new File(this.getFilesDir().getAbsolutePath(), Globals.saveFileName);
            //try to create a new file, quit if system cannot create new file
            try{listFile.createNewFile();}
            catch(Exception f){Toast.makeText(this, "The list file could not be created. Shutting down.",
                    Toast.LENGTH_LONG).show();
                try {
                    wait(2000);
                }
                catch(Exception a){}
                finish();
            }
        }
        catch(Exception f){}
    }

    //updates the adapter with the newest version of the global list
    private void updateAdapter() {
        //updates the adapter with a new list
        adapter = new ListViewAdapter(getApplicationContext(),Globals.locationArray.toStringArray(), Globals.locationArray);
        //sets the updated adapter to the list view
        listView.setAdapter(adapter);
        //refreshes the list view
        listView.refreshDrawableState();
    }
}

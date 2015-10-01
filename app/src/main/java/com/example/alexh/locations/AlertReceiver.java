package com.example.alexh.locations;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.io.ObjectOutputStream;
import java.util.Random;

public class AlertReceiver extends BroadcastReceiver{

    Context context; //used to store the context of teh calling activity
    int priority; //used to determine the color of the icon on the notification panel

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //the type of alarm that is waking up the app
        String alarmType = intent.getStringExtra("alarm type");
        //alarm to add the daily tasks to the list
        if(alarmType.equals("add daily")) {
            addAllDailyTasks();
        }
        //alarm to make a notification to display
        else if(alarmType.equals("create notification")) {
            //get the description of the item from the intent
            String task = intent.getStringExtra("task");
            //get the priority of the item from the intent
            priority = intent.getIntExtra("priority", Item.PRIORITY_NORMAL);
            createNotification(context, "Checklist Reminder", task, "Checklist Reminder");
        }
    }

    private void addAllDailyTasks() {
        //try block will only work if the presets list has items in it
        try {
            for(int i = 0; i < Globals.presets.sizeOf(); i++) {
                //searches the list for the preset task
                if (!(Globals.list.contains(Globals.presets.getItem(i)))) {
                    //adds item if it was not already present
                    Globals.list.addItem(Globals.presets.getItem(i));
                }
            }
            //save list and update adapter
            saveList();
            updateAdapter();
        }
        //if no presets set, do nothing
        catch(Exception NulLPointerException) {}
    }

    private void createNotification(Context context, String message, String messageText,
                                    String messageAlert) {
        // Define an Intent and an action to perform with it by another application
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        // Builds a notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(message)
                        .setTicker(messageAlert)
                        .setContentText(messageText);

        //set the icon for the notification
        if(priority == Item.PRIORITY_NORMAL) {
            builder.setSmallIcon(R.drawable.normal_priority_reminder_icon);
        }
        else if(priority == Item.PRIORITY_MEDIUM) {
            builder.setSmallIcon(R.drawable.medium_priority_reminder_icon);
        }
        else if(priority == Item.PRIORITY_HIGH) {
            builder.setSmallIcon(R.drawable.high_priority_reminder_icon);
        }

        // Defines the Intent to fire when the notification is clicked
        builder.setContentIntent(notificIntent);

        // Set the default notification option
        builder.setDefaults(Notification.DEFAULT_ALL);

        // Auto cancels the notification when clicked on in the task bar
        builder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //get unique id for notification
        Random random = new Random();
        int id = random.nextInt();
        // Post the notification
        notificationManager.notify(id, builder.build());
    }

    public void saveList() {
        //opens the list file and writes the list to it without append
        try{ObjectOutputStream listOutput =
                new ObjectOutputStream(context.openFileOutput(Globals.listFileName, Context.MODE_PRIVATE));
            listOutput.writeObject(Globals.list);
            listOutput.close();
        }
        catch(Exception e){}
    }

    private void updateAdapter() {
        //get a view for the main activity with an inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_main, null);
        //get the ListView to update the list after the items are added
        ListView listView = (ListView) view.findViewById(R.id.mainListView);
        //updates the adapter with a new list
        MyAdapter adapter = new MyAdapter(context, Globals.list.getStringArray(), Globals.list);
        //sets the updated adapter to the list view
        listView.setAdapter(adapter);
        //refreshes the list view
        listView.refreshDrawableState();
    }
}

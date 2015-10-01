package com.example.alexh.locations;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //reset alarms for current items in list
            for(int i = 0; i < Globals.list.sizeOf(); i++) {
                //set reminder of item in list if it has one
                if(Globals.list.getItem(i).hasReminder()) {
                    Item item = Globals.list.getItem(i);
                    //set time for alarm
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, item.getTime().getHourOfDay());
                    calendar.set(Calendar.MINUTE, item.getTime().getMinute());
                    calendar.set(Calendar.MONTH, item.getDate().getMonth());
                    calendar.set(Calendar.DAY_OF_MONTH, item.getDate().getDay());
                    calendar.set(Calendar.YEAR, item.getDate().getYear());
                    //set alarm
                    AlarmManager alarmManager =
                            (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(context, AlertReceiver.class);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            PendingIntent.getBroadcast(context,
                                    item.getIdNumber(), alarmIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT));
                }
            }
            //reset alarm to add daily items at midnight
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //set time to midnight
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            AlarmManager alarmManager =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(context, AlertReceiver.class);
            alarmIntent.putExtra("alarm type", "add daily");
            //set alarm to trigger at midnight every day
            alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(context, 999, alarmIntent, 0));
            //add the daily items to the list if they are not already there
            for(int i = 0; i < Globals.presets.sizeOf(); i++) {
                //searches the list for the preset task
                if (!(Globals.list.contains(Globals.presets.getItem(i)))) {
                    //adds item if it was not already present
                    Globals.list.addItem(Globals.presets.getItem(i));
                }
            }
        }
        */
    }
}

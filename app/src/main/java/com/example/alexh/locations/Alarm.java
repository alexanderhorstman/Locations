package com.example.alexh.locations;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.Calendar;

public class Alarm implements Serializable{

    String message;
    Time time;
    Date date;


    public Alarm(String message, Time time, Date date) {
        this.message = message;
        this.time = time;
        this.date = date;
        //setAlarm();
    }

    public void cancelAlarm() {

    }

    public int getAlarmId() {
        if(date != null && time != null) {
            return date.getYear() * 100000000 + date.getMonth() * 1000000 + date.getDay() * 1000 +
                    time.getHourOfDay() * 100 + time.getMinute();
        }
        return 0;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public Time getTime() {
        return time;
    }

    public void setAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, time.getHourOfDay());
        calendar.set(Calendar.MINUTE, time.getMinute());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        calendar.set(Calendar.YEAR, date.getYear());
        //set alarm
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        //intent fires the AlertReceiver class
        Intent intent = new Intent(context, AlertReceiver.class);
        //sets alarm using new item's id number
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                PendingIntent.getBroadcast(context,
                        getAlarmId(), intent,
                        PendingIntent.FLAG_UPDATE_CURRENT));
    }

}

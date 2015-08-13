package com.example.alexh.locations;

public class Alarm {

    String message;
    Time time;
    Date date;


    public Alarm(String message, Time time, Date date) {
        this.message = message;
        this.time = time;
        this.date = date;
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

    public void setAlarm() {

    }

}

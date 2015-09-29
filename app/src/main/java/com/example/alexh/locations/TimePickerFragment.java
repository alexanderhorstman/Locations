package com.example.alexh.locations;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    private Time time = null; //the time that will be chosen; the default is one hour more than the
                              // current hour, and 0 minutes

    //fired when the fragment is created
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets the current time and sets the minute to 0
        final Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = 0;
        //gets the am status of the current time
        boolean isAm;
        if(calendar.get(Calendar.AM_PM) == Calendar.AM) {
            isAm = true;
        }
        else {
            isAm = false;
        }
        time = new Time(calendar.get(Calendar.HOUR), minute, isAm, hourOfDay);
        //the hour + 1 is the "one hour more than the current hour" part
        return new TimePickerDialog(getActivity(), this, (hourOfDay + 1), minute, false);
    }

    public Time getTime() {
        return time;
    }

    //fired when the time is chosen from the dialog
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        boolean isAm = true;
        int hour;
        //get the button from the time button's id
        //Button timeButton = (Button) getActivity().findViewById(R.id.reminderTimePickerButton);
        if(hourOfDay > 11) {
            isAm = false;
            //changes the hour to a 12 hour format if the time is in PM
            hour = hourOfDay - 12;
        }
        else {
            hour = hourOfDay;
        }
        //0 is the same as 12 o'clock
        //but 12 is normal to see whereas 0 is not
        if(hour == 0) {
            hour = 12;
        }
        time = new Time(hour, minute, isAm, hourOfDay);
        //set the button text to the new time
        //timeButton.setText(time.toString());
    }
}

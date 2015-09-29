package com.example.alexh.locations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private Date date = null; //the date that will be chosen; current day is the default

    //fired when the fragment is created
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = new Date(month, day, year);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public Date getDate() {
        return date;
    }

    //fired when a date is chosen from the dialog
    public void onDateSet(DatePicker view, int year, int month, int day) {
        date = new Date(month, day, year);
        //update button text
        //Button dayButton = (Button) getActivity().findViewById(R.id.reminderDayPickerButton);
        //dayButton.setText((month + 1) + "/" + day + "/" + year);
        //dayButton.setText(date.toString());
    }
}

package com.example.alexh.locations;

import java.io.Serializable;

public class Date implements Serializable {

    private int day; //day of the month; 1 - 31
    private int month; //month of the year; 0 - 11
    private int year; //year 0000 -9999

    //constructor takes date in format month, day, year
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    //returns true if the day, the month, and year are all equal
    public boolean equals(Date date) {
        if(day == date.getDay() && month == date.getMonth() && year == date.getYear()) {
            return true;
        }
        return false;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    //returns the date as a String in the form mm/dd/yyyy
    public String toString() {
        return (month + 1) + "/" + day + "/" + year;
    }
}

package com.example.alexh.locations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex H on 7/8/2015.
 */
public class LocationArray implements Serializable{

    public static final int SORT_ALPHABETICAL = 1;

    private List<LocationItem> list;
    private int sortType = SORT_ALPHABETICAL;

    //default constructor does nothing
    public LocationArray() {
        list = new ArrayList<>();
    }

    public void add(LocationItem newItem) {
        list.add(newItem);
        sort(sortType);
    }

    public LocationItem get(int index) {
        return list.get(index);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public int size() {
        return list.size();
    }

    public void sort(int sortType) {

    }

    public  String[] toStringArray() {
        String[] array = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getName();
        }
        return array;
    }
}

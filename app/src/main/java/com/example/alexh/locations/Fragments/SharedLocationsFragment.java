package com.example.alexh.locations.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.alexh.locations.Activities.ViewLocation;
import com.example.alexh.locations.Activities.ViewSharedLocation;
import com.example.alexh.locations.Adapters.ListViewAdapter;
import com.example.alexh.locations.Data.LocationArray;
import com.example.alexh.locations.Managers.ListManager;
import com.example.alexh.locations.R;

public class SharedLocationsFragment extends ListFragment implements AdapterView.OnItemClickListener  {

    ListManager manager = ListManager.getManager(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared, container, false);
        LocationArray sharedLocations = manager.getSharedLocations();
        int listSize = sharedLocations.size();
        String[] sharedNames = new String[listSize];
        for(int i = 0; i < listSize; i++) {
            sharedNames[i] = sharedLocations.get(i).getName();
        }
        ListViewAdapter adapter = new ListViewAdapter(getContext(), sharedNames, sharedLocations);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
        manager = ListManager.getManager(getContext());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        //launch view location activity
        Intent intent = new Intent(getContext(), ViewSharedLocation.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void updateAdapter() {
        LocationArray sharedLocations = manager.getSharedLocations();
        int listSize = sharedLocations.size();
        String[] sharedNames = new String[listSize];
        for(int i = 0; i < listSize; i++) {
            sharedNames[i] = sharedLocations.get(i).getName();
        }
        ListViewAdapter adapter = new ListViewAdapter(getContext(), sharedNames, sharedLocations);
        setListAdapter(adapter);
    }

}

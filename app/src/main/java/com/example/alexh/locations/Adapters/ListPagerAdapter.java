package com.example.alexh.locations.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alexh.locations.Fragments.MyLocationsFragment;
import com.example.alexh.locations.Fragments.SharedLocationsFragment;

/**
 * Created by Alex H on 4/29/2016.
 */
public class ListPagerAdapter extends FragmentPagerAdapter {

    private MyLocationsFragment myLocations;
    private SharedLocationsFragment sharedLocations;

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
        myLocations = new MyLocationsFragment();
        sharedLocations = new SharedLocationsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return myLocations;
        }
        else if(position == 1) {
            return sharedLocations;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Locations";
            case 1:
                return "Shared";
        }
        return null;
    }

    public void updatePage(int page, Context context) {
        if(page == 0) {
            myLocations.updateAdapter(context);
        }
        else {
            sharedLocations.updateAdapter();
        }
    }

}

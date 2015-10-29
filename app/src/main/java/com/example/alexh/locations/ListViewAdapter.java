package com.example.alexh.locations;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


class ListViewAdapter extends ArrayAdapter<String> {

    protected Context context;
    protected static Holder viewHolder;

    public ListViewAdapter(Context context, String[] names, LocationArray locationArray) {
        super(context, R.layout.main_list_layout, names);
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        viewHolder = new Holder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.main_list_layout, null);
            viewHolder.mainListLayout = (RelativeLayout) view.findViewById(R.id.mainListLayout);
            viewHolder.viewLocationImage = (ImageButton) view.findViewById(R.id.viewLocationImage);
            viewHolder.secondaryButtons = (LinearLayout) view.findViewById(R.id.secondaryButtons);
            viewHolder.textView = (TextView) view.findViewById(R.id.listText);
            viewHolder.routeButton = (TextView) view.findViewById(R.id.routeToLocationButton);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (Holder) view.getTag();
        }
        //getItem(int) is a method from the extended ArrayAdapter to get the String at the position
        String selectedItem = getItem(position);
        //sets the text of the item from getItem(int)
        viewHolder.textView.setText(selectedItem);
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets the visibility of the secondary buttons if the location item is selected
                if (viewHolder.secondaryButtons.getVisibility() == View.GONE) {
                    viewHolder.secondaryButtons.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.secondaryButtons.setVisibility(View.GONE);
                }
            }
        });
        viewHolder.viewLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start new activity
                Intent intent = new Intent(context, ViewLocation.class);
                intent.putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationItem item = Globals.locationArray.get(position);
                double latitude = item.getLatitude();
                double longitude = item.getLongitude();
                Uri uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        return view;
    }

    private class Holder {
        protected RelativeLayout mainListLayout;
        protected ImageButton viewLocationImage;
        protected LinearLayout secondaryButtons;
        protected TextView textView;
        protected TextView routeButton;
    }
}

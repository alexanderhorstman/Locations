package com.example.alexh.locations.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alexh.locations.Data.LocationArray;
import com.example.alexh.locations.Data.LocationItem;
import com.example.alexh.locations.R;
import com.example.alexh.locations.Activities.ViewLocation;


public class ListViewAdapter extends ArrayAdapter<String> {

    protected Context context;
    protected Holder viewHolder;
    protected LocationArray currentArray;

    public ListViewAdapter(Context context, String[] names, LocationArray locationArray) {
        super(context, R.layout.main_list_layout, names);
        this.context = context;
        currentArray = locationArray;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        viewHolder = new Holder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.main_list_layout, null);
            viewHolder.mainListLayout = (RelativeLayout) view.findViewById(R.id.mainListLayout);
            //viewHolder.viewLocationImage = (ImageButton) view.findViewById(R.id.viewLocationImage);
            viewHolder.textView = (TextView) view.findViewById(R.id.listText);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (Holder) view.getTag();
        }
        //getItem(int) is a method from the extended ArrayAdapter to get the String at the position
        String selectedItem = getItem(position);
        //sets the text of the item from getItem(int)
        viewHolder.textView.setText(selectedItem);
        /*viewHolder.viewLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start new activity
                Intent intent = new Intent(context, ViewLocation.class);
                intent.putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
        return view;
    }

    private class Holder {
        protected RelativeLayout mainListLayout;
        protected ImageButton viewLocationImage;
        protected TextView textView;
    }
}

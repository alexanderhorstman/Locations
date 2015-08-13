package com.example.alexh.locations;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


class ListViewAdapter extends ArrayAdapter<String> {

    protected Context context;

    public ListViewAdapter(Context context, String[] names, LocationArray locationArray) {
        super(context, R.layout.main_list_layout, names);
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        Holder viewHolder = new Holder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.main_list_layout, null);
            viewHolder.viewLocationImage = (ImageButton) view.findViewById(R.id.viewLocationImage);
            viewHolder.secondaryButtons = (LinearLayout) view.findViewById(R.id.secondaryButtons);
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
        //sets the visibility of the secondary buttons if the location item is selected
        /*
        if(position < Globals.locationArray.size() && Globals.locationArray.get(position).isSelected()) {
            viewHolder.secondaryButtons.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.secondaryButtons.setVisibility(View.GONE);
        }
        */
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
        return view;
    }

    private class Holder {
        protected ImageButton viewLocationImage;
        protected LinearLayout secondaryButtons;
        protected TextView textView;
    }
}

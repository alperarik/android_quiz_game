package com.example.alper_arik.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    //Constructor
    public CustomAdapter(Context context, String[] competitors) {
        super(context,R.layout.custom_row, competitors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //uses custom_row layout file
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        // passes listView item to custom textView
        String competitor = getItem(position);
        TextView customRowTextView = (TextView) customView.findViewById(R.id.customRowTextView);
        customRowTextView.setText(competitor);
        return  customView;
    }
}

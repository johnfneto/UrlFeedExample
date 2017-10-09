package com.optus.jneto.landmarksfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.optus.jneto.landmarksfeed.R;
import com.optus.jneto.landmarksfeed.models.Item;

import java.util.List;

/**
 * Created by jneto on 10/6/17.
 */

public class SpinnerAdapter extends ArrayAdapter<Item> {

    private Context context;
    List<Item> locations = null;

    public SpinnerAdapter(Context context, int textViewResourceId, List<Item> locations) {
        super(context, textViewResourceId, locations);
        this.context = context;
        this.locations = locations;
    }

    public int getCount(){
        return locations.size();
    }

    public Item getItem(int position){
        return locations.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.spinner_row, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.textView);
        textView.setText(locations.get(position).getName());

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.spinner_row, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.textView);
        textView.setText(locations.get(position).getName());

        return textView;
    }
}

package com.example.dmitry.musicdigger.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digger.dmitry.musicdigger.R;
import com.example.dmitry.musicdigger.webdigger.DigTag;

import java.util.List;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class TagsAdapter extends ArrayAdapter<DigTag> {

    public TagsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TagsAdapter(Context context, int resource, List<DigTag> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemtagrow, null);
        }

        DigTag p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tag);

            if (tt1 != null) {
                tt1.setText(p.toString());
            }

        }

        return v;
    }

}

package com.example.dmitry.musicdigger.gui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digger.dmitry.musicdigger.R;
import com.example.dmitry.musicdigger.player.PlaylistItem;

import java.util.List;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class PlaylistItemAdapter extends ArrayAdapter<PlaylistItem> {

    public PlaylistItemAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PlaylistItemAdapter(Context context, int resource, List<PlaylistItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.playlistitem, null);
        }

        PlaylistItem p = getItem(position);

        if (p != null) {
            TextView durationTxt = (TextView) v.findViewById(R.id.durationLbl);
            TextView artistTxt = (TextView) v.findViewById(R.id.artistLbl);
            TextView titleTxt = (TextView) v.findViewById(R.id.titleLbl);
            LinearLayout base =(LinearLayout)v.findViewById(R.id.currentPlayingBase);

            if(p.isPlayng())
            {
                base.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));//
            }
            else
            {
                base.setBackgroundColor(Color.WHITE);
            }

            durationTxt.setText(DateUtils.formatElapsedTime(p.getDuration()));
            artistTxt.setText(p.getArtist());
            titleTxt.setText(p.getTitle());
            if(parent.isSelected())
            {
                titleTxt.setTextColor(24);
            }
        }

        return v;
    }

}

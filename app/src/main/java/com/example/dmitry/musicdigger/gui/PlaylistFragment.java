package com.example.dmitry.musicdigger.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dmitry.musicdigger.R;
import com.example.dmitry.musicdigger.player.DiggerPlayer;

/**
 * Created by Dmitry on 15/05/2016.
 */
public class PlaylistFragment extends Fragment {



    public PlaylistFragment() {

        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        ListView playlistView = (ListView) rootView.findViewById(R.id.playlistView);
        PlaylistItemAdapter adapter = new PlaylistItemAdapter(getContext(),R.layout.playlistitem,  DiggerPlayer.getPlaylist());
        playlistView.setAdapter(adapter);

        return rootView;
    }

}

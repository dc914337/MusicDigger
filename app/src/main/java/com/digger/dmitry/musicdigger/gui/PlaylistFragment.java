package com.digger.dmitry.musicdigger.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.digger.dmitry.musicdigger.R;
import com.digger.dmitry.musicdigger.player.DiggerPlayer;

/**
 * Created by Dmitry on 15/05/2016.
 */
public class PlaylistFragment extends Fragment {

    ListView playlistView;
    View rootView;
    PlaylistItemAdapter adapter;

    public PlaylistFragment() {

        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        playlistView = (ListView) rootView.findViewById(R.id.playlistView);
        adapter = new PlaylistItemAdapter(getContext(),R.layout.playlistitem,  DiggerPlayer.getPlaylist());
        playlistView.setAdapter(adapter);

        playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
              DiggerPlayer.setPlaying(position);
                if(adapter!=null)
                    adapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }


    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if(adapter!=null)
                adapter.notifyDataSetChanged();

           final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        }
    }




}

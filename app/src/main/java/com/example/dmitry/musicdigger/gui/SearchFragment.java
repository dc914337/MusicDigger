package com.example.dmitry.musicdigger.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dmitry.musicdigger.R;
import com.example.dmitry.musicdigger.player.DiggerPlayer;
import com.example.dmitry.musicdigger.player.PlaylistItem;
import com.example.dmitry.musicdigger.webdigger.DigRequestConstructor;
import com.example.dmitry.musicdigger.webdigger.DigTag;
import com.example.dmitry.musicdigger.webdigger.VKAudio;
import com.example.dmitry.musicdigger.webdigger.WebDiggerApi;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dmitry on 15/05/2016.
 */
public class SearchFragment extends Fragment {

    DigRequestConstructor digReqConstructor;

    EditText tagSearchTextBox;
    ListView tagsListView;
    TextView tagsView;
    TagsAdapter adapter;
    Button addAndBtn;
    Button removeBtn;
    Button addNotBtn;
    FloatingActionButton searchBtn;



    ArrayList<DigTag> showingTags;

    public SearchFragment() {

    }


    private void initControls(View rootView)
    {
        tagsListView = (ListView) rootView.findViewById(R.id.tagsListView);
        adapter = new TagsAdapter(getContext(),android.R.layout.simple_list_item_1, showingTags);
        tagsListView.setAdapter(adapter);

        tagSearchTextBox=(EditText) rootView.findViewById(R.id.tagSearchTextBox);
        tagsView=(TextView)rootView.findViewById(R.id.tagsView);

        addAndBtn= ((Button) rootView.findViewById(R.id.addAndButton));
        addAndBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.addAnd();
                builderChanged();
            }
        });

        addNotBtn=((Button) rootView.findViewById(R.id.addNotButton));
        addNotBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.addNot();
                builderChanged();
            }
        });

        removeBtn=((Button) rootView.findViewById(R.id.removeButton));
        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.removeLast();
                builderChanged();
            }
        });

        searchBtn=((FloatingActionButton) rootView.findViewById(R.id.searchButton));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            WebDiggerApi api=new WebDiggerApi();
                            ArrayList<VKAudio> res= api.getAudios(digReqConstructor.getUri());

                            if(res ==null || res.size()==0)
                            {
                                Snackbar.make(v, "Fuck you!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else
                            {
                                Snackbar.make(v, String.format("Yay! %s songs found",res.size()), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                for(VKAudio song: res)
                                {
                                    DiggerPlayer.addToPlaylist( new PlaylistItem(song.getArtist(),song.getTitle(),song.getDuration(),song.getUrl()));
                                }
                                DiggerPlayer.start(getContext());
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                });

                thread.start();

            }
        });

        tagsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                digReqConstructor.addTag((DigTag) parent.getItemAtPosition(position));
                builderChanged();
            }
        });


        tagSearchTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

        }
        });

    }

    private void builderChanged()
    {
        addAndBtn.setEnabled(digReqConstructor.canAnd());
        removeBtn.setEnabled(digReqConstructor.canRemove());
        addNotBtn.setEnabled(digReqConstructor.canNot());
        tagsListView.setEnabled(digReqConstructor.canAddTag());
        tagsView.setText(digReqConstructor.toString());
    }


    private void searchText(String searchingTag)
    {
        showingTags.clear();
        for(DigTag tag : digReqConstructor.getAllTags())
        {
            if(tag.toString().contains(searchingTag) || searchingTag.equals(""))
            {
                showingTags.add(tag);
            }
        }

       adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        digReqConstructor=new DigRequestConstructor(getContext());
        View rootView=inflater.inflate(R.layout.fragment_search, container, false);
        showingTags = new ArrayList<DigTag>(Arrays.asList(digReqConstructor.getAllTags()));

        initControls(rootView);


        return rootView;
    }


}

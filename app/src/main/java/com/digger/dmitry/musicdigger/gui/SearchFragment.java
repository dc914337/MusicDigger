package com.digger.dmitry.musicdigger.gui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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

import com.digger.dmitry.musicdigger.R;
import com.digger.dmitry.musicdigger.player.DiggerPlayer;
import com.digger.dmitry.musicdigger.player.PlaylistItem;
import com.digger.dmitry.musicdigger.webdigger.DigRequestConstructor;
import com.digger.dmitry.musicdigger.webdigger.DigTag;
import com.digger.dmitry.musicdigger.webdigger.VKAudio;
import com.digger.dmitry.musicdigger.webdigger.WebDiggerApi;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dmitry on 15/05/2016.
 */
public class SearchFragment extends Fragment {

    DigRequestConstructor digReqConstructor;

    EditText tagSearchTextBox;
    ListView tagsListView;
    TextView includedTagsView;
    TextView excludedTagsView;

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
        includedTagsView=(TextView)rootView.findViewById(R.id.includedTagsView);
        excludedTagsView=(TextView)rootView.findViewById(R.id.excludedTagsView);

        //addAndBtn= ((Button) rootView.findViewById(R.id.addAndButton));
/*        addAndBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.addAnd();
                builderChanged();
            }
        });*/

        //addNotBtn=((Button) rootView.findViewById(R.id.addNotButton));
       /* addNotBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.addNot();
                builderChanged();
            }
        });

        //removeBtn=((Button) rootView.findViewById(R.id.removeButton));
        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                digReqConstructor.removeLast();
                builderChanged();
            }
        });*/

        searchBtn=((FloatingActionButton) rootView.findViewById(R.id.searchButton));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                final String uri=digReqConstructor.getUri();
                digReqConstructor=new DigRequestConstructor(getContext());
                DiggerPlayer.clearPlaylist();



                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Snackbar wait= Snackbar.make(v, "Searching audios. Please stand by...", Snackbar.LENGTH_INDEFINITE);
                            wait.setAction("Action", null).show();

                            WebDiggerApi api = new WebDiggerApi();
                            ArrayList<VKAudio> res = api.getAudios(uri);

                            wait.dismiss();
                            if (res == null || res.size() == 0) {
                                Snackbar.make(v, "Nothing found!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                builderChanged();
                            } else {


                                for (VKAudio song : res) {
                                    DiggerPlayer.addToPlaylist(new PlaylistItem(song.getArtist(), song.getTitle(), song.getDuration(), song.getUrl()));
                                }

                                DiggerPlayer.start(getContext());


                                Handler mainThread = new Handler(Looper.getMainLooper());
                                mainThread.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        builderChanged();
                                        TabLayout tabLayout = (TabLayout)getActivity().findViewById(R.id.tabs);
                                        TabLayout.Tab tab = tabLayout.getTabAt(2);
                                        tab.select();

                                    }
                                });

                                Snackbar.make(v, String.format("Yay! %s songs found", res.size()), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        } catch (Exception e) {
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
                digReqConstructor.addIncludeTag((DigTag) parent.getItemAtPosition(position));
                builderChanged();
                tagSearchTextBox.setText("");

            }
        });

        tagsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                digReqConstructor.addExcludeTag((DigTag) parent.getItemAtPosition(position));
                builderChanged();
                tagSearchTextBox.setText("");
                return true;
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
        //addAndBtn.setEnabled(digReqConstructor.canAnd());
        //removeBtn.setEnabled(digReqConstructor.canRemove());
       // addNotBtn.setEnabled(digReqConstructor.canNot());
        //tagsListView.setEnabled(digReqConstructor.canAddTag());
        includedTagsView.setText(digReqConstructor.includedToString());
        excludedTagsView.setText(digReqConstructor.excludedToString());
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

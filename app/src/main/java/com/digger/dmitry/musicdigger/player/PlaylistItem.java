package com.digger.dmitry.musicdigger.player;

import android.net.Uri;

/**
 * Created by Dmitry on 18/05/2016.
 */
public class PlaylistItem {
    private String artist;
    private String title;
    private int duration;
    private Uri url;
    private boolean playing;



    public PlaylistItem(String artist, String title, int duration, String url) {
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.url = Uri.parse(url);
    }

    public void setPlaying(boolean isPlaying)
    {
        playing=isPlaying;
    }

    public boolean isPlayng()
    {
        return playing;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public Uri getUrl() {
        return url;
    }
}

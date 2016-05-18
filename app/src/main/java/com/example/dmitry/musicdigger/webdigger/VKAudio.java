package com.example.dmitry.musicdigger.webdigger;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class VKAudio {
    private int aid;
    private int owner_id;
    private String artist;
    private String title;
    private int duration;
    private String url;


    public VKAudio(int aid, int owner_id, String artist, String title, int duration, String url) {
        this.aid = aid;
        this.owner_id = owner_id;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public int getAid() {
        return aid;
    }
}

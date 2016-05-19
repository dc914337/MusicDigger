package com.digger.dmitry.musicdigger.player;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by Dmitry on 18/05/2016.
 */
public class DiggerPlayer {

    static ArrayList<PlaylistItem> playlist=new ArrayList<PlaylistItem>();
    static int currentPlayingIndex=0;
    static MediaPlayer player;
    static Context ctx;

    public static void addToPlaylist(PlaylistItem newItem)
    {
        playlist.add(newItem);
    }

    public static void start(Context appCtx)  {
        ctx=appCtx;
        player= MediaPlayer.create(ctx, getCurrentPlaying().getUrl());
        getCurrentPlaying().setPlaying(true);

        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                player.release();
                DiggerPlayer.next();
            }
        });
    }

    public static void pause()
    {
        player.pause();
    }

    public static void stop()
    {
        if(isPlaying())
            player.stop();
    }

    public static void clearPlaylist()
    {
        playlist.clear();
        currentPlayingIndex=0;
        if(isPlaying())
            player.stop();

    }



    public static boolean hasNext()
    {
        return currentPlayingIndex<=playlist.size()-2;
    }

    public static void next() {
        player.release();
        if(!hasNext())
        {
            return;
        }
        getCurrentPlaying().setPlaying(false);
        currentPlayingIndex++;
        start(ctx);
    }


    public static boolean isPlaying()
    {
        if(player!=null)
        return player.isPlaying();
        else
            return false;
    }

    public static PlaylistItem getCurrentPlaying(){
        return playlist.get(currentPlayingIndex);
    }

    public static ArrayList<PlaylistItem> getPlaylist()
    {
        return playlist;
    }


    public static void setPlaying(int playing) {
        player.release();
        getCurrentPlaying().setPlaying(false);
        currentPlayingIndex=playing;
        start(ctx);
    }
}

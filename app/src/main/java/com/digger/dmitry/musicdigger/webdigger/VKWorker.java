package com.digger.dmitry.musicdigger.webdigger;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dmitry on 18/05/2016.
 */
public class VKWorker
{
    private static String vkToken="8eb45678cc974aa50a1c4ae199f621c9f4379fac10e6b4f7ff250cf6cfdb0a4b113885270d71c6be5a452";
    private static String audio_request_template="https://api.vk.com/method/audio.getById?audios=%s&access_token=%s";


    public static ArrayList<VKAudio> getAudios(String[] short_uris)
    {
        String response;
        ArrayList<VKAudio> resp=new ArrayList<VKAudio>();
        try {
            response= GetRequester.apiGetRequest(String.format(audio_request_template, TextUtils.join(",", short_uris), vkToken));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);

            JSONArray jsonArray = jObject.getJSONArray("response");
            for(int i = 0, count = jsonArray.length(); i< count; i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                VKAudio audio=new VKAudio(
                        jsonObject.getInt("aid"),
                        jsonObject.getInt("owner_id"),
                        jsonObject.getString("artist"),
                        jsonObject.getString("title"),
                        jsonObject.getInt("duration"),
                        jsonObject.getString("url"));
                resp.add(audio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return resp;

    }
}

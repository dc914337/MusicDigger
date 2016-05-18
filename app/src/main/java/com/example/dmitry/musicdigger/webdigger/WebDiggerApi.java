package com.example.dmitry.musicdigger.webdigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class WebDiggerApi {

    private static String apiUri="http://176.96.19.208:6666/api/getAudios/";



    public ArrayList<VKAudio> getAudios(String request)
    {
        String response;
        try {
            response=GetRequester.apiGetRequest(generateUri(request));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            if(!jObject.getBoolean("success"))
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }



        ArrayList<String> shortUris = new ArrayList<String>();
        JSONArray jsonArray = null;
        try {
            jsonArray = jObject.getJSONArray("audios");
            for(int i = 0, count = jsonArray.length(); i< count; i++)
            {
                    shortUris.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            return null;
        }

        return VKWorker.getAudios(shortUris.toArray(new String[shortUris.size()]));
    }


    private String generateUri(String request)
    {
        return apiUri.concat(URLEncoder.encode(request));
    }



}

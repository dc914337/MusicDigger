package com.digger.dmitry.musicdigger.webdigger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dmitry on 18/05/2016.
 */
public class GetRequester {

    public static String apiGetRequest(String apiUri) throws IOException {
        URL url = new URL(apiUri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(60000);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod("GET");
       // conn.setDoInput(true);
        conn.setDoOutput(false);

        int responseCode = conn.getResponseCode();
        StringBuilder response=new StringBuilder();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        } else {
            response = new StringBuilder();
        }
        return response.toString();
    }


}

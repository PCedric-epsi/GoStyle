package com.epsi.gostyle.rest;

import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtils {

    public static String findCodes(String url) throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    /*public static String findCodeByName(String url, String code) throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url + code).build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }*/

    public static String findCodeByName(String url, String code) throws Exception{

        URL url_ = new URL(url + code);
        HttpURLConnection con = (HttpURLConnection) url_.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        return content.toString();
    }
}

package com.epsi.gostyle.rest;

import android.os.SystemClock;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtils {

    public static String findCodes(String url) throws Exception{

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Log.w("URL", String.valueOf(request.url()));
        Response response = client.newCall(request).execute();

        if(response.code() < 200 || response.code() >= 300){
            Log.w("REP", "fail");
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        }
        else {
            Log.w("REP", "WIN");
            return response.body().string();
        }
    }

    public static String findCodeByName(String url, String code) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + code).build();

        Log.w("URL", String.valueOf(request.url()));
        Response response = client.newCall(request).execute();

        if(response.code() < 200 || response.code() >= 300){
            Log.w("REP", "fail");
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        }
        else {
            Log.w("REP", "WIN");
            return response.body().string();
        }
    }
}

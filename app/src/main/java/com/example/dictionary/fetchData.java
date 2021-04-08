package com.example.dictionary;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class fetchData extends AsyncTask<String,Integer,String > {
    Context context;
    TextView mview;
    fetchData(Context context, TextView tV){
        this.context =context;
        mview=tV;
    }

    @Override
    protected String doInBackground(String... params) {


        final String app_id = "0ee22a30";
        final String app_key = "543144d1c6fb94837aba663a54f87ec8";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String def;
        try {
            JSONObject js=new JSONObject(result) ;
            JSONArray results=js.getJSONArray("results");

            JSONObject lEntries=results.getJSONObject(0) ;
            JSONArray laArray=lEntries.getJSONArray("lexicalEntries");

            JSONObject entries=laArray.getJSONObject(0);
            JSONArray e=entries.getJSONArray("entries");

            JSONObject jsonObject =e.getJSONObject(0);
            JSONArray sensesArray=jsonObject.getJSONArray("senses") ;

            JSONObject de=sensesArray.getJSONObject(0);
            JSONArray d=de.getJSONArray("definitions");

            def=d.getString(0);
            mview.setText(def);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
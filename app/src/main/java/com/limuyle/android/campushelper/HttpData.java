package com.limuyle.android.campushelper;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by limuyle on 2018/3/10.
 */

public class HttpData extends AsyncTask<String,Void,String> {
    private String url;
    private StringBuilder builder;
    private BufferedReader reader=null;
    private HttpDataListener listener;

    public HttpData(String url,HttpDataListener listener){
            this.url=url;
            this.listener=listener;

   }

    @Override
    protected String doInBackground(String... strings) {

            try {
                URL request=new URL(url);
                URLConnection connection=request.openConnection();
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String inputLine=null;
                builder=new StringBuilder();
                while((inputLine=reader.readLine())!=null){
                    builder.append(inputLine);
                }
                reader.close();
                return builder.toString();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return null;


    }
    @Override
    protected void onPostExecute(String result){
        listener.getDataUrl(result);
        super.onPostExecute(result);

    }

}


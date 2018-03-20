package com.limuyle.android.campushelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

public class NewsActivity extends AppCompatActivity {
    private VideoView videoView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();




        if (intent != null) {

            if(intent.getExtras()!=null){
                String type=intent.getStringExtra("type");
                switch (type){
                    case "url":
                        webView = findViewById(R.id.wv);
                        String url=intent.getStringExtra("url");
                        webView.loadUrl(url);
                        break;
                    case "uri":
                        String uri=intent.getStringExtra("uri");
                        //Log.i("url-------------------",uri);
                        Uri vUri = Uri.parse(uri);
                       // videoView=new VideoView(this);
                        videoView = findViewById(R.id.video_view);
                        videoView.setMediaController(new MediaController(this));
                        videoView.setVideoPath(vUri.toString());
                        videoView.requestFocus();
                        videoView.start();


                }
            }

        }


    }
}



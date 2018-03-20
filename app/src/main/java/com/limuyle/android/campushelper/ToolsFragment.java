package com.limuyle.android.campushelper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by limuyle on 2018/3/15.
 */

public  class ToolsFragment extends Fragment{
    private GridView gv;
    private EditText edt_page;
    
    private Button check,preview,next;
    private  ArrayList<Image> mImages;
    private GridViewAdapter adapter;
    private int page=1;
    private PicsDownloader task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.tools_fragment,container,false);
        gv=view.findViewById(R.id.gv);
        edt_page=view.findViewById(R.id.edt_page);
        check=view.findViewById(R.id.check);
        preview=view.findViewById(R.id.preview);
        next=view.findViewById(R.id.next);

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        task=new PicsDownloader("https://www.apiopen.top/meituApi?page="+page);
        task.execute();

        super.onAttach(activity);
    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState) {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1){
                    page--;
                    new PicsDownloader("https://www.apiopen.top/meituApi?page="+page).execute();
                }else {
                    Toast.makeText(getActivity(), "前面没有了!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                new PicsDownloader("https://www.apiopen.top/meituApi?page=" + page).execute();


            } });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=edt_page.getText().toString().trim();
                if (Integer.parseInt(input)>0){
                    page=Integer.parseInt(input);
                    new PicsDownloader("https://www.apiopen.top/meituApi?page=" + page).execute();
                }
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),NewsActivity.class);
                intent.putExtra("type","uri");
                intent.putExtra("url",mImages.get(position).getUrl());
                startActivity(intent);
               // WebView webView=new WebView(getActivity());
                     //   webView.loadUrl(mImages.get(position).getUrl());
                //Toast.makeText(getActivity(), "You click picture!", Toast.LENGTH_SHORT).show();
            }
        });



        super.onActivityCreated(saveInstanceState);
    }
    @Override
    public void onResume() {

        super.onResume();
        if (adapter != null) {
            gv.setAdapter(adapter);
        } else {
            adapter = new GridViewAdapter(getActivity(), mImages);
            gv.setAdapter(adapter);
        }
    }
    @Override
    public void onPause() {
        //task.cancel(true);
        Toast.makeText(getActivity(), "Canceled!", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    public static Fragment newInstance() {
        return new ToolsFragment();
    }

      class PicsDownloader extends AsyncTask<String,Void,ArrayList<Image>> {
        private String url;
        public PicsDownloader(String url){
            this.url=url;
        }

        @Override
        protected ArrayList<Image> doInBackground(String... strings) {
            BufferedReader reader;
            StringBuilder builder;
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
               String json=builder.toString();
              // JSONObject object=new JSONObject(json);
              mImages=new ArrayList<>();
               JSONObject object=new JSONObject(json);

            //  JSONObject obj=object.getJSONObject("data");
              JSONArray array=object.getJSONArray("data");


               for(int i=0;i<array.length();i++){
                   //JSONObject object= array1.getJSONObject(i);
                   JSONObject obj= array.getJSONObject(i);
                   if(obj!=null) {
                      Log.i("url", obj.getString("url"));
                       mImages.add(new Image(i + "", obj.getString("url")));
                   }
               }


            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mImages;
        }
        @Override
        protected void onPostExecute(ArrayList<Image> images){
            super.onPostExecute(images);
            Log.i("images",images.size()+"");
adapter=new GridViewAdapter(getActivity(),images);
if(images.size()>0) {
    gv.setAdapter(adapter);
}

        }
    }
}

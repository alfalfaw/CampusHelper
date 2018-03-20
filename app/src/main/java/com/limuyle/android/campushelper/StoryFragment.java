package com.limuyle.android.campushelper;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class StoryFragment extends Fragment {
    private ListView slv;
    private ArrayList<String> sList;
    private Button check,next,preview;
    private EditText edt_page;
    private int page=1;
    private int oldSize=0;


    // TODO: Rename and change types and number of parameters
    public static StoryFragment newInstance() {
        StoryFragment fragment = new StoryFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sList=new ArrayList<>();
        new StoryDownloader("https://www.apiopen.top/satinApi?type=29&page="+page).execute();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                oldSize=sList.size();
                edt_page.setText(page+"");
                new StoryDownloader("https://www.apiopen.top/satinApi?type=29&page="+page).execute();
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1){
                    oldSize=sList.size();
                    page--;
                    edt_page.setText(page+"");
                    new StoryDownloader("https://www.apiopen.top/satinApi?type=29&page="+page).execute();
                   // new ToolsFragment.PicsDownloader("https://www.apiopen.top/meituApi?page="+page).execute();
                }else {
                    Toast.makeText(getActivity(), "前面没有了!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                edt_page.setText(page+"");
                oldSize=sList.size();
                new StoryDownloader("https://www.apiopen.top/satinApi?type=29&page="+page).execute();
                //new ToolsFragment.PicsDownloader("https://www.apiopen.top/meituApi?page=" + page).execute();



            } });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldSize=sList.size();
                String input=edt_page.getText().toString().trim();
                if (Integer.parseInt(input)>0){
                    page=Integer.parseInt(input);
                    edt_page.setText(page+"");
                    new StoryDownloader("https://www.apiopen.top/satinApi?type=29&page="+page).execute();
                   // new ToolsFragment.PicsDownloader("https://www.apiopen.top/meituApi?page=" + page).execute();

                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_story, container, false);
        slv=view.findViewById(R.id.lv);
        edt_page=view.findViewById(R.id.edt_page);
        check=view.findViewById(R.id.check);
        preview=view.findViewById(R.id.preview);
        next=view.findViewById(R.id.next);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        }

    class StoryDownloader extends AsyncTask<String,Void,ArrayList<String>> {
        private String url;

        public StoryDownloader(String url) {
            this.url = url;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            BufferedReader reader;
            StringBuilder builder;
            try {
                URL request = new URL(url);
                URLConnection connection = request.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String inputLine = null;
                builder = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    builder.append(inputLine);
                }
                reader.close();
                String json = builder.toString();
                // JSONObject object=new JSONObject(json);

                JSONObject object = new JSONObject(json);

                //  JSONObject obj=object.getJSONObject("data");
                JSONArray array = object.getJSONArray("data");


                for (int i = 0; i < array.length(); i++) {
                    //JSONObject object= array1.getJSONObject(i);
                    JSONObject obj = array.getJSONObject(i);
                    if (obj != null) {
                        Log.i("url", obj.getString("text"));
                        sList.add(obj.getString("text"));
                    }
                }


            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> stories){
            super.onPostExecute(stories);
            Log.i("images",stories.size()+"");
            ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.story_item,R.id.tv_story,sList);
            slv.setAdapter(adapter);
            slv.setSelection(oldSize);

        }
    }

    }





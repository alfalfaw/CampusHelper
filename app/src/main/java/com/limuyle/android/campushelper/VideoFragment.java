package com.limuyle.android.campushelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wseemann.media.FFmpegMediaMetadataRetriever;


public class VideoFragment extends Fragment {
    private ListView slv;
    private ArrayList<String> sList;
    private Button check,next,preview;
    private EditText edt_page;
    private int page=1;
    private int oldSize=0;
    private ArrayList <Map<String,Object>> mVideoList;

    //private ArrayList<Bitmap> mBitmaps;
   // private ArrayList<String> mUrls;


    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mVideoList=new ArrayList<>();
        new VideoDownloader("https://www.apiopen.top/satinApi?type=41&page="+page).execute();
       slv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(),NewsActivity.class);
            intent.putExtra("uri", (String) mVideoList.get(position).get("url"));
            intent.putExtra("type","uri");
            startActivity(intent);
           }
       });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                oldSize=mVideoList.size();
                edt_page.setText(page+"");
                new VideoDownloader("https://www.apiopen.top/satinApi?type=41&page="+page).execute();
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1){
                    oldSize=mVideoList.size();
                    page--;
                    edt_page.setText(page+"");
                    new VideoDownloader("https://www.apiopen.top/satinApi?type=41&page="+page).execute();
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
                oldSize=mVideoList.size();
                new VideoDownloader("https://www.apiopen.top/satinApi?type=41&page="+page).execute();
                //new ToolsFragment.PicsDownloader("https://www.apiopen.top/meituApi?page=" + page).execute();



            } });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldSize=mVideoList.size();
                String input=edt_page.getText().toString().trim();
                if (Integer.parseInt(input)>0){
                    page=Integer.parseInt(input);
                    edt_page.setText(page+"");
                    new VideoDownloader("https://www.apiopen.top/satinApi?type=41&page="+page).execute();
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

    class VideoDownloader extends AsyncTask<String,Void,ArrayList<Map<String,Object>>> {
        private String url;

        public VideoDownloader(String url) {
            this.url = url;
        }

        @Override
        protected ArrayList<Map<String, Object>> doInBackground(String... strings) {
            BufferedReader reader;
            StringBuilder builder;
            FFmpegMediaMetadataRetriever mmr=new FFmpegMediaMetadataRetriever();

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
                        //Log.i("url", obj.getString("videouri"));
                        String vurl=obj.getString("videouri").toString().trim();
                        String title=null;
                        String height;
                        String width;
                        if(!vurl.equals("")) {
                            mmr.setDataSource(vurl);
                            Bitmap bitmap = mmr.getScaledFrameAtTime(1000,FFmpegMediaMetadataRetriever.OPTION_CLOSEST,80,60);
                           title=mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE);
                           height=mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                           width=mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);



                                    //VideoHelper.createVideoThumbnail(vurl,80,60);
                            //mUrls.add(vurl);
                            //mBitmaps.add(bitmap);
                            HashMap<String,Object> map=new HashMap<>();
                            map.put("video",bitmap);
                            map.put("url",vurl);
                            map.put("title",title);
                            map.put("height",height);
                            map.put("width",width);
                            mVideoList.add(map);

                        }
                    }
                }
                mmr.release();


            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mVideoList;
        }
        @Override
        protected void onPostExecute(ArrayList<Map<String,Object>> vList){
            super.onPostExecute(vList);
            mVideoList=vList;
            Log.i("mVideoList",mVideoList.size()+"");
            //ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.video_item,R.id.tv_video,sList);
            //SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),mVideoList,R.layout.video_item,new String[]{"video","url"},new int[]{R.id.media,R.id.tv_video});
            if(mVideoList.size()>0) {
                slv.setAdapter(new VideoAdapter(getActivity(),mVideoList));
                slv.setSelection(oldSize);
            }

        }
    }


}






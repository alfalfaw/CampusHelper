package com.limuyle.android.campushelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by limuyle on 2018/3/15.
 */

public  class NewsListFragment extends Fragment implements NewsTaskListener {
    private ListView news_lv;
    private String start_url = "http://news.qau.edu.cn";
    private ArrayList<NewsBean> mList;
    private NewsAdapter adapter;
    private WebView mWebView;
    private NewsDownloaderTask task;
    //
//    private ImageView iv_contact;

    //    private AlertDialog.Builder builder;
//    private AlertDialog mDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        news_lv = view.findViewById(R.id.news_lv);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        mWebView = new WebView(getActivity());


        initEvents();

    }


    public void initEvents() {
        mList = new ArrayList<NewsBean>();


//        iv_contact=findViewById(R.id.iv_chat);
//        iv_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(NewsListActivity.this,ChatActivity.class));
//            }
//        });
//        ImageView iv_news = findViewById(R.id.iv_news);
//        ImageView iv_tool=findViewById(R.id.iv_tool);


        news_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // mWebView.loadUrl(mList.get(position).getNews_url());
//               Toast.makeText(getActivity(), "You click item!", Toast.LENGTH_SHORT).show();
//                FragmentManager manager=getActivity().getFragmentManager();
//                FragmentTransaction transaction=manager.beginTransaction();
//                        transaction.add(R.id.fragment_container, NewsFragment.newInstance(mList.get(position).getNews_url()))
//                        .commit();
//            }
//        });
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("url", mList.get(position).getNews_url());
                intent.putExtra("type","url");
                startActivity(intent);

            }
        });
    }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mList=NetworkUtils.getNews(start_url);
//                Message message=Message.obtain();
//                message.obj=mList;
//                mHandler.sendMessage(message);
//
//            }
//        }).start();


//    private void setCustomActionBar() {
//        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
//        View actionBarView= LayoutInflater.from(this).inflate(R.layout.action_bar,null);
//        ActionBar action_bar=getActionBar();
//        action_bar.setCustomView(actionBarView,lp);
//        action_bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        action_bar.setDisplayShowCustomEnabled(true);
//        action_bar.setDisplayShowHomeEnabled(false);
//        action_bar.setDisplayShowTitleEnabled(false);
//
//    }


//
//        for(int i=0;i<10;i++){
//newList.add(mList.get(i));}


//    class MyHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            mList = (ArrayList<NewsBean>) message.obj;
//            adapter = new NewsAdapter(NewsListActivity.this, mList);
//
//            news_lv.setAdapter(adapter);
//        }
//
//
//    }


    public static Fragment newInstance() {
        return new NewsListFragment();
    }
    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        task=new NewsDownloaderTask(start_url, this);
        task.execute();
        //Toast.makeText(getActivity(), "Task start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getNewsList(ArrayList<NewsBean> newsList) {
        mList = newsList;
        adapter = new NewsAdapter(getActivity(), mList);
        news_lv.setAdapter(adapter);
    }

//    @Override
//    public void onResume() {
//
//        super.onResume();
//        if (adapter != null) {
//            news_lv.setAdapter(adapter);
//        } else {
//            adapter = new NewsAdapter(getActivity(), mList);
//            news_lv.setAdapter(adapter);
//        }
//    }
    @Override
    public void onPause() {
     task.cancel(true);
       // Toast.makeText(getActivity(), "Canceled!", Toast.LENGTH_SHORT).show();
        super.onPause();
    }





}




package com.limuyle.android.campushelper;

import android.app.Activity;
import android.os.AsyncTask;

import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by limuyle on 2018/3/13.
 */

public class NewsDownloaderTask extends AsyncTask<String,Void,ArrayList<NewsBean>> {
    private static ArrayList<NewsBean> mNewsBeanArrayList=new ArrayList<>();
    private static Document document=null;
    private static Document doc=null;
    //private static ArrayList<String> content_urls=new ArrayList<>();
    private String url;
    private NewsTaskListener listener;
    //private static ArrayList<String> titles=new ArrayList<>();
    public NewsDownloaderTask(String url, NewsTaskListener listener){
        this.url=url;
        this.listener=listener;
    }


    @Override
    protected ArrayList<NewsBean> doInBackground(String...strings) {
//        try {
//            document = Jsoup.connect(url).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Elements groups = document.select("a[href]");
//        String contentUrl=null;
//        String title = null;
//
//        for (Element group : groups) {
//
//            String tmpUrl = group.attr("href");
//
//
//            if(tmpUrl.startsWith("/content/")) {
//
//                if (!(title = group.attr("title")).equals("")) {
//                    contentUrl = "http://news.qau.edu.cn" + tmpUrl;
//                    content_urls.add(contentUrl);
//                    titles.add(title);
//
//                }
//            }
//
//
//
//        }
//      //  for(String content_url:content_urls){
//            for(int i=0;i<content_urls.size();i++){
//            String imageUrl="";
//            try {
//                doc = Jsoup.connect(content_urls.get(i)).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Elements elements=doc.select("img[src$=.jpg]");
//            for(Element element:elements){
//                String image=element.attr("src");
//                if(image.startsWith("/userfiles/image/")) {
//                    imageUrl = "http://news.qau.edu.cn" + image;
//
//                    break;
//                }
//            }
//            mNewsBeanArrayList.add(new NewsBean(titles.get(i), content_urls.get(i),imageUrl));
//
//
//
//        }

            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements groups = document.select("a[href]");


            for (Element group : groups) {
                String contentUrl=null;
                String title ="";
                String imageUrl="";

                String tmpUrl = group.attr("href");


                if(tmpUrl.startsWith("/content/")) {

                    if (!(title = group.attr("title")).equals("")) {
                        contentUrl = "http://news.qau.edu.cn" + tmpUrl;
                        // content_urls.add(contentUrl);
                        try {
                            doc = Jsoup.connect(contentUrl).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Elements elements=doc.select("img[src$=.jpg]");
                        for(Element element:elements){
                            String image=element.attr("src");
                            if(image.startsWith("/userfiles/image/")) {
                                imageUrl = "http://news.qau.edu.cn" + image;


                                break;
                            }
                        }

                        mNewsBeanArrayList.add(new NewsBean(title, contentUrl,imageUrl));

                    }
                }



            }
        return mNewsBeanArrayList;



    }
    @Override
    protected void onPostExecute(ArrayList<NewsBean> newsList){
        listener.getNewsList(newsList);
        super.onPostExecute(newsList);
    }




}

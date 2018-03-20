package com.limuyle.android.campushelper;

/**
 * Created by limuyle on 2018/3/13.
 */

public class NewsBean {
    private int id;
    private int type;
    private String title;
    private String news_url;
    private String icon_url;
    public NewsBean(String title,String news_url,String image_url){
        setTitle(title);
        setNews_url(news_url);
        setIcon_url(image_url);
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getNews_url() {
        return news_url;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }
}

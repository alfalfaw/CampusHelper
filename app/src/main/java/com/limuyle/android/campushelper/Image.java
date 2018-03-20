package com.limuyle.android.campushelper;

/**
 * Created by limuyle on 2018/3/17.
 */

public class Image {
    private String name,url;
    public Image(String name,String url){
        setName(name);
        setUrl(url);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
}

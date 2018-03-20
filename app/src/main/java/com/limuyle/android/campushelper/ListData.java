package com.limuyle.android.campushelper;

import java.io.Serializable;

/**
 * Created by limuyle on 2018/3/10.
 */

public class ListData implements Serializable{
    public static final int SEND=1;
    public static final int RECEIVER=2;
    private int flag;
    private long time;
    private String content;
    private int id;

    public ListData(int id,String content,int flag,long time){
        setContent(content);
        setFlag(flag);
        setTime(time);
        setId(id);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public String toString(){
        return this.content+""+this.flag+""+String.valueOf(time);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

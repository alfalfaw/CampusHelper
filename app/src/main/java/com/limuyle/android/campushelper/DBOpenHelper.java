package com.limuyle.android.campushelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by limuyle on 2018/3/13.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="record.db";
    private static final int DB_VERSION=1;
    public DBOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table record(_id integer primary key autoincrement not null,name text,content text ,flag integer,time integer)";
        db.execSQL(sql);
        ContentValues contentValues=new ContentValues();
for (int i=0;i<2;i++) {
    String name=i%2==0?"turing":"moli";
    String content = getRandomWelcomeTips();
    long time = System.currentTimeMillis();
    contentValues.put("name",name);
    contentValues.put("content", content);
    contentValues.put("flag", ListData.RECEIVER);
    contentValues.put("time", time);
    db.insert("record", null, contentValues);
}

    }

    private String getRandomWelcomeTips() {
        String[] welcome_tips = new String[]{
                "没有都市的繁华，没有山林的鸟语花香，只有一片如水的宁静，来这里你可以静心休憩你的灵魂，调养你疲惫的心.",
                "花径不曾缘客扫，蓬门今始为君开.",
                "时光太瘦,指尖太宽.终于让我等到你.",
                "你念或者不念我，情就在这里，不来也不去.",
                "你见，或者不见我,我就在那里,不悲不喜."};
        return welcome_tips[(int)(Math.random()*5)];
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

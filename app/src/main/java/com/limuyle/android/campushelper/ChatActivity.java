package com.limuyle.android.campushelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.limuyle.android.campushelper.DBOpenHelper;
import com.limuyle.android.campushelper.HttpData;
import com.limuyle.android.campushelper.HttpDataListener;
import com.limuyle.android.campushelper.ListData;
import com.limuyle.android.campushelper.MyBaseAdapter;
import com.limuyle.android.campushelper.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends Activity implements HttpDataListener,View.OnClickListener {
    private HttpData httpData;
    private ArrayList<ListData> lists;
    private RecyclerView rv;
    private EditText edt;
    private Button btn;
    private String contentStr;
    private MyBaseAdapter adapter;
    private final int TURING=0;
    private final int MOLI=1;

    LinearLayoutManager manager;

    SQLiteDatabase db;
    int robot_id=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        if (intent!=null) {
            robot_id = intent.getIntExtra("robot_id", 0);
        }
        if(savedInstanceState!=null){
            robot_id=savedInstanceState.getInt("robot_id",0);
        }
        DBOpenHelper helper=new DBOpenHelper(this);
        db=helper.getWritableDatabase();

        manager=new LinearLayoutManager(this, LinearLayout.VERTICAL,false);

        initView();

    }

    private void initData() {

        lists=new ArrayList<>();
        Cursor cursor=null;
        if(robot_id==TURING) {
            cursor = db.query("record", null, "name=?", new String[]{"turing"}, null, null, null);
        }else {
            cursor=db.query("record",null,"name=?",new String[]{"moli"},null,null,null);
        }
        while (cursor.moveToNext()){


            String content=cursor.getString(cursor.getColumnIndex("content"));
            long time=cursor.getLong(cursor.getColumnIndex("time"));
            int flag=cursor.getInt(cursor.getColumnIndex("flag"));
            lists.add(new ListData(robot_id,content,flag,time));


        }

    }


    private void initView(){

        initData();
        rv=findViewById(R.id.rv);
        edt=findViewById(R.id.sendMsg);
        btn=findViewById(R.id.send);
//        builder=new AlertDialog.Builder(this);
//        builder.setTitle("温馨提示:").setIcon(android.R.drawable.btn_star).setMessage("确定要退出吗?").setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//
//            }
//        }).setNegativeButton("否",null);
//        mDialog=builder.create();


        edt.addTextChangedListener(new TextWatcher() {
                                       @Override
                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                       }

                                       @Override
                                       public void onTextChanged(CharSequence s, int start, int before, int count) {
                                           if (!TextUtils.isEmpty(edt.getText().toString())){
                                               btn.setBackgroundResource(R.drawable.btn_enable);
                                               btn.setEnabled(true);
                                           }else{
                                               btn.setBackgroundResource(R.drawable.btn_unenable);
                                               btn.setEnabled(false);
                                           }

                                       }

                                       @Override
                                       public void afterTextChanged(Editable s) {
                                           if (!TextUtils.isEmpty(edt.getText().toString())){
                                               btn.setBackgroundResource(R.drawable.btn_enable);
                                               btn.setEnabled(true);
                                           }else{
                                               btn.setBackgroundResource(R.drawable.btn_unenable);
                                               btn.setEnabled(false);
                                           }



                                       }


                                   }
        );




        btn.setOnClickListener(this);
        rv.setLayoutManager(manager);

        adapter = new MyBaseAdapter(lists, this);

        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        rv.smoothScrollToPosition(lists.size());

    }



    @Override
    public void getDataUrl(String data) {
        parseText(data);


    }
    public void parseText(String html){
        String content=null;
        if (robot_id==TURING) {

            try {
                JSONObject object = new JSONObject(html);
                content=object.getString("text");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            content=html;
        }

        ListData listData;
        listData = new ListData(robot_id,content, ListData.RECEIVER, System.currentTimeMillis());
        lists.add(listData);
        insert(listData);
        adapter.notifyDataSetChanged();
        rv.smoothScrollToPosition(lists.size() - 1);
    }

    @Override
    public void onClick(View v) {
        contentStr=edt.getText().toString().trim();
        ListData listData=new ListData(robot_id,contentStr,ListData.SEND, System.currentTimeMillis());
        lists.add(listData);
        insert(listData);
        adapter.notifyItemChanged(lists.size()-1);
        rv.smoothScrollToPosition(lists.size()-1);
        edt.setText("");
        if(robot_id==0) {
            httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=e7683257dc9c471c848d11ceabb30024&info=" + contentStr, this).execute();
        }else{
            httpData=(HttpData)new HttpData("http://i.itpk.cn/api.php?question="+contentStr+"&api_key=d6f8c7c1785d25845635329dcd18cd83&api_secret=6pr975lkzj5r",this).execute();
        }

    }

    private void insert(ListData listData) {
        String name=robot_id%2==0?"turing":"moli";
        ContentValues contentValues=new ContentValues();

        contentValues.put("name",name);
        contentValues.put("content",listData.getContent());
        contentValues.put("flag",listData.getFlag());
        contentValues.put("time",listData.getTime());
        db.insert("record",null,contentValues);
    }

//    private void updateData(ListData listData,boolean isUpdateAll) {
//        FileOutputStream fileOutputStream = null;
//        ObjectOutputStream objectOutputStream = null;
//        if (!isUpdateAll) {
//            try {
//                fileOutputStream = openFileOutput("record.dat", MODE_APPEND);
//                objectOutputStream = new ObjectOutputStream(fileOutputStream);
//                objectOutputStream.writeObject(listData);
//            } catch (Exception e) {
//                Log.i("IN UPONE ", "________ERROR________");
//                e.printStackTrace();
//            } finally {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    objectOutputStream.close();
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            try {
//                fileOutputStream = openFileOutput("record.dat", MODE_PRIVATE);
//                objectOutputStream = new ObjectOutputStream(fileOutputStream);
//                for (int i = 0; i < lists.size(); i++) {
//                    objectOutputStream.writeObject(lists.get(1));
//                }
//
//            } catch (Exception e) {
//                Toast.makeText(this, "Update all error", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            } finally {
//                try {
//                    fileOutputStream.close();
//                    Log.i("Error", "Error");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    objectOutputStream.close();
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//            }
//
//
//        }
//    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.options_menu,menu);
//        setIconsVisible(menu,true);
//        return true;
//
//    }

//    private void setIconsVisible(Menu menu, boolean flag) {
//        if(menu!=null){
//
//                Method method= null;
//                try {
//                    method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
//                    method.setAccessible(true);
//                    method.invoke(menu,flag);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()) {
//                    case R.id.action_search:
//                        Toast.makeText(ChatActivity.this, "You click search!", Toast.LENGTH_SHORT).show();
//
//
//                        break;
//                    case R.id.action_share:
//                        Toast.makeText(ChatActivity.this, "You click share!", Toast.LENGTH_SHORT).show();
//
//
//                        break;
//                    case R.id.change:
//                        count++;
//                        if (count % 2 == 0) {
//                            code = 0;
//                        } else {
//                            code = 1;
//                        }
//
//                        Toast.makeText(ChatActivity.this, "Done!", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {

        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("robot_id",robot_id);
    }
}







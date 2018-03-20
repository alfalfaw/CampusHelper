package com.limuyle.android.campushelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by limuyle on 2018/3/18.
 */

  public class VideoAdapter extends BaseAdapter{
      private Context context;
      private LayoutInflater mInflater;
      private ArrayList<Map<String,Object>> mList;
      public VideoAdapter(Context context, ArrayList<Map<String,Object>> mList){
          this.context=context;
          this.mList=mList;

      }

@Override
public int getCount() {
        return mList!=null?mList.size():0;
        }

@Override
public Object getItem(int position) {
        return mList.get(position);
        }

@Override
public long getItemId(int position) {
        return position;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        ViewHolder holder=null;
        if(convertView==null){
        inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.video_item,null);
        holder.iv=convertView.findViewById(R.id.media);
        holder.tv=convertView.findViewById(R.id.tv_video);
        convertView.setTag(holder);




        }else{
            holder= (ViewHolder) convertView.getTag();
        }
    holder.tv.setText((CharSequence) mList.get(position).get("title"));
        holder.iv.setImageBitmap((Bitmap) mList.get(position).get("video"));



        return convertView;
        }
    static class ViewHolder{
        static ImageView iv;
        static TextView tv;





        }

}


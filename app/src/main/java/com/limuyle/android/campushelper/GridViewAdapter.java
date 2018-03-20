package com.limuyle.android.campushelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

/**
 * Created by limuyle on 2018/3/17.
 */

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Image> imgs;
    private Context context;
    LayoutInflater inflater;
    public GridViewAdapter(Context context,ArrayList<Image> images){
        this.imgs=images;
        this.context=context;

    }
    @Override
    public int getCount() {
        return imgs!=null?imgs.size():0;
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            inflater= LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.grid_item,null);
            holder=new ViewHolder();
            holder.iv=convertView.findViewById(R.id.iv_image);
            holder.tv=convertView.findViewById(R.id.tv_imgName);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(imgs.get(position).getName());
//        RequestOptions options=new RequestOptions()
//                .placeholder(R.drawable.icon)
//                .error(R.drawable.icon);
        Glide.with(context)
                .load(imgs.get(position).getUrl().toString().trim())
                .placeholder(R.drawable.icon)
                .override(150,150)
                .centerCrop()
                .crossFade()
                .into(holder.iv);

        return convertView;
    }
    static class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}

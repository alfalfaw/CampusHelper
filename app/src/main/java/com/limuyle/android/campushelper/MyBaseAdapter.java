package com.limuyle.android.campushelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limuyle on 2018/3/10.
 */

public class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.ViewHolder> {
    private ArrayList<ListData> lists;
    private Context context;
//    private static final int SEND=1;
//    private static final int RECEIVER=2;
    private final int TURING=0;
    private final int MOLI=1;
    public MyBaseAdapter(ArrayList<ListData> lists,Context context){
        this.context=context;
        this.lists=lists;

    }

//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            if (lists.get(position).getFlag() == ListData.SEND) {
//                convertView = inflater.inflate(R.layout.right_item,parent, false);
//
//            }
//            if (lists.get(position).getFlag() == ListData.RECEIVER) {
//                convertView = inflater.inflate(R.layout.left_item,parent,false);
//            }
//            holder.tv = convertView.findViewById(R.id.tv);
//            holder.tv_time = convertView.findViewById(R.id.time);
//            holder.iv=convertView.findViewById(R.id.iv);
//
//            convertView.setTag(holder);
//        } else {
//            holder= (ViewHolder) convertView.getTag();
//        }
//
//        holder.tv.setText(lists.get(position).getContent());
//
//        holder.tv_time.setText(lists.get(position).getTime());
//
//        if (lists.get(position).getFlag() == ListData.SEND) {
//            holder.iv.setImageResource(R.drawable.user_profile);
//
//        }else{
//            holder.iv.setImageResource(R.drawable.jimmy);
//        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=null;
        switch (viewType){
            case 1:
                view=inflater.inflate(R.layout.right_item,parent,false);
                return new ViewHolder(view);
            case 2:
                view=inflater.inflate(R.layout.left_item,parent,false);
                return new ViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(lists.get(position).getFlag()==ListData.SEND){
            holder.iv.setImageResource(R.drawable.user_profile);
        }else{
            if (lists.get(position).getId()==TURING) {
                holder.iv.setImageResource(R.drawable.turing);
            }else {
                holder.iv.setImageResource(R.drawable.moli);
            }
        }

        long time=lists.get(position).getTime();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str=format.format(time);
        if(position==0||(time-lists.get(position-1).getTime()>5*60*1000)){
            holder.tv_time.setText(str);

        } else{
            holder.tv_time.setText("");
        }
        holder.tv.setText(lists.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position){
        if(lists.get(position).getFlag()==ListData.SEND){

            return 1;
        }else{
            return 2;
        }



    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv,tv_time;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv_time=itemView.findViewById(R.id.time);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}

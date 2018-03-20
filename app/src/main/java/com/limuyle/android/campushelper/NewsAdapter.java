package com.limuyle.android.campushelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.utils.StorageUtils;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by limuyle on 2018/3/13.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private ImageLoaderConfiguration mConfiguration;
    private LayoutInflater mInflater;
    DisplayImageOptions mOptions;
    private ArrayList<NewsBean> newsList;
    public  NewsAdapter(Context context, ArrayList<NewsBean> newsList) {
        //this.mInflater=LayoutInflater.from(context);
        this.newsList = newsList;
        // ImageLoader.getInstance().init(mConfiguration);
        this.context = context;
        //File cacheDir= StorageUtils.getOwnCacheDirectory(context,"imageLoader/Cache");
//        mOptions=new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.qau)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .delayBeforeLoading(100)
//                .showImageForEmptyUri(R.drawable.qau)
//                .showImageOnFail(R.drawable.qau)
//                .build();
//        mConfiguration = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCache(new UnlimitedDiskCache(cacheDir))
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                .imageDownloader(new BaseImageDownloader(context,5*1000,30*1000))
//                .defaultDisplayImageOptions(mOptions)
//                .threadPoolSize(4)
//                .memoryCacheExtraOptions(480,800)
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .memoryCacheSize(5*1024*1024)
//                .memoryCache(new LruMemoryCache(50*1024*1024))
//                .writeDebugLogs()
//                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        ImageLoader.getInstance().init(mConfiguration);
//
//    }
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Picasso.Builder picassoBuilider=new Picasso.Builder(context);
//        Picasso picasso=picassoBuilider.build();

       ViewHolder holder;
       if(convertView==null){
           mInflater=LayoutInflater.from(context);
           convertView=mInflater.inflate(R.layout.news_item,null);
           holder=new ViewHolder();
           holder.iv=convertView.findViewById(R.id.iv);
           holder.tv=convertView.findViewById(R.id.tv);
           convertView.setTag(holder);

       }
       else {
           holder= (ViewHolder) convertView.getTag();
       }

       if (!newsList.get(position).getIcon_url().equals("")) {
//           picasso.load(newsList.get(position).getIcon_url())
//                   .placeholder(R.drawable.qau)
//                   .error(R.drawable.qau)
//                   .centerCrop()
//                   .fit()
//                   .into(holder.iv);
          // ImageAware imageAware=new ImageAware(holder.iv,false);
          // ImageLoader.getInstance().displayImage(newsList.get(position).getIcon_url(),holder.iv);
           Glide.with(context).load(newsList.get(position).getIcon_url()).placeholder(R.drawable.qau)
                   .centerCrop()
                   .crossFade()
                   .into(holder.iv);

       }else {
           holder.iv.setImageResource(R.drawable.qau);
       }




       holder.tv.setText(newsList.get(position).getTitle());
       return convertView;

    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}

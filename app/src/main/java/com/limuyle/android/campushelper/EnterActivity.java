package com.limuyle.android.campushelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EnterActivity extends FragmentActivity {
    private ViewPager vp;
  private   List<Fragment> list;
  private PagerTabStrip navigation;
    private String [] tabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
       vp=findViewById(R.id.vp);
       FragmentManager manager=getSupportFragmentManager();

        list=new ArrayList<>();
        list.add(StoryFragment.newInstance());
        list.add(VideoFragment.newInstance());
       navigation=findViewById(R.id.navigation);
       tabNames=getResources().getStringArray(R.array.tabs);
        MyAdapter adapter=new MyAdapter(manager);
        vp.setAdapter(adapter);

    }
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return  list.get(position);
        }

        @Override
        public int getCount() {
            return list!=null?list.size():0;
        }
        @Override
        public CharSequence getPageTitle(int position){
            return  tabNames[position];
        }
    }

}

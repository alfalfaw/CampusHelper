package com.limuyle.android.campushelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar mBottomNavigationBar;
    private int lastSelect=0;

    private Fragment newsListFragment,contactsListFragment,toolsFragment,aboutFragment;
   private ArrayList<Fragment> fragments;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            lastSelect=savedInstanceState.getInt("INDEX",0);
        }
        setContentView(R.layout.activity_main);
        mBottomNavigationBar=findViewById(R.id.navigation);
        builder = new AlertDialog.Builder(this);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.news,"新闻").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.contact,"聊天").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.tool,"工具").setActiveColor(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_help_white_24dp,"帮助").setActiveColor(R.color.colorPrimary))
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setFirstSelectedPosition(lastSelect)
                .initialise();
        fragments=new ArrayList<>();
        fragments=getFragments();
        setDefaultFragment(lastSelect);
        mBottomNavigationBar.setTabSelectedListener(this);

        builder.setTitle("温馨提示:").setIcon(android.R.drawable.btn_star).setMessage("确定要退出吗?").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(MainActivity.this,"You click back!",Toast.LENGTH_SHORT).show();
        // finish();
                System.exit(0);
            }
        }).setNegativeButton("否", null);
        dialog = builder.create();


    }
//
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(NewsListFragment.newInstance());
        fragmentList.add(ContactsFragment.newInstance());
        fragmentList.add(ToolsFragment.newInstance());
        fragmentList.add(AboutFragment.newInstance());
        return  fragmentList;
    }

    private void setDefaultFragment(int lastSelect) {
        //hideFragments();
       // newsListFragment=NewsListFragment.newInstance();
        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        if(transaction==null) {
            transaction.add(R.id.fragment_container, fragments.get(lastSelect),fragments.get(lastSelect).getClass().getName()).show(fragments.get(lastSelect)).commit();
        }else {
            transaction.replace(R.id.fragment_container,fragments.get(lastSelect)).show(fragments.get(lastSelect)).commit();
        }
//        for(int i=0;i<fragments.size();i++){
//            transaction.add(R.id.fragment_container,fragments.get(i));
//        }
       // hideFragments(lastSelect);

//        manager=getFragmentManager();
//        transaction=manager.beginTransaction();
//        transaction.show(fragments.get(lastSelect)).commit();




    }

    @Override
    public void onTabSelected(int position) {

       // hideFragments(lastSelect);

        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        if(lastSelect!=position) {
            if (!fragments.get(position).isAdded()) {
                transaction.hide(fragments.get(lastSelect)).add(R.id.fragment_container, fragments.get(position)).show(fragments.get(position)).commit();
            } else {
                transaction.hide(fragments.get(lastSelect)).show(fragments.get(position)).commit();
            }
        }
            lastSelect = position;

//manager.findFragmentByTag(fragments.get(lastSelect).getClass().getName());

//        if (manager.findFragmentByTag(fragments.get(position).getClass().getName()). {
//            if (manager.findFragmentByTag(fragments.getClass().getName()).isHidden()) {
      //  if(fragments.get(poman).commit();
      //  }else{
//                transaction.hide(manager.findFragmentByTag(fragments.get(lastSelect).getClass().getName())).show(fragments.get(position)).commit();
//                lastSelect=position;
          // }
    //}
      //  }else {
            //if(!manager.findFragmentByTag(fragments.get(position).getClass().getName()).isAdded()) {
             //   transaction.add(R.id.fragment_container, fragments.get(position),fragments.get(position).getClass().getName()).show(fragments.get(position)).commit();


        }


//        switch (position){
//            case 0:
//                lastSelect=0;
//                if (fragments.get(position).isHidden()){
//                    transaction.show(fragments.get(position)).commit();
//                }else{
//                    transaction.add(R.id.fragment_container,fragments.get(position)).commit();
//                }
//                break;
//            case 1:
//                hideFragments(0);
//                lastSelect=1;
//
//                    transaction.add(R.id.fragment_container, fragments.get(position)).commit();
//
//                break;
//            case 2:
//                hideFragments(0);
//                lastSelect=2;
//
//
//                    transaction.add(R.id.fragment_container,fragments.get(position)).commit();
//
//                break;
//            case 3:
//                hideFragments(0);
//                lastSelect=3;
//
//
//                    transaction.add(R.id.fragment_container,fragments.get(position)).commit();
//
//
//                break;
//        }
//        if(fragments!=null){
//            if(position<fragments.size()){
//                Fragment fragment=fragments.get(position);
//                if(!fragment.isAdded()){
//                    transaction.replace(R.id.fragment_container,fragment).commit();
//                }
//                transaction.commitAllowingStateLoss();
//            }
//        }

   // }

//    private void hideFragments(int lastSelect) {
//        manager=getFragmentManager();
//        transaction=manager.beginTransaction();
//
//        for (int i=0;i<fragments.size();i++){
//
//            if(i!=lastSelect&&!fragments.get(i).isHidden()){
//                transaction.hide(fragments.get(i));
//            }
//        }
//        transaction.commit();
//    }

//    private void hideFragments(int position) {
//        if (fragments.get(position).isAdded()) {
//            transaction.hide(fragments.get(position));
//
//        }
//    }

    @Override
    public void onTabUnselected(int position) {
//        FragmentManager manager=getFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        if(fragments!=null){
//            if(position<fragments.size()){
//                Fragment fragment=fragments.get(position);
//                transaction.remove(fragment);
//                transaction.commitAllowingStateLoss();
//            }
//        }

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if ((keyCode == KeyEvent.KEYCODE_BACK) && (!dialog.isShowing())) {
                dialog.show();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {

            super.onSaveInstanceState(saveInstanceState);
            saveInstanceState.putInt("INDEX",lastSelect);
        }
    }


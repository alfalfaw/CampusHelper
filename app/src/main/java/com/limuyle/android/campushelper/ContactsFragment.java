package com.limuyle.android.campushelper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limuyle on 2018/3/15.
 */

public  class ContactsFragment extends Fragment{
    ListView contacts_list;
    ArrayList<Map<String ,Object>> list=new ArrayList<>() ;
    private String[] names={"图灵机器人","茉莉机器人"};
    private int[] imgs={R.drawable.turing,R.drawable.moli};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.contacts_fragment,container,false);
    contacts_list=view.findViewById(R.id.contacts_list);


        return view;
    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        for(int i=0;i<2;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("pic",imgs[i]);
            map.put("nam",names[i]);
            list.add(map);
        }
       contacts_list.setAdapter(new SimpleAdapter(getActivity(),list,R.layout.contact_item,new String[]{"pic","nam"},new int[]{R.id.iv_contact,R.id.tv_name}));
contacts_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),ChatActivity.class);
        intent.putExtra("robot_id",position);
        startActivity(intent);
    }
});
        super.onActivityCreated(saveInstanceState);
    }
    public static Fragment newInstance() {
        return new ContactsFragment();
    }
}

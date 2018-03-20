package com.limuyle.android.campushelper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by limuyle on 2018/3/15.
 */

public class AboutFragment extends Fragment{
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.about_fragment,container,false);

        fab=(FloatingActionButton) view.findViewById(R.id.fab);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "欢迎!", Snackbar.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),EnterActivity.class));
            }


        }
        );

        super.onActivityCreated(saveInstanceState);
    }

    public static Fragment newInstance() {
        AboutFragment fragment=new AboutFragment();

        return fragment;
    }
}

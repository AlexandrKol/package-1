package com.gamoshi.app.fr;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;
import com.gamoshi.app.adapt.BlockView;


import java.io.File;
import java.io.IOException;

public class Page_3 extends Fragment {
    LinearLayout root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getPreferences(0);
        root= getView().findViewById(R.id.root);
        root.removeAllViews();
        BlockView blockView = new BlockView(getActivity());
        blockView.setLoadTitle(null,"Page 3",null,false);
        root.addView(blockView);
        try {
            int count =   pref.getInt("count_block",6);
            if( count > 6)  count = 6;
            String[] Sp = Home_Page.data.split("\n\n");
            for (int i =0;i<count;i++){
                BlockView blockView1 = new BlockView(getActivity());
                blockView1.setLoad((ActivityMain1) getActivity(),"    " + Sp[1] +"\n",null,false);
                root.addView(blockView1);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}

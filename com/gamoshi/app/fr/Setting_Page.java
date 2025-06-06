package com.gamoshi.app.fr;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;
import com.gamoshi.app.adapt.BlockView;
import com.google.android.material.textfield.TextInputLayout;

import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.api.data.InitializationStatus;

public class Setting_Page extends Fragment {
    LinearLayout root;
    private EditText   current_request , server_adr , count_block;
    SharedPreferences preferences;
    ActivityMain1 actiivity;
    CheckBox ERROR,WARN,INFO,DEBUG,VERBOSE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        preferences = getActivity().getPreferences(0);
        actiivity = (ActivityMain1) getActivity();
        return inflater.inflate(R.layout.setting_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        server_adr = view.findViewById(R.id.server_adr);
        current_request = view.findViewById(R.id.current_request);
        count_block = view.findViewById(R.id.count_block);
        current_request.setText( preferences.getString("current_request","30168"));
        server_adr.setText(preferences.getString("server_adr","https://hb.xoalt.com/x-simb/"));
        count_block.setText(""+preferences.getInt("count_block",6));
        VERBOSE =view.findViewById(R.id.VERBOSE);
        DEBUG =view.findViewById(R.id.DEBUG);
        INFO =view.findViewById(R.id.INFO);
        WARN =view.findViewById(R.id.WARN);
        ERROR =view.findViewById(R.id.ERROR);
        VERBOSE.setChecked(preferences.getBoolean("VERBOSE",true));
        DEBUG.setChecked(preferences.getBoolean("DEBUG",true));
        INFO.setChecked(preferences.getBoolean("INFO",true));
        WARN.setChecked(preferences.getBoolean("WARN",true));
        ERROR.setChecked(preferences.getBoolean("ERROR",true));
        VERBOSE.setOnCheckedChangeListener(( compoundButton,  b)->{
            preferences.edit().putBoolean("VERBOSE",b).commit();
        });
        DEBUG.setOnCheckedChangeListener(( compoundButton,  b)->{
            preferences.edit().putBoolean("DEBUG",b).commit();
        });
        INFO.setOnCheckedChangeListener(( compoundButton,  b)->{
            preferences.edit().putBoolean("INFO",b).commit();
        });
        WARN.setOnCheckedChangeListener(( compoundButton,  b)->{
            preferences.edit().putBoolean("WARN",b).commit();
        });
        ERROR.setOnCheckedChangeListener(( compoundButton,  b)->{
            preferences.edit().putBoolean("ERROR",b).commit();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.edit().putString("current_request",current_request.getText().toString()).commit();
        preferences.edit().putString("server_adr",server_adr.getText().toString()).commit();
        preferences.edit().putInt("count_block",Integer.parseInt(count_block.getText().toString())).commit();
        PrebidMobile.initializeSdk(actiivity,preferences.getString("server_adr","https://hb.xoalt.com/x-simb/auction/"), status -> {
            if (status == InitializationStatus.SUCCEEDED) {

                actiivity.pageschange(1);


            }
        });
     }

    @Override
    public void onStart() {
        super.onStart();

      /*  root = getView().findViewById(R.id.root);
        root.removeAllViews();
        BlockView blockView = new BlockView(getActivity());
        blockView.setLoadTitle("Settings", "", false);
        root.addView(blockView);
        TextInputLayout full_request = new TextInputLayout(getActivity());
        full_request.addView(new EditText(getActivity()));
        full_request.getEditText().setHint("full request");
        root.addView(full_request);//*/
    }

}

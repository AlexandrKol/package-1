package com.gamoshi.app.fr;

import android.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PrebidSdk_Log extends Fragment {
    ActivityMain1 activity;
    File logfile;
    SpannableStringBuilder buildSpanned = new SpannableStringBuilder();
    TextView text;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        activity = (ActivityMain1) getActivity();
        logfile= new File(activity.getCacheDir(), "logfile");
        preferences = activity.getPreferences(0);
        return inflater.inflate(R.layout.prebidsdk_log,null);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text= view.findViewById(R.id.log);
        updateText();
        view.findViewById(R.id.clean).setOnClickListener(v->{
            clean();
            updateText();
        });
        view.findViewById(R.id.copy).setOnClickListener(v->{
           // text.setTextIsSelectable(true);
            ClipboardManager cm = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(text.getText().toString());
        });
    }

    private void clean() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(logfile, false);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
           // text.setText("");
            buildSpanned = new SpannableStringBuilder();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void updateText() {

        try {
            FileInputStream fileInputStream = new FileInputStream(logfile);
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            String s = new String(b);
         //  Log.e("jjjj" ,s);
           // text.setText(new String(b));
            fileInputStream.close();
            String[] sp = s.split("\n\n");
            for (String am : sp){
                if(am.startsWith("V") && !preferences.getBoolean("VERBOSE",true)) continue;
                if(am.startsWith("I") && !preferences.getBoolean("INFO",true)) continue;
                if(am.startsWith("D") && !preferences.getBoolean("DEBUG",true)) continue;
                if(am.startsWith("W") && !preferences.getBoolean("WARN",true)) continue;
                if(am.startsWith("E") && !preferences.getBoolean("ERROR",true)) continue;
                addedText(am);
            }
        } catch ( Exception e) {
           e.printStackTrace();
        }
    }

    private void addedText(String am) {
       // Log

        int st = buildSpanned.length();
        buildSpanned.append(am);
        buildSpanned.append("\n\n");
        if(am.startsWith("E"))
        buildSpanned.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.colorAccent)),   st,  buildSpanned.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       else if(am.startsWith("W"))
            buildSpanned.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.coloryYel)),   st,  buildSpanned.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else if(am.startsWith("I"))
            buildSpanned.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.teal_700)),   st,  buildSpanned.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else if(am.startsWith("D"))
            buildSpanned.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.purple_500)),   st,  buildSpanned.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            buildSpanned.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.colorBlack)),   st,  buildSpanned.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setText(buildSpanned, TextView.BufferType.SPANNABLE);
    }
}

package com.gamoshi.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;

import com.gamoshi.app.fr.Home_Page;
import com.gamoshi.app.fr.PageBook;
import com.gamoshi.app.fr.Page_1;
import com.gamoshi.app.fr.Page_2;
import com.gamoshi.app.fr.Page_3;
import com.gamoshi.app.fr.PrebidSdk_Log;
import com.gamoshi.app.fr.Setting;
import com.gamoshi.app.fr.Setting_Page;
import com.net.flood.LoadClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.api.data.InitializationStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ActivityMain1 extends Activity {
   String TAG = "PrebidCustomInit";
    public  JSONArray ob;
    public static ActivityMain1 a;
    public int item;
    private View menu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        @SuppressLint("ResourceAsColor") ColorDrawable dialogColor = new ColorDrawable(R.color.white);
        getWindow().setBackgroundDrawable(dialogColor);

a = this;
        File logfile = new File(getCacheDir(), "logfile");
        LogUtil.loggerFile = logfile;
        setContentView(R.layout.activity1);
        menu  = findViewById(R.id.menu);
        PopupMenu pm = new PopupMenu(this,menu);
        try {
            InputStream is = getAssets().open("IN-APP_SDK_demo.json");
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();

            JSONObject obg = new JSONObject(new String(b));
             ob = obg.getJSONArray("books");
            //new JSONObject( LoadClass.get().getRequestFile("https://gist.github.com/SRaylyan/4195e0086200d62b3b2e6de194817d68/raw/e141cb272ab56cc88c89635e8a2e0a0a218b23d8/IN-APP_SDK_demo.json",LoadClass.defaulHeader(),new File(getCacheDir(),"json")));
            Menu menux = pm.getMenu();
            menux.add(0,R.id.Home_Page ,Menu.NONE,"Home Page");
            for(int i = 0;i<ob.length();i++){
                menux.add(0,i ,Menu.NONE,"Page " + (i + 1));
            }
            menux.add(0,R.id.settings ,Menu.NONE,"Settings");
            menux.add(0,R.id.prebidsdk_log ,Menu.NONE,"Prebidsdk log");
    //  Log.e("dddd" , "" + ob);
        } catch (Exception e) {
            e.printStackTrace();
        }

       // pm.inflate(R.menu.menu2);
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.Home_Page){
                    pageschange(100);
              //  } else if (id == R.id.page_1) {
               //     pageschange(2);
              //  }  else if (id == R.id.page_2) {
              //      pageschange(3);
          //  }else if (id == R.id.page_3) {
              //      pageschange(4);
                }else if (id == R.id.settings) {
                    pageschange(500);
                }else if (id == R.id.prebidsdk_log) {
                    pageschange(600);
                } else{
                    pageschange(id);
                }
                return false;
            }
        });
        menu.setOnClickListener(v->{
            pm.show();
        });

      //  PrebidMobile.setAuctionSettingsId("0689a263-318d-448b-a3d4-b02e8a709d9d");
      //  PrebidMobile.setCustomStatusEndpoint("https://prebid-server-test-j.prebid.org/status");
      //  PrebidMobile.setShareGeoLocation(true);
        SharedPreferences pref =  getPreferences(0);
        PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
        PrebidMobile.initializeSdk(this,pref.getString("server_adr","https://hb.xoalt.com/x-simb/")
                ,status -> {
            if (status == InitializationStatus.SUCCEEDED) {
                Log.d(TAG, "SDK initialized successfully!");

                    pageschange(100);


            } else {
                Log.e(TAG, "SDK initialization error: $status\n${status.description}");
            }
        });
       //
    }

  public   void pageschange(int i){

        FragmentTransaction tr = getFragmentManager().beginTransaction();
        if(i == 100)tr.replace(R.id.r1,new Home_Page()).commit();
      //  else  if(i == 2)tr.replace(R.id.r1,new Page_1()).commit();
      //  else  if(i == 3)tr.replace(R.id.r1,new Page_2()).commit();
      //  else  if(i == 4)tr.replace(R.id.r1,new Page_3()).commit();
        else  if(i == 500){
            //  Log.e("mmm" , "" +(i / 0));
            PreferenceManager.setDefaultValues(this, R.xml.preferences_lbs, false);
            tr.replace(R.id.r1,new Setting_Page()).commit();
        }else  if(i == 600)tr.replace(R.id.r1,new PrebidSdk_Log()).commit();
        else {
            item = i;
            tr.replace(R.id.r1,new PageBook()).commit();
        }
    }
}

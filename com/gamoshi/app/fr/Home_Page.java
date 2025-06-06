package com.gamoshi.app.fr;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;
import com.gamoshi.app.adapt.BlockView;
import com.net.flood.LoadClass;


import org.prebid.mobile.rendering.networking.BaseNetworkTask;
import org.prebid.mobile.rendering.networking.BaseResponseHandler;
import org.prebid.mobile.rendering.utils.helpers.Utils;
import org.prebid.mobile.rendering.views.interstitial.InterstitialManager;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBanner;
import org.prebid.mobile.rendering.views.webview.WebViewBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Home_Page extends Fragment {

    LinearLayout root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       // defaultAdContainer = new PrebidWebViewBanner(getActivity(),new InterstitialManager(){

       // });
        return inflater.inflate(R.layout.home_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root= view.findViewById(R.id.root);
        root.removeAllViews();
        SharedPreferences pref = getActivity().getPreferences(0);
        BlockView blockView = new BlockView(getActivity());
        blockView.setLoadTitle((ActivityMain1) getActivity(),"home", pref.getString("current_request", "prebid-demo-banner-300-250"),false);
        root.addView(blockView);
        // BaseNetworkTask.GetUrlParams param = Utils.parseUrl("https://en.lipsum.com/feed/html");//new BaseNetworkTask.GetUrlParams();
      /*  File file = new File(getActivity().getCacheDir(),"tmpstatic");
        // LoadClass.get().postRequestString("https://en.lipsum.com/feed/html","",null,file);
       // String str = LoadClass.get().getRequestFile("https://en.lipsum.com/feed/html", null, file);
        if(!file.isFile()){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data.getBytes());
                fileOutputStream.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
        }
       //*/
        try {
            int count =   pref.getInt("count_block",6);
            if( count > 6)  count = 6;

            String[] Sp = data.split("\n\n");
            for (int i =0;i<count;i++){
                BlockView blockView1 = new BlockView(getActivity());
                blockView1.setLoad((ActivityMain1) getActivity(),"\n    " + Sp[1] +"\n","home");
                root.addView(blockView1);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       // Log.e("mkkkk", "mmmmmmmmmmmmmmmmmmmmm " + getView());
       // BaseResponseHandler handler = new BaseResponseHandler() {
       // };

    }
  public static   String data ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed suscipit purus sed libero facilisis fermentum. Suspendisse faucibus tincidunt lectus vel maximus. Aenean rutrum ex at pretium porta. Morbi pharetra suscipit varius. Suspendisse tempor, risus nec malesuada tempor, nulla leo fringilla nisl, suscipit pretium sem ante sodales risus. Nulla ultrices sodales arcu, ut maximus leo finibus quis. Curabitur et arcu nec sapien porttitor luctus nec a libero. Ut ut hendrerit neque, sit amet posuere metus.\n" +
            "\n" +
            "Duis bibendum mollis lobortis. Proin hendrerit nibh eget posuere malesuada. Nullam convallis velit sit amet tempus placerat. Ut malesuada porta nunc vitae finibus. In in vulputate mauris, non porta lectus. Aenean volutpat porttitor ipsum, at luctus elit faucibus quis. Cras ultrices tellus sed tortor congue, in dapibus nunc hendrerit. Proin ut nisi lobortis lectus faucibus laoreet. Nulla egestas nec elit non hendrerit.\n" +
            "\n" +
            "Curabitur condimentum, nisl maximus ullamcorper luctus, leo dolor tempor turpis, nec molestie quam augue maximus arcu. Praesent sed imperdiet mauris. Aliquam pharetra elit quis interdum ullamcorper. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec commodo, erat non faucibus tincidunt, urna neque rutrum diam, et bibendum leo felis id dui. Praesent fermentum orci at ipsum varius tincidunt. Phasellus sit amet convallis mi. Donec nec nunc at nisi condimentum tempus cursus ullamcorper nisl. Aliquam tempor vulputate interdum. Sed quis convallis orci. Maecenas egestas massa odio.\n" +
            "\n" +
            "Curabitur ornare mollis erat sit amet faucibus. Integer rutrum purus quis tellus consectetur, sit amet malesuada lacus scelerisque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer accumsan arcu vel augue ullamcorper tempus. Nulla elit turpis, tempor at scelerisque non, malesuada eu mi. Integer sed nibh eget urna imperdiet interdum ac non ipsum. Phasellus pretium et ligula id aliquam. Fusce eget purus mollis, imperdiet lorem nec, blandit eros. Nam tristique lacus justo, hendrerit pharetra ex ullamcorper nec. Etiam ex neque, rhoncus a sagittis sagittis, viverra a tortor.\n" +
            "\n" +
            "Quisque vestibulum pulvinar sem, placerat placerat erat pulvinar ultricies. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin interdum turpis vel nisi congue pretium. Phasellus ac efficitur nisl. Cras a nisl et eros fringilla bibendum bibendum eu lorem. Suspendisse tempus nisl quam, ut luctus turpis dapibus id. Morbi at purus odio. Sed viverra, mi ac imperdiet consequat, est lorem laoreet quam, sit amet eleifend tellus neque in enim. Nunc nec nibh fermentum, semper lectus sed, facilisis nulla. Suspendisse sit amet velit malesuada, cursus diam laoreet, pulvinar enim. Etiam sed tincidunt nunc. Maecenas vel scelerisque tortor. Nullam facilisis gravida orci, ut porttitor urna accumsan in.\n" +
            "\n" +
            "In porttitor felis id mauris lacinia ornare. Ut id tellus id nulla egestas varius ut quis dui. Nunc luctus est quis odio finibus, ac venenatis nibh ultrices. Sed at elit odio. Nullam volutpat tincidunt ultricies. Donec feugiat sapien et velit ultrices, eget fermentum mauris vehicula. Pellentesque libero eros, tincidunt in tristique in, porttitor ac orci. Ut enim risus, suscipit rutrum efficitur vel, efficitur sed augue. Nam accumsan gravida sem. Suspendisse non augue a tellus iaculis pellentesque.";
}

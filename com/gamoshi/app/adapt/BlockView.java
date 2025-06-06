package com.gamoshi.app.adapt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gamoshi.app.ActivityMain;
import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.prebid.mobile.BannerParameters;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.Signals;
import org.prebid.mobile.TargetingParams;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.configuration.AdUnitConfiguration;
import org.prebid.mobile.rendering.bidding.data.bid.BidResponse;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;
import org.prebid.mobile.rendering.bidding.loader.BidLoader;
import org.prebid.mobile.rendering.views.webview.AdWebView;
import org.prebid.mobile.rendering.views.webview.AdWebViewClient;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBanner;
import org.prebid.mobile.rendering.views.webview.PreloadManager;
import org.prebid.mobile.rendering.views.webview.WebViewBanner;
import org.prebid.mobile.rendering.views.webview.WebViewBase;

import java.util.ArrayList;

public class BlockView extends LinearLayout {
    ActivityMain1 activity;
    TextView title;

    PrebidWebViewBanner defaultAdContainer;

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public BlockView(Context context) {
        this(context, null);
    }
    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

    }
    public void setLoadTitle(ActivityMain1 a,String titles, String banner, boolean showbanner){
        activity = a;
         title = new TextView(getContext());
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        title.setText(titles);
        title.setTextColor(getResources().getColor(R.color.black));
        title.setGravity(Gravity.CENTER_HORIZONTAL);


      if(showbanner) {
         // addView(imageView);
          addBanner(banner ,titles);
      }else {
          addView(title);
      }
    }
    public void setLoad(ActivityMain1 a,String exampetext,String text){
        activity = a;
        title = new TextView(getContext());
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        title.setText(exampetext);
        addBanner(text, text);
    }
    public void setLoad(ActivityMain1 a,String text, String banner, boolean showbanner){
        activity = a;
        title = new TextView(getContext());
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        title.setText(text);


        if(showbanner)  {
            addBanner(banner, text);
        }else {

            addView(title);
        }
    }
    private void addBanner(String configIdold,String text) {
        AdUnitConfiguration configuration = new AdUnitConfiguration();
        SharedPreferences pref =  ActivityMain1.a.getPreferences(0);
        String configId =  pref.getString("current_request", "30168");
        org.prebid.mobile.BannerAdUnit adUnit= new org.prebid.mobile.BannerAdUnit(configId, 300, 250);
        adUnit.setAutoRefreshInterval(120);
        BannerParameters parameters = new BannerParameters();
        ArrayList<Signals.Api> arr = new ArrayList<>();
        arr.add(Signals.Api.MRAID_3);
        arr.add(Signals.Api.OMID_1);
        parameters.setApi(arr);

        adUnit.setBannerParameters(parameters);
      //   configuration.setConfigId(text);
        configuration.setIsOriginalAdUnit(true);


        ArraySet<String> value = new ArraySet<>();
        value.add(text  );
         TargetingParams.updateExtData("page",value);
        AdManagerAdRequest request= new AdManagerAdRequest.Builder().build();
        //  supportedAdObject(adUnit);
        adUnit.fetchDemand(request, resultCode->{

        }, createBidListener());
        addView(title);
        FrameLayout fr = new FrameLayout(getContext());
        defaultAdContainer = new PrebidWebViewBanner(getContext(), null);
        fr.addView(defaultAdContainer);
        LinearLayout.LayoutParams lp = new LayoutParams(-2,-2);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        fr.setLayoutParams(lp);
        if(! (getChildAt(1) instanceof FrameLayout)){
            addView(fr);
            defaultAdContainer.loadHTML(js,300,250);

        }
      //  BidLoader bidLoader = new BidLoader(
     //           configuration,
      //          createBidListener()
      //  );
      //  bidLoader.load();
    }


    BidRequesterListener createBidListener() {
        return new BidRequesterListener(){
            @Override
            public void onFetchCompleted(BidResponse response) {
                try {

                    JSONObject ob = new JSONObject(response.getWinningBidJson()).getJSONArray("seatbid").getJSONObject(0)
                            .getJSONArray("bid").getJSONObject(0);
                    String adm = ob.getString("adm");


                   // PrebidBidLoader
                    defaultAdContainer.loadHTML(adm.replace("<head>" ,"<head>"+ ActivityMain.s), 300,250);
                    AdWebView adView = (WebViewBanner) defaultAdContainer.getWebView();
                     adView.getAdWebViewClient().setadLoadedListener(new AdWebViewClient.AdLoadedListener() {
                         @Override
                         public void loadUrlmain(String url) {
                             LogUtil.debug(",bjbb", " url: " + url);
                             if (activity == null) activity = ActivityMain1.a;
                             Uri uri = Uri.parse(url);
                          String   EncodedPath =  uri.getEncodedPath();
                             Log.w("j  j", ""+ EncodedPath);
                             if(EncodedPath.contains("page")  ){
if(EncodedPath.contains("page1"))activity.pageschange(0);
else if(EncodedPath.contains("page2"))activity.pageschange(1);
else if(EncodedPath.contains("page3"))activity.pageschange(2);
                             }
                             else{
                                 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));
                                 activity.startActivity(browserIntent);
                             }
                            //  Log.w("j  j", ""+ EncodedPath);
                            // Log.w("j  j", ""+ uri.getQuery());
                            // Log.w("j  j", ""+ uri.getEncodedPath());
                            // Log.w("j  j", ""+ uri.getAuthority());
                            // defaultAdContainer.getWebView().loadUrl(url);
                         }
                     });
                // adView.setAdLoadedListener(url->{
                 //    Log.w("j  j", ""+ url);
                 //});
                //   ad.
                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }

            @Override
            public void onError(AdException exception) {
                exception.printStackTrace();
            }
        };
    }

    String js = "<html><body><div>\n" +

            "  <img src=\"data:image/png;base64, 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9qc2RMtF/zfpep12Op04VtMCmPW6QfYI1hr5vq9WqzVx9rTruqpWqzP3LNb98TLRm4Idx9GrV69mhijNx9AkReGZ5/YbnOet08PDw5lviObjZTYbwdoAo9HoxtmpRT+k7r/+AX7LWNc6xWoRLABmMOgOwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcCM/wH/6xl/fWd1gAAAAABJRU5ErkJggg==\" />\n" +
            "</div> </body></html>";
}

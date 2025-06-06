package com.gamoshi.app;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresFeature;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AppEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.BannerParameters;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.Signals;
import org.prebid.mobile.Util;
import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.configuration.AdUnitConfiguration;

import org.prebid.mobile.rendering.bidding.data.bid.BidResponse;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;
import org.prebid.mobile.rendering.bidding.loader.BidLoader;
import org.prebid.mobile.rendering.loading.TransactionManager;
import org.prebid.mobile.rendering.models.CreativeModel;
import org.prebid.mobile.rendering.models.HTMLCreative;
import org.prebid.mobile.rendering.mraid.methods.network.RedirectUrlListener;
import org.prebid.mobile.rendering.session.manager.OmAdSessionManager;
import org.prebid.mobile.rendering.video.OmEventTracker;
import org.prebid.mobile.rendering.views.interstitial.InterstitialManager;
import org.prebid.mobile.rendering.views.webview.AdWebView;
import org.prebid.mobile.rendering.views.webview.AdWebViewClient;
import org.prebid.mobile.rendering.views.webview.MraidEventsManager;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBanner;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBase;
import org.prebid.mobile.rendering.views.webview.PreloadManager;
import org.prebid.mobile.rendering.views.webview.WebViewBanner;
import org.prebid.mobile.rendering.views.webview.WebViewBase;
import org.prebid.mobile.rendering.views.webview.mraid.BaseJSInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMain extends Activity {
    AdUnitConfiguration configuration = new AdUnitConfiguration();
    WebViewBanner adView;
    PrebidWebViewBanner defaultAdContainer;
    FrameLayout root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        @SuppressLint("ResourceAsColor") ColorDrawable dialogColor = new ColorDrawable( R.color.purple_500);
        getWindow().setBackgroundDrawable(dialogColor);
        setContentView(R.layout.layout);
        root = findViewById(R.id.root);
        InterstitialManager menager= new InterstitialManager(){
            @Override
            public HTMLCreative getHtmlCreative() {
                return super.getHtmlCreative();
            }
        };
        defaultAdContainer = new PrebidWebViewBanner(getApplicationContext(),menager);




     /*  CreativeModel model= new CreativeModel(null,new OmEventTracker(){

       },null);
      // OmAdSessionManager sesionmanager = new OmAdSessionManager();
     //   HTMLCreative creactive = null;
        try {
            HTMLCreative    creactive = new HTMLCreative(getApplicationContext(),model,null,menager);
            defaultAdContainer.setCreative(creactive);
        } catch (AdException e) {
            throw new RuntimeException(e);
        }//*/

        findViewById(R.id.text).setOnClickListener(v->{
            loadBanner("test_placement_id");
        });
        new Thread(()->{

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
           // loadBanner();
        });//.start();

    }

    private void loadBanner(String configId) {
    //    R.string.gam_original_display_banner_300x250,
        //        AdFormat.DISPLAY_BANNER;
              //  IntegrationKind.GAM_ORIGINAL,
        //GamOriginalApiDisplayBanner300x250Activity::class.java,
    /*    TestCase test = new TestCase(R.string.gam_original_display_banner_300x250,
                AdFormat.DISPLAY_BANNER,
                IntegrationKind.GAM_ORIGINAL,
                GamOriginalApiDisplayBanner300x250Activity.class
                );
        TestCaseRepository.INSTANCE.setLastTestCase(test); //*/
       // startActivity(new Intent(this, test.getActivity()));


 //startService()
        new Handler(Looper.getMainLooper()).post(()->{

            org.prebid.mobile.BannerAdUnit adUnit= new org.prebid.mobile.BannerAdUnit("prebid-demo-banner-300-250", 300, 250);
            adUnit.setAutoRefreshInterval(100);
            BannerParameters parameters = new BannerParameters();
            ArrayList<Signals.Api> arr = new ArrayList<>();
            arr.add(Signals.Api.MRAID_3);
            arr.add(Signals.Api.OMID_1);
            parameters.setApi(arr);
            adUnit.setBannerParameters(parameters);
         // InterstitialManager manager = new InterstitialManager();
            //manager.

          // adView.setAdUnitId("/21808260008/prebid_demo_app_original_api_banner_300x250_order");
           // AdSize ad = new AdSize(300, 250);
           // adView.setAdSizes(ad);
           // adView.setAppEventListener(new ApthispEventListener() {
            //    @Override
             //   public void onAppEvent(@NonNull String s, @NonNull String s1) {
               //     Log.e("ddddd" ,""+ s +" " + s1);
               // }
            //});
            configuration.setConfigId(configId);
            configuration.setIsOriginalAdUnit(true);
            AdManagerAdRequest request= new AdManagerAdRequest.Builder().build();
          //  supportedAdObject(adUnit);
            adUnit.fetchDemand(request, resultCode -> {

                Log.e("ddd", "" + resultCode);
            }, new BidRequesterListener() {
                @Override
                public void onFetchCompleted(BidResponse response) {
                    try {

                        JSONObject ob = new JSONObject(response.getWinningBidJson()).getJSONArray("seatbid").getJSONObject(0)
                                .getJSONArray("bid").getJSONObject(0);
                        String adm = ob.getString("adm");
                        if(root.getChildCount()==0)    root.addView(defaultAdContainer);

                        // BaseJSInterface aa =
                        //   adView.setMRAIDInterface();
                        //  Log.e("ddddd" ,""+aa.getDefaultPosition());
                        //  Log.e("ddddd" ,""+ adm.replace("<script src=\"https://yzu77606.trafsync.com/js/mraid.js1\"></script>" ,s));
                        // adView.setScaleX(2);
                        // adView.setScaleY(2);
              /*  adView.setMraidAdAssetsLoadListener(new AdWebViewClient.AdAssetsLoadedListener() {
                        @Override
                        public void startLoadingAssets() {
                            Log.e("ddddd" ," startLoadingAssets"  );
                        }

                        @Override
                        public void adAssetsLoaded() {
                            Log.e("ddddd" ," adAssetsLoaded"  );
                        }

                        @Override
                        public void notifyMraidScriptInjected() {
                            Log.e("ddddd" ,"notifyMraidScriptInjected "  );
                        }
                    },s);//*/
                        //adView.setJSName("jsBridge");
                        // adView.initContainsIFrame(s);

                        defaultAdContainer.loadHTML(adm.replace("<head>" ,"<head>"+s), 300,250);

                        adView = (WebViewBanner) defaultAdContainer.getWebView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(AdException exception) {

                }
            });

           // BidLoader  bidLoader = new BidLoader(
          //          configuration,
          //          createBidListener(null)
          //  );
          //  bidLoader.load();

        });
        //*/

    }
    BidRequesterListener createBidListener() {
        return new BidRequesterListener() {
            @Override
            public void onFetchCompleted(BidResponse response) {
                try {

                    JSONObject ob = new JSONObject(response.getWinningBidJson()).getJSONArray("seatbid").getJSONObject(0)
                            .getJSONArray("bid").getJSONObject(0);
                    String adm = ob.getString("adm");
                   /* adView = new WebViewBanner(getApplicationContext(), s, 300, 250, new PreloadManager.PreloadedListener() {
                        @Override
                        public void preloaded(WebViewBase adBaseView) {
                            Log.e("ddddd", " " + adBaseView);
                        }
                    }, new MraidEventsManager.MraidListener() {
                        @Override
                        public void openExternalLink(String url) {
                            Log.e("ddddd", " " + url);
                        }

                        @Override
                        public void openMraidExternalLink(String url) {
                            Log.e("ddddd", " openMraidExternalLink  " + url);
                        }

                        @Override
                        public void onAdWebViewWindowFocusChanged(boolean hasFocus) {
                            Log.e("ddddd", " onAdWebViewWindowFocusChanged  " + hasFocus);
                        }

                        @Override
                        public void loadMraidExpandProperties() {
                            Log.e("ddddd", "  loadMraidExpandProperties  " );
                        }
                    });//*/
                    //    adView.setVisibility(View.VISIBLE);
                    //   adView.setVisibility(View.GONE);
                    // adView.getSettings().setJavaScriptEnabled(true);
                    //  adView.loadData(s,"text/html", "utf-8");
                    // root.addView(adView,dp(300) , dp(250));
                if(root.getChildCount()==0)    root.addView(defaultAdContainer);

                   // BaseJSInterface aa =
                         //   adView.setMRAIDInterface();
                  //  Log.e("ddddd" ,""+aa.getDefaultPosition());
                    //  Log.e("ddddd" ,""+ adm.replace("<script src=\"https://yzu77606.trafsync.com/js/mraid.js1\"></script>" ,s));
                   // adView.setScaleX(2);
                   // adView.setScaleY(2);
              /*  adView.setMraidAdAssetsLoadListener(new AdWebViewClient.AdAssetsLoadedListener() {
                        @Override
                        public void startLoadingAssets() {
                            Log.e("ddddd" ," startLoadingAssets"  );
                        }

                        @Override
                        public void adAssetsLoaded() {
                            Log.e("ddddd" ," adAssetsLoaded"  );
                        }

                        @Override
                        public void notifyMraidScriptInjected() {
                            Log.e("ddddd" ,"notifyMraidScriptInjected "  );
                        }
                    },s);//*/
                    //adView.setJSName("jsBridge");
                   // adView.initContainsIFrame(s);

                    defaultAdContainer.loadHTML(adm.replace("<head>" ,"<head>"+s), 300,250);

                    adView = (WebViewBanner) defaultAdContainer.getWebView();
                //    Log.e("ddddd", " " + adm);
                 // adView.loadData(adm.replace("<head>" ,"<head>"+s)
                   //      ,"text/html", "utf-8");
                } catch (JSONException e) {
                   e.printStackTrace();
                }

               // HashMap<String, String> keywords = response.getTargeting();
               // Util.apply(keywords, adObject);
                //originalListener.onComplete(ResultCode.SUCCESS);

             //   registerVisibilityTrackerIfNeeded(bidResponse);
            }

            @Override
            public void onError(AdException exception) {
               // bidResponse = null;
                exception.printStackTrace();
               // Util.apply(null, adObject);
                //originalListener.onComplete(convertToResultCode(exception));
            }
        };
    }


    public   int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }
  public static   String s = "<script type=\"text/javascript\">; var printDebug = function(messageBuilder) {};\n" +
            "\n" +
            "(function() {\n" +
            "\n" +
            "    const LOG_TO_CONSOLE_ENABLE = true;\n" +
            "    const TAG = \"PREBID_MRAID_JS\";\n" +
            "\n" +
            "    var mraid = window.mraid = {};\n" +
            "    mraid.eventListeners = {};\n" +
            "    mraid.state = \"ready\";\n" +
            "    mraid.viewable = false;\n" +
            "    mraid.resizePropertiesInitialized = false;\n" +
            "    mraid.volumePercentage = null;\n" +
            "\n" +
            "    var nativeCallQueue = [];\n" +
            "    var nativeCallInFlight = false;\n" +
            "\n" +
            "    mraid.currentPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.maxSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.defaultPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.screenSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.allSupports = {\n" +
            "        sms: false,\n" +
            "        tel: false,\n" +
            "        calendar: false,\n" +
            "        storePicture: false,\n" +
            "        inlineVideo: false,\n" +
            "        vpaid: false\n" +
            "    };\n" +
            "\n" +
            "    if (LOG_TO_CONSOLE_ENABLE) {\n" +
            "        printDebug = function(messageBuilder) {\n" +
            "            var message = messageBuilder();\n" +
            "            console.log(TAG + ': ' + message);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    mraid.setCurrentPosition = function(x, y, width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setCurrentPosition');\n" +
            "\n" +
            "        mraid.currentPosition.x = x;\n" +
            "        mraid.currentPosition.y = y;\n" +
            "        mraid.currentPosition.width = width;\n" +
            "        mraid.currentPosition.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setMaxSize = function(width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setMaxSize');\n" +
            "\n" +
            "        mraid.maxSize.width = width;\n" +
            "        mraid.maxSize.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setDefaultPosition = function(x, y, width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setDefaultPosition');\n" +
            "\n" +
            "        mraid.defaultPosition.x = x;\n" +
            "        mraid.defaultPosition.y = y;\n" +
            "        mraid.defaultPosition.width = width;\n" +
            "        mraid.defaultPosition.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setScreenSize = function(width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setScreenSize');\n" +
            "\n" +
            "        mraid.screenSize.width = width;\n" +
            "        mraid.screenSize.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.orientationProperties = {\n" +
            "        allowOrientationChange: true,\n" +
            "        forceOrientation: \"none\"\n" +
            "    };\n" +
            "\n" +
            "    mraid.expandProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        useCustomClose: false\n" +
            "    };\n" +
            "\n" +
            "    mraid.resizeProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        customClosePosition: \"top-right\",\n" +
            "        offsetX: 0,\n" +
            "        offsetY: 0,\n" +
            "        allowOffscreen: true\n" +
            "    };\n" +
            "\n" +
            "     mraid.lastExposure = {\n" +
            "         exposedPercentage: 100.0,\n" +
            "         visibleRectangle: {\n" +
            "            x: 0.0,\n" +
            "            y: 0.0,\n" +
            "            width: 0.0,\n" +
            "            height: 0.0\n" +
            "         },\n" +
            "         occlusionRectangles: null\n" +
            "     };\n" +
            "\n" +
            "    var safeString = function(str) {\n" +
            "\n" +
            "        printDebug(() => 'safeString' + str);\n" +
            "        return str ? str : '';\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    var callContainer = function(command) {\n" +
            "        printDebug(() => 'callContainer ' + command);\n" +
            "        var args = Array.prototype.slice.call(arguments);\n" +
            "        args.shift();\n" +
            "\n" +
            "        if (nativeCallInFlight) {\n" +
            "            var nextCommand = {\n" +
            "              \"command\" : command,\n" +
            "              \"args\" : args\n" +
            "            };\n" +
            "            printDebug(() => 'callContainer nextCommand in queue ' + nextCommand);\n" +
            "            nativeCallQueue.push(nextCommand);\n" +
            "        } else {\n" +
            "            nativeCallInFlight = true;\n" +
            "            printDebug(() => 'callContainer executing immediately.');\n" +
            "            jsBridge[command].apply(jsBridge, args);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.open = function(url) {\n" +
            "        printDebug(() => 'open(' + url + ')');\n" +
            "\n" +
            "        callContainer('open', url);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.resize = function() {\n" +
            "        printDebug(() => 'resize');\n" +
            "        if(mraid.resizePropertiesInitialized) {\n" +
            "\n" +
            "            callContainer('resize');\n" +
            "\n" +
            "        } else {\n" +
            "\n" +
            "            mraid.onError(\"[width] and [height] parameters must be at least 1px. Set them using setResizeProperties() MRAID method.\", \"resize\");\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.expand = function(url) {\n" +
            "        printDebug(() => 'mraid.expand(' + url + ')');\n" +
            "\n" +
            "        if(url) {\n" +
            "            callContainer('expand', url);\n" +
            "        } else {\n" +
            "            callContainer('expand');\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.close = function() {\n" +
            "        printDebug(() => 'close');\n" +
            "        callContainer('close');\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getPlacementType = function() {\n" +
            "        printDebug(() => 'getPlacementType');\n" +
            "        return jsBridge.getPlacementType();\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getState = function() {\n" +
            "        printDebug(() => 'getState ' + mraid.state);\n" +
            "        return mraid.state;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getVersion = function() {\n" +
            "        printDebug(() => 'getVersion');\n" +
            "        return '3.0';\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.isViewable = function() {\n" +
            "\n" +
            "        printDebug(() => 'isViewable ' + mraid.viewable);\n" +
            "        return mraid.viewable;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getScreenSize = function() {\n" +
            "        printDebug(() => 'getScreenSize');\n" +
            "        return JSON.parse(jsBridge.getScreenSize());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getDefaultPosition = function() {\n" +
            "        printDebug(() => 'getDefaultPosition');\n" +
            "        return JSON.parse(jsBridge.getDefaultPosition());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getCurrentPosition = function() {\n" +
            "        printDebug(() => 'getCurrentPosition');\n" +
            "        return JSON.parse(jsBridge.getCurrentPosition());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getMaxSize = function() {\n" +
            "        printDebug(() => 'getMaxSize');\n" +
            "        return JSON.parse(jsBridge.getMaxSize());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.supports = function(feature) {\n" +
            "        printDebug(() => 'supports: ' + feature);\n" +
            "        printDebug(() => 'supports:regular call: ' + mraid.allSupports[feature]);\n" +
            "        return mraid.allSupports[feature];\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.playVideo = function(url) {\n" +
            "        printDebug(() => 'playVideo');\n" +
            "        callContainer('playVideo', url);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.addEventListener = function(event, listener) {\n" +
            "        printDebug(() => 'addEventListener: ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers == null) {\n" +
            "            handlers = mraid.eventListeners[event] = [];\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "            if(listener == handlers[handler]) {\n" +
            "                return;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        handlers.push(listener);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.removeEventListener = function(event, listener) {\n" +
            "        printDebug(() => 'removeEventListener: ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers != null) {\n" +
            "            for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "                if(handlers[handler] == listener) {\n" +
            "                    handlers.splice(handler, 1);\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.setOrientationProperties = function(properties) {\n" +
            "        printDebug(() => 'setOrientationProperties');\n" +
            "        if(!properties)\n" +
            "            return;\n" +
            "        var aoc = properties.allowOrientationChange;\n" +
            "        if(aoc === true || aoc === false) {\n" +
            "            mraid.orientationProperties.allowOrientationChange = aoc;\n" +
            "        }\n" +
            "\n" +
            "        var fo = properties.forceOrientation;\n" +
            "        if(fo == 'landscape' || fo == 'portrait' || fo == 'none') {\n" +
            "            mraid.orientationProperties.forceOrientation = fo;\n" +
            "        }\n" +
            "\n" +
            "        callContainer('onOrientationPropertiesChanged', JSON.stringify(mraid.getOrientationProperties()));\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getOrientationProperties = function() {\n" +
            "        printDebug(() => 'getOrientationProperties');\n" +
            "        return mraid.orientationProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getResizeProperties = function() {\n" +
            "        printDebug(() => 'getResizeProperties');\n" +
            "        return mraid.resizeProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getExpandProperties = function() {\n" +
            "        printDebug(() => 'getExpandProperties');\n" +
            "        mraid.expandProperties.isModal = true;\n" +
            "        return mraid.expandProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.setResizeProperties = function(properties) {\n" +
            "            printDebug(() => 'setResizeProperties ');\n" +
            "   \t\t    mraid.resizePropertiesInitialized = false;\n" +
            "\n" +
            "    \t\tif (!properties) {\n" +
            "    \t\t\tmraid.onError(\"properties is null\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Allow Offscreen\n" +
            "    \t\tif (typeof(properties.allowOffscreen) !== \"boolean\") {\n" +
            "    \t\t\tmraid.onError(\"allowOffscreen param of [\" + properties.allowOffscreen + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tvar allowOffscreen = properties.allowOffscreen\n" +
            "\n" +
            "    \t\t//Get max size\n" +
            "    \t\tvar maxSize = mraid.getMaxSize();\n" +
            "    \t\tif (!maxSize || !maxSize.width || !maxSize.height) {\n" +
            "    \t\t\tmraid.onError(\"Unable to use maxSize of [\" + JSON.stringify(maxSize) + \"]\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Width\n" +
            "    \t\tif (properties.width == null || typeof properties.width === 'undefined' || isNaN(properties.width)) {\n" +
            "    \t\t\tmraid.onError(\"width param of [\" + properties.width + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.width < 50 || (properties.width > maxSize.width && !allowOffscreen)) {\n" +
            "    \t\t\tmraid.onError(\"width param of [\" + properties.width + \"] outside of acceptable range of 50 to \" + maxSize.width, \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Height\n" +
            "    \t\tif (properties.height == null || typeof properties.height === 'undefined' || isNaN(properties.height)) {\n" +
            "    \t\t\tmraid.onError(\"height param of [\" + properties.height + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.height < 50 || (properties.height > maxSize.height && !allowOffscreen)) {\n" +
            "    \t\t\tmraid.onError(\"height param of [\" + properties.height + \"] outside of acceptable range of 50 to \" + maxSize.height, \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Offset\n" +
            "    \t\tif (properties.offsetX == null || typeof properties.offsetX === 'undefined' || isNaN(properties.offsetX)) {\n" +
            "    \t\t\tmraid.onError(\"offsetX param of [\" + properties.offsetX + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.offsetY == null || typeof properties.offsetY === 'undefined' || isNaN(properties.offsetY)) {\n" +
            "    \t\t\tmraid.onError(\"offsetY param of [\" + properties.offsetY + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "\n" +
            "    \t\tmraid.resizeProperties.width = properties.width;\n" +
            "    \t\tmraid.resizeProperties.height = properties.height;\n" +
            "    \t\tmraid.resizeProperties.customClosePosition = properties.customClosePosition;\n" +
            "    \t\tmraid.resizeProperties.offsetX = properties.offsetX;\n" +
            "    \t\tmraid.resizeProperties.offsetY = properties.offsetY;\n" +
            "    \t\tmraid.resizeProperties.allowOffscreen = properties.allowOffscreen;\n" +
            "\n" +
            "    \t\tmraid.resizePropertiesInitialized = true;\n" +
            "    \t};\n" +
            "\n" +
            "\n" +
            "    mraid.setExpandProperties = function(properties) {\n" +
            "\n" +
            "        printDebug(() => 'setExpandProperties');\n" +
            "\n" +
            "        if(properties && properties.width != null && typeof properties.width !== 'undefined' && !isNaN(properties.width)) {\n" +
            "            mraid.expandProperties.width = properties.width;\n" +
            "        }\n" +
            "\n" +
            "        if(properties && properties.height != null && typeof properties.height !== 'undefined' && !isNaN(properties.height)) {\n" +
            "            mraid.expandProperties.height = properties.height;\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.useCustomClose = function(useCustomClose) {\n" +
            "        printDebug(() => 'DEPRECATED useCustomClose' + useCustomClose);\n" +
            "        if(useCustomClose == true || useCustomClose == false) {\n" +
            "            mraid.expandProperties.useCustomClose = useCustomClose;\n" +
            "            //also call container's\n" +
            "            callContainer('shouldUseCustomClose', useCustomClose);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.fireEvent = function(event, args) {\n" +
            "        printDebug(() => 'fireEvent ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers == null) {\n" +
            "            return;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "            if(event == 'ready') {\n" +
            "                handlers[handler]();\n" +
            "            } else if(event == 'error') {\n" +
            "                handlers[handler](args[0], args[1]);\n" +
            "            } else if(event == 'stateChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'viewableChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'sizeChange') {\n" +
            "                handlers[handler](args[0], args[1]);\n" +
            "            } else if(event == 'audioVolumeChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'exposureChange') {\n" +
            "                handlers[handler](mraid.lastExposure.exposedPercentage,\n" +
            "                 mraid.lastExposure.visibleRectangle,\n" +
            "                 mraid.lastExposure.occlusionRectangles);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.createCalendarEvent = function(parameters) {\n" +
            "        printDebug(() => 'createCalendarEvent');\n" +
            "        callContainer('createCalendarEvent', JSON.stringify(parameters));\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.storePicture = function(url) {\n" +
            "        printDebug(() => 'storePicture');\n" +
            "        callContainer('storePicture', url);\n" +
            "    };\n" +
            "\n" +
            "    mraid.onError = function(message, action) {\n" +
            "        printDebug(() => 'onError' + message);\n" +
            "        mraid.fireEvent(\"error\", [message, action]);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.onReady = function() {\n" +
            "        printDebug(() => 'ready');\n" +
            "\n" +
            "        mraid.fireEvent(\"ready\");\n" +
            "    };\n" +
            "\n" +
            "    mraid.onReadyExpanded = function() {\n" +
            "        printDebug(() => 'onReadyExpanded');\n" +
            "        mraid.fireEvent(\"ready\");\n" +
            "    };\n" +
            "\n" +
            "    mraid.onSizeChange = function(width, height) {\n" +
            "        printDebug(() => 'onSizeChange: width ' + width + 'height: ' + height);\n" +
            "        mraid.fireEvent(\"sizeChange\", [width, height]);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.onStateChange = function(state) {\n" +
            "        printDebug(() => 'onStateChange' + state);\n" +
            "        mraid.state = state;\n" +
            "        mraid.fireEvent(\"stateChange\", mraid.getState());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "    * @deprecated Since MRAID 3. Use onExposureChange instead.\n" +
            "    */\n" +
            "    mraid.onViewableChange = function(isViewable) {\n" +
            "        printDebug(() => 'onViewableChange');\n" +
            "\n" +
            "        mraid.viewable = isViewable;\n" +
            "        mraid.fireEvent(\"viewableChange\", mraid.isViewable());\n" +
            "    };\n" +
            "\n" +
            "    mraid.getLocation = function() {\n" +
            "        printDebug(() => 'getLocation');\n" +
            "        return JSON.parse(jsBridge.getLocation());\n" +
            "    };\n" +
            "\n" +
            "    mraid.getCurrentAppOrientation = function() {\n" +
            "        printDebug(() => 'getCurrentAppOrientation');\n" +
            "        return JSON.parse(jsBridge.getCurrentAppOrientation());\n" +
            "    };\n" +
            "\n" +
            "    mraid.unload = function() {\n" +
            "        printDebug(() => 'unload');\n" +
            "        callContainer('unload');\n" +
            "    };\n" +
            "\n" +
            "    mraid.onExposureChange = function(viewExposureString) {\n" +
            "        printDebug(() => 'onExposureChange ' + viewExposureString);\n" +
            "        var viewExposure = JSON.parse(viewExposureString);\n" +
            "        mraid.lastExposure = viewExposure;\n" +
            "        mraid.fireEvent(\"exposureChange\");\n" +
            "    };\n" +
            "\n" +
            "    /*\n" +
            "        This event fires on changing of the audio volume.\n" +
            "        See https://www.iab.com/wp-content/uploads/2017/07/MRAID_3.0_FINAL.pdf , par. 7.6\n" +
            "\n" +
            "        @volumePercentage -  percentage of maximum audio playback\n" +
            "                             volume, a floating-point number between 0.0 and 100.0, or 0.0\n" +
            "                             if playback is not allowed, or null if volume can’t be\n" +
            "                             determined\n" +
            "    */\n" +
            "    mraid.onAudioVolumeChange = function(newVolumePercentage) {\n" +
            "            printDebug(() => 'onAudioVolumeChange(' + newVolumePercentage + ')');\n" +
            "            mraid.volumePercentage = newVolumePercentage;\n" +
            "            mraid.fireEvent(\"audioVolumeChange\", newVolumePercentage);\n" +
            "    };\n" +
            "\n" +
            "    mraid.nativeCallComplete = function() {\n" +
            "      if (nativeCallQueue.length === 0) {\n" +
            "        nativeCallInFlight = false;\n" +
            "        return;\n" +
            "     }\n" +
            "\n" +
            "      var nextCall = nativeCallQueue.shift();\n" +
            "      printDebug(() => 'nativeCallComplete nextCall: ' + nextCall.command + \" arg \" + nextCall.args);\n" +
            "\n" +
            "      jsBridge[nextCall.command].apply(jsBridge, nextCall.args);\n" +
            "    };\n" +
            "\n" +
            "}());</script>";
}

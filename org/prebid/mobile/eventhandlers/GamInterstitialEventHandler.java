
package org.prebid.mobile.eventhandlers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import org.prebid.mobile.LogUtil;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.eventhandlers.global.Constants;
import org.prebid.mobile.rendering.bidding.data.bid.Bid;
import org.prebid.mobile.rendering.bidding.interfaces.InterstitialEventHandler;
import org.prebid.mobile.rendering.bidding.listeners.InterstitialEventListener;

import java.lang.ref.WeakReference;

/**
 * Interstitial event handler for communication between Prebid rendering API and the GAM SDK.
 */
public class GamInterstitialEventHandler implements InterstitialEventHandler, GamAdEventListener {

    private static final String TAG = GamInterstitialEventHandler.class.getSimpleName();

    private static final long TIMEOUT_APP_EVENT_MS = 600;

    private PublisherInterstitialAdWrapper requestInterstitial;

    private final WeakReference<Activity> activityWeakReference;
    private final String gamAdUnitId;

    private InterstitialEventListener interstitialEventListener;
    private Handler appEventHandler;

    private boolean isExpectingAppEvent;
    private boolean didNotifiedBidWin;

    public GamInterstitialEventHandler(
            Activity activity,
            String gamAdUnitId
    ) {
        activityWeakReference = new WeakReference<>(activity);
        this.gamAdUnitId = gamAdUnitId;
    }

    //region ==================== GAM AppEventsListener Implementation
    @Override
    public void onEvent(AdEvent adEvent) {
        switch (adEvent) {
            case APP_EVENT_RECEIVED:
                handleAppEvent();
                break;
            case CLOSED:
                interstitialEventListener.onAdClosed();
                break;
            case FAILED:
                handleAdFailure(adEvent.getErrorCode());
                break;
            case DISPLAYED:
                interstitialEventListener.onAdDisplayed();
                break;
            case LOADED:
                primaryAdReceived();
                break;
        }
    }
    //endregion ==================== GAM AppEventsListener Implementation

    //region ==================== EventHandler Implementation
    @Override
    public void show() {
        if (requestInterstitial != null && requestInterstitial.isLoaded()) {
            requestInterstitial.show();
        } else {
            interstitialEventListener.onAdFailed(new AdException(
                    AdException.THIRD_PARTY,
                    "GAM SDK - failed to display ad."
            ));
        }
    }

    @Override
    public void setInterstitialEventListener(
            @NonNull
            InterstitialEventListener interstitialEventListener) {
        this.interstitialEventListener = interstitialEventListener;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void requestAdWithBid(Bid bid) {
        isExpectingAppEvent = false;
        didNotifiedBidWin = false;

        initPublisherInterstitialAd();

        if (bid != null && bid.getPrice() > 0) {
            isExpectingAppEvent = true;
        }

        if (requestInterstitial == null) {
            handleAdFailure(Constants.ERROR_CODE_INTERNAL_ERROR);
            return;
        }

        requestInterstitial.loadAd(bid);
    }

    @Override
    public void trackImpression() {

    }

    @Override
    public void destroy() {
        cancelTimer();
    }
    //endregion ==================== EventHandler Implementation

    private void initPublisherInterstitialAd() {
        if (requestInterstitial != null) {
            requestInterstitial = null;
        }

        requestInterstitial = PublisherInterstitialAdWrapper.newInstance(
                activityWeakReference.get(),
                gamAdUnitId,
                this
        );
    }

    private void primaryAdReceived() {
        if (isExpectingAppEvent) {
            if (appEventHandler != null) {
                LogUtil.debug(TAG, "primaryAdReceived: AppEventTimer is not null. Skipping timer scheduling.");
                return;
            }

            scheduleTimer();
        } else if (!didNotifiedBidWin) {
            interstitialEventListener.onAdServerWin();
        }
    }

    private void handleAppEvent() {
        if (!isExpectingAppEvent) {
            LogUtil.debug(TAG, "appEventDetected: Skipping event handling. App event is not expected");
            return;
        }

        cancelTimer();
        isExpectingAppEvent = false;
        didNotifiedBidWin = true;
        interstitialEventListener.onPrebidSdkWin();
    }

    private void scheduleTimer() {
        cancelTimer();

        appEventHandler = new Handler(Looper.getMainLooper());
        appEventHandler.postDelayed(this::handleAppEventTimeout, TIMEOUT_APP_EVENT_MS);
    }

    private void cancelTimer() {
        if (appEventHandler != null) {
            appEventHandler.removeCallbacksAndMessages(null);
        }
        appEventHandler = null;
    }

    private void handleAppEventTimeout() {
        cancelTimer();
        isExpectingAppEvent = false;
        interstitialEventListener.onAdServerWin();
    }

    private void handleAdFailure(int errorCode) {
        switch (errorCode) {
            case Constants.ERROR_CODE_INTERNAL_ERROR:
                interstitialEventListener.onAdFailed(new AdException(
                        AdException.THIRD_PARTY,
                        "GAM SDK encountered an internal error."
                ));
                break;
            case Constants.ERROR_CODE_INVALID_REQUEST:
                interstitialEventListener.onAdFailed(new AdException(
                        AdException.THIRD_PARTY,
                        "GAM SDK - invalid request error."
                ));
                break;
            case Constants.ERROR_CODE_NETWORK_ERROR:
                interstitialEventListener.onAdFailed(new AdException(
                        AdException.THIRD_PARTY,
                        "GAM SDK - network error."
                ));
                break;
            case Constants.ERROR_CODE_NO_FILL:
                interstitialEventListener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK - no fill."));
                break;
            default:
                interstitialEventListener.onAdFailed(new AdException(
                        AdException.THIRD_PARTY,
                        "GAM SDK - failed with errorCode: " + errorCode
                ));
        }
    }
}
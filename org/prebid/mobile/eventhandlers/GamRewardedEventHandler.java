

package org.prebid.mobile.eventhandlers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.ads.rewarded.RewardItem;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.eventhandlers.global.Constants;
import org.prebid.mobile.rendering.bidding.data.bid.Bid;
import org.prebid.mobile.rendering.bidding.interfaces.RewardedEventHandler;
import org.prebid.mobile.rendering.bidding.listeners.RewardedVideoEventListener;
import org.prebid.mobile.rendering.interstitial.rewarded.Reward;

import java.lang.ref.WeakReference;

/**
 * Rewarded event handler for communication between Prebid rendering API and the GAM SDK.
 */
public class GamRewardedEventHandler implements RewardedEventHandler, GamAdEventListener {

    private static final String TAG = GamRewardedEventHandler.class.getSimpleName();
    private static final long TIMEOUT_APP_EVENT_MS = 600;

    private RewardedAdWrapper rewardedAd;

    private final WeakReference<Activity> activityWeakReference;
    private final String gamAdUnitId;

    private RewardedVideoEventListener listener;
    private Handler appEventHandler;

    private boolean isExpectingAppEvent;
    private boolean didNotifiedBidWin;

    /**
     * Default constructor.
     *
     * @param activity    Android activity
     * @param gamAdUnitId the GAM ad unit id for the rewarded ad unit
     */
    public GamRewardedEventHandler(
            Activity activity,
            String gamAdUnitId
    ) {
        activityWeakReference = new WeakReference<>(activity);
        this.gamAdUnitId = gamAdUnitId;
    }

    //region ==================== EventListener Implementation
    @Override
    public void onEvent(AdEvent adEvent) {
        switch (adEvent) {
            case APP_EVENT_RECEIVED:
                handleAppEvent();
                break;
            case LOADED:
                primaryAdReceived();
                break;
            case DISPLAYED:
                listener.onAdDisplayed();
                break;
            case CLOSED:
                listener.onAdClosed();
                break;
            case FAILED:
                notifyErrorListener(adEvent.getErrorCode());
                break;
            case REWARD_EARNED:
                listener.onUserEarnedReward();
                break;
        }
    }
    //endregion ==================== EventListener Implementation

    //region ==================== EventHandler Implementation
    @Override
    public void setRewardedEventListener(
            @NonNull
            RewardedVideoEventListener listener) {
        this.listener = listener;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void requestAdWithBid(
            @Nullable
            Bid bid) {
        isExpectingAppEvent = false;
        didNotifiedBidWin = false;

        initPublisherRewardedAd();

        if (bid != null && bid.getPrice() > 0) {
            isExpectingAppEvent = true;
        }

        if (rewardedAd == null) {
            notifyErrorListener(Constants.ERROR_CODE_INTERNAL_ERROR);
            return;
        }

        rewardedAd.loadAd(bid);
    }

    @Override
    public void show() {
        if (rewardedAd != null && rewardedAd.isLoaded()) {
            rewardedAd.show(activityWeakReference.get());
        } else {
            listener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK - failed to display ad."));
        }
    }

    @Override
    public void trackImpression() {

    }

    @Override
    public void destroy() {
        cancelTimer();
    }
    //endregion ==================== EventHandler Implementation

    private void initPublisherRewardedAd() {
        rewardedAd = RewardedAdWrapper.newInstance(activityWeakReference.get(), gamAdUnitId, this);
    }

    private void primaryAdReceived() {
        if (isExpectingAppEvent) {
            if (appEventHandler != null) {
                LogUtil.debug(TAG, "primaryAdReceived: AppEventTimer is not null. Skipping timer scheduling.");
                return;
            }

            scheduleTimer();
        } else if (!didNotifiedBidWin) {
            listener.onAdServerWin(getRewardItem());
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
        listener.onPrebidSdkWin();
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
        listener.onAdServerWin(getRewardItem());
    }

    private RewardItem getRewardItem() {
        return rewardedAd != null ? rewardedAd.getRewardItem() : null;
    }

    @Nullable
    @Override
    public Reward getReward() {
        RewardItem rewardItem = getRewardItem();
        if (rewardItem == null) return null;

        return new Reward(rewardItem.getType(), rewardItem.getAmount(), null);
    }

    private void notifyErrorListener(int errorCode) {
        switch (errorCode) {
            case Constants.ERROR_CODE_INTERNAL_ERROR:
                listener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK encountered an internal error."));
                break;
            case Constants.ERROR_CODE_INVALID_REQUEST:
                listener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK - invalid request error."));
                break;
            case Constants.ERROR_CODE_NETWORK_ERROR:
                listener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK - network error."));
                break;
            case Constants.ERROR_CODE_NO_FILL:
                listener.onAdFailed(new AdException(AdException.THIRD_PARTY, "GAM SDK - no fill."));
                break;
            default:
                listener.onAdFailed(new AdException(
                        AdException.THIRD_PARTY,
                        "GAM SDK - failed with errorCode: " + errorCode
                ));
        }
    }
}
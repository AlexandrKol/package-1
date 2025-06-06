package org.prebid.mobile.admob

import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdView
import org.prebid.mobile.LogUtil
import org.prebid.mobile.rendering.bidding.data.bid.BidResponse
import org.prebid.mobile.rendering.bidding.display.PrebidMediationDelegate
import org.prebid.mobile.rendering.models.internal.VisibilityTrackerOption
import org.prebid.mobile.rendering.models.ntv.NativeEventTracker
import org.prebid.mobile.rendering.utils.helpers.VisibilityChecker
import java.lang.ref.WeakReference

/**
 * Banner mediation delegate.
 */
class AdMobMediationBannerUtils(
    private val extras: Bundle,
    adView: AdView
) : PrebidMediationDelegate {
    private val adView: WeakReference<AdView>

    init {
        this.adView = WeakReference(adView)
    }

    override fun setResponseToLocalExtras(response: BidResponse?) {
        if (response != null) {
            extras.putString(PrebidBannerAdapter.EXTRA_RESPONSE_ID, response.id)
        }
    }

    override fun canPerformRefresh(): Boolean {
        val view = adView.get()
        if (view == null) {
            LogUtil.error(TAG, "AdView is null, it can be destroyed as WeakReference")
            return false
        }
        val visibilityTrackerOption =
            VisibilityTrackerOption(NativeEventTracker.EventType.IMPRESSION)
        val checker = VisibilityChecker(visibilityTrackerOption)
        val isVisible = checker.isVisibleForRefresh(view)
        if (isVisible) {
            Log.d(TAG, "Visibility checker result: " + true)
        } else {
            Log.e(TAG, "Can't perform refresh. Ad view is not visible.")
        }
        return isVisible
    }

    override fun handleKeywordsUpdate(keywords: HashMap<String, String>?) {}

    companion object {
        private const val TAG = "BannerMediationUtils"
    }
}
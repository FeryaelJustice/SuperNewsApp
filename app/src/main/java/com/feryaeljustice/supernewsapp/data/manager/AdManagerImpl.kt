package com.feryaeljustice.supernewsapp.data.manager

import android.app.Activity
import android.content.Context
import android.util.Log
import com.feryaeljustice.supernewsapp.BuildConfig
import com.feryaeljustice.supernewsapp.domain.manager.AdManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdManagerImpl
@Inject
constructor(
    @param:ApplicationContext private val context: Context,
) : AdManager {

    private val tag = "AdManager"
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val isMobileAdsInitialized = AtomicBoolean(false)
    private val isAdLoading = AtomicBoolean(false)

    @Volatile
    private var interstitialAd: InterstitialAd? = null

    @Volatile
    private var lastAdShownTimestamp: Long = 0L
    private val minIntervalBetweenAdsMs: Long = 30_000L

    private val adUnitId: String
        get() = BuildConfig.ADMOB_INTERSTITIAL_AD_UNIT_ID

    override fun initialize(activity: Activity, onInitializationComplete: (() -> Unit)?) {
        scope.launch {
            val consentInformation = UserMessagingPlatform.getConsentInformation(activity)
            val consentParams = ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build()

            consentInformation.requestConsentInfoUpdate(
                activity,
                consentParams,
                {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                        if (formError != null) {
                            Log.w(tag, "Consent form error: [${formError.errorCode}] ${formError.message}")
                        }
                        if (consentInformation.canRequestAds()) {
                            setupMobileAds()
                        }
                        onInitializationComplete?.invoke()
                    }
                },
                { requestError ->
                    Log.w(tag, "Consent info update failed: [${requestError.errorCode}] ${requestError.message}")
                    if (consentInformation.canRequestAds()) {
                        setupMobileAds()
                    }
                    onInitializationComplete?.invoke()
                }
            )
        }
    }

    private fun setupMobileAds() {
        if (!isMobileAdsInitialized.compareAndSet(false, true)) {
            return
        }

        Log.i(tag, "Initializing Google Mobile Ads SDK (Test Mode: ${BuildConfig.IS_ADMOB_TEST_MODE})")
        MobileAds.initialize(context) { initializationStatus ->
            Log.i(tag, "Mobile Ads SDK initialized: $initializationStatus")
            loadInterstitialAd()
        }
    }

    override fun loadInterstitialAd() {
        if (!isMobileAdsInitialized.get()) {
            Log.d(tag, "Mobile Ads SDK is not initialized yet. Skipping preload.")
            return
        }

        if (interstitialAd != null) {
            Log.d(tag, "Interstitial Ad is already loaded and ready.")
            return
        }

        if (!isAdLoading.compareAndSet(false, true)) {
            Log.d(tag, "Interstitial Ad is currently loading.")
            return
        }

        scope.launch {
            val currentAdUnitId = adUnitId
            if (currentAdUnitId.isBlank()) {
                Log.w(tag, "AdUnit ID is blank. Make sure to configure ADMOB_INTERSTITIAL_AD_UNIT_ID.")
                isAdLoading.set(false)
                return@launch
            }

            Log.d(tag, "Loading Interstitial Ad with unit id: $currentAdUnitId")
            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(
                context,
                currentAdUnitId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                        isAdLoading.set(false)
                        Log.i(tag, "Interstitial Ad successfully loaded and cached.")
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        interstitialAd = null
                        isAdLoading.set(false)
                        Log.w(tag, "Failed to load Interstitial Ad: [${error.code}] ${error.message}")
                    }
                }
            )
        }
    }

    override fun showInterstitialAd(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        val currentAd = interstitialAd
        val currentTime = System.currentTimeMillis()
        val isCooldownActive = (currentTime - lastAdShownTimestamp) < minIntervalBetweenAdsMs

        if (currentAd == null || isCooldownActive) {
            Log.d(
                tag,
                "Skipping Interstitial (available=${currentAd != null}, cooldownActive=$isCooldownActive). Proceeding immediately."
            )
            if (currentAd == null) {
                loadInterstitialAd()
            }
            onAdDismissedOrSkipped()
            return
        }

        interstitialAd = null

        currentAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(tag, "Interstitial Ad was dismissed.")
                lastAdShownTimestamp = System.currentTimeMillis()
                loadInterstitialAd()
                onAdDismissedOrSkipped()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.w(tag, "Interstitial Ad failed to show: [${error.code}] ${error.message}")
                loadInterstitialAd()
                onAdDismissedOrSkipped()
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(tag, "Interstitial Ad displayed on screen.")
                lastAdShownTimestamp = System.currentTimeMillis()
            }
        }

        currentAd.show(activity)
    }

    override fun isInterstitialLoaded(): Boolean = interstitialAd != null
}

package com.feryaeljustice.supernewsapp.domain.manager

import android.app.Activity

interface AdManager {
    fun initialize(activity: Activity, onInitializationComplete: (() -> Unit)? = null)
    fun loadInterstitialAd()
    fun showInterstitialAd(activity: Activity, onAdDismissedOrSkipped: () -> Unit)
    fun isInterstitialLoaded(): Boolean
}

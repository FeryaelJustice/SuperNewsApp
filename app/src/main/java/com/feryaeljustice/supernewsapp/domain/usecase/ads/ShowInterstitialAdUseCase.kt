package com.feryaeljustice.supernewsapp.domain.usecase.ads

import android.app.Activity
import com.feryaeljustice.supernewsapp.domain.manager.AdManager
import javax.inject.Inject

class ShowInterstitialAdUseCase
@Inject
constructor(
    private val adManager: AdManager,
) {
    operator fun invoke(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        adManager.showInterstitialAd(activity, onAdDismissedOrSkipped)
    }
}

package com.feryaeljustice.supernewsapp.domain.usecase.ads

import android.app.Activity
import com.feryaeljustice.supernewsapp.domain.manager.AdManager
import javax.inject.Inject

class InitAdsUseCase
@Inject
constructor(
    private val adManager: AdManager,
) {
    operator fun invoke(activity: Activity, onComplete: (() -> Unit)? = null) {
        adManager.initialize(activity, onComplete)
    }
}

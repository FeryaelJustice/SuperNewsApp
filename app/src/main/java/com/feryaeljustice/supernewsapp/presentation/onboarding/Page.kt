package com.feryaeljustice.supernewsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.feryaeljustice.supernewsapp.R

data class Page(
    @param:StringRes val titleRes: Int,
    @param:StringRes val descriptionRes: Int,
    @param:DrawableRes val image: Int,
)

val pages =
    listOf(
        Page(
            titleRes = R.string.onboarding_title_1,
            descriptionRes = R.string.onboarding_desc_1,
            image = R.drawable.onboarding1,
        ),
        Page(
            titleRes = R.string.onboarding_title_2,
            descriptionRes = R.string.onboarding_desc_2,
            image = R.drawable.onboarding2,
        ),
        Page(
            titleRes = R.string.onboarding_title_3,
            descriptionRes = R.string.onboarding_desc_3,
            image = R.drawable.onboarding3,
        ),
    )

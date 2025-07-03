package com.feryaeljustice.supernewsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.feryaeljustice.supernewsapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages =
    listOf(
        Page(
            title = "Welcome to your favourite news app",
            description = "Read the latest news from around the world.",
            image = R.drawable.onboarding1,
        ),
        Page(
            title = "We will make you be informed of the most important news around the world",
            description = "No matter what is happening, you will be instantly informed.",
            image = R.drawable.onboarding2,
        ),
        Page(
            title = "If you like this app, please may I ask you to leave a review and recommend it!",
            description = "Thank you.",
            image = R.drawable.onboarding3,
        ),
    )

package com.feryaeljustice.supernewsapp.presentation.navigation

import com.feryaeljustice.supernewsapp.domain.model.Article
import kotlinx.serialization.Serializable

// import androidx.navigation.NamedNavArgument
// val arguments: List<NamedNavArgument> = emptyList()

@Serializable
sealed class Route {
    @Serializable
    data object OnBoardingScreen : Route()

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object ContactScreen : Route()

    @Serializable
    data object SearchScreen : Route()

    @Serializable
    data object BookmarkScreen : Route()

    @Serializable data class NewsDetailScreen(val article: Article) : Route()
    @Serializable
    data object AppStartNavigation : Route()

    @Serializable
    data object NewsNavigation : Route()

    @Serializable
    data object NewsNavigatorScreen : Route()
}

package com.feryaeljustice.supernewsapp.presentation.navigation

// import androidx.navigation.NamedNavArgument
// val arguments: List<NamedNavArgument> = emptyList()

sealed class Route(val route: String) {
    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object ContactScreen : Route(route = "contactScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object BookmarkScreen : Route(route = "bookmarkScreen")
    data object NewsDetailScreen : Route(route = "newsDetailScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object NewsNavigation : Route(route = "newsNavigation")
    data object NewsNavigatorScreen : Route(route = "newsNavigatorScreen")
}
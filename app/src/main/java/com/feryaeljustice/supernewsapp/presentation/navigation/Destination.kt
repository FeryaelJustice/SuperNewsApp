/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.presentation.navigation

import com.feryaeljustice.supernewsapp.R

enum class Destination(
    val route: Route,
    val icon: Int,
    val label: String,
    val contentDescription: String
) {
    Home(Route.HomeScreen, R.drawable.ic_home, "Home", "Home Screen"),
    Search(Route.SearchScreen, R.drawable.ic_search, "Search", "Search Screen"),
    Bookmark(Route.BookmarkScreen, R.drawable.ic_bookmark, "Bookmark", "Bookmark Screen"),
    Contact(Route.ContactScreen, R.drawable.ic_contact, "Contact", "Contact Screen")
}
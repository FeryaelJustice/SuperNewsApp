/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    // La lista almacena las pantallas activas.
    val backStack = mutableStateListOf<Route>()

    fun initialize(startRoute: Route) {
        if (backStack.isEmpty()) {
            backStack.add(startRoute)
        } else if (backStack.firstOrNull() != startRoute) {
            backStack.clear()
            backStack.add(startRoute)
        }
    }

    fun navigateTo(route: Route) {
        backStack.add(route)
    }

    // Comportamiento idéntico a launchSingleTop + popUpTo para las pestañas de la barra inferior
    fun navigateToTab(route: Route) {
        // Dejamos únicamente la pantalla inicial o limpiamos el histórico previo
        if (backStack.isNotEmpty()) {
            backStack.clear()
        }
        backStack.add(route)
    }

    fun popBackStack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeLast()
            return true
        }
        return false
    }
}
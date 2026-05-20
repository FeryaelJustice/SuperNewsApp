package com.feryaeljustice.supernewsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.feryaeljustice.supernewsapp.presentation.onboarding.OnBoardingScreen
import com.feryaeljustice.supernewsapp.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(startDestination: Route, navViewModel: NavigationViewModel = viewModel()) {
    LaunchedEffect(startDestination) {
        navViewModel.initialize(startDestination)
    }

    if (navViewModel.backStack.isEmpty()) return

    NavDisplay(
        backStack = navViewModel.backStack,
        onBack = { navViewModel.popBackStack() },
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator())
    ) { route ->
        when (route) {
            is Route.OnBoardingScreen, is Route.AppStartNavigation -> NavEntry(route) {
                val onboardingViewModel: OnBoardingViewModel = viewModel()
                OnBoardingScreen(
                    event = onboardingViewModel::onEvent,
                    onNavigate = {
                        navViewModel.navigateToTab(Route.NewsNavigatorScreen)
                    }
                )
            }

            else -> NavEntry(route){
                NewsNavigator(navigatorViewModel = navViewModel)
            }
        }
    }
}

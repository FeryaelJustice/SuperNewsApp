package com.feryaeljustice.supernewsapp.presentation.mainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeljustice.supernewsapp.domain.usecase.app_entry.ReadAppEntry
import com.feryaeljustice.supernewsapp.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    readAppEntry: ReadAppEntry,
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    val startDestination: StateFlow<Route?> = readAppEntry().map { shouldStartFromHomeScreen ->
        _splashCondition.value = false

        if (shouldStartFromHomeScreen) {
            Route.NewsNavigatorScreen
        } else {
            Route.OnBoardingScreen
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}

package com.feryaeljustice.supernewsapp.presentation.mainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeljustice.supernewsapp.domain.usecase.app_entry.ReadAppEntry
import com.feryaeljustice.supernewsapp.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    readAppEntry: ReadAppEntry,
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        readAppEntry()
            .onEach { shouldStartFromHomeScreen ->
                _startDestination.value =
                    if (shouldStartFromHomeScreen) {
                        Route.NewsNavigation.route
                    } else {
                        Route.AppStartNavigation.route
                    }
                delay(300)
                _splashCondition.value = false
            }.launchIn(viewModelScope)
    }
}

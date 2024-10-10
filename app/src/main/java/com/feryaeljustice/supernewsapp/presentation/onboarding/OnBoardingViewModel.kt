

package com.feryaeljustice.supernewsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeljustice.supernewsapp.domain.usecase.app_entry.SaveAppEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val saveAppEntry: SaveAppEntry) :
    ViewModel() {

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.SaveAppEntry -> {
                saveTheAppEntry()
            }
        }
    }

    private fun saveTheAppEntry() {
        viewModelScope.launch {
            saveAppEntry()
        }
    }
}
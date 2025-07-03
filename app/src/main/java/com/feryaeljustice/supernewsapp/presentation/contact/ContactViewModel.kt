package com.feryaeljustice.supernewsapp.presentation.contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel
@Inject
constructor() : ViewModel() {
    private val _state = mutableStateOf(ContactState())
    val state: State<ContactState> = _state
}

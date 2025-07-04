package com.feryaeljustice.supernewsapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.feryaeljustice.supernewsapp.domain.usecase.news.SearchNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchNewsUseCase: SearchNews,
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val searchQuery = state.value.searchQuery
        if (searchQuery.isNotEmpty()) {
            val articles =
                searchNewsUseCase(
                    searchQuery = searchQuery,
                    sources =
                        listOf(
                            "bbc-news",
                            "cnn",
                            "fox-news",
                            "google-news",
                            "reuters",
                        ),
                ).cachedIn(viewModelScope)
            _state.value = state.value.copy(articles = articles)
        }
    }
}

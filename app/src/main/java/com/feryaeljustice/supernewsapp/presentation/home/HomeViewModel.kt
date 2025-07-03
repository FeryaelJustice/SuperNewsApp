package com.feryaeljustice.supernewsapp.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.feryaeljustice.supernewsapp.annotations.DeeplApiKey
import com.feryaeljustice.supernewsapp.domain.usecase.news.GetNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    getNewsUseCase: GetNews,
    @param:DeeplApiKey private val deeplApiKey: String,
    private val locale: String,
) : ViewModel() {
    var state = mutableStateOf(HomeState())
        private set

    val news =
        getNewsUseCase(
            sources =
                listOf(
                    "bbc-news",
                    "cnn",
                    "fox-news",
                    "google-news",
                    "reuters",
                ),
        ).cachedIn(viewModelScope)

//    val translatedNews = news.map { pagingDataItem ->
//        pagingDataItem.filter { article ->
//            !article.title.isNullOrBlank() && !article.urlToImage.isNullOrBlank()
//        }.map { article ->
//            article.copy(
//                title = getTranslation(article.title!!, locale)
//            )
//        }
//    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }

    private fun updateScrollValue(newValue: Int) {
        state.value = state.value.copy(scrollValue = newValue)
    }

    private fun updateMaxScrollingValue(newValue: Int) {
        state.value = state.value.copy(maxScrollingValue = newValue)
    }

//    private suspend fun getTranslation(text: String, locale: String): String {
//        return withContext(Dispatchers.IO) {
//            val translator = Translator(deeplApiKey)
//            try {
//                val result = translator.translateText(text, null, locale)
//                result.text
//            } catch (e: Exception) {
//                Log.e("translation", e.message.toString())
//                ""
//            }
//        }
//    }
}

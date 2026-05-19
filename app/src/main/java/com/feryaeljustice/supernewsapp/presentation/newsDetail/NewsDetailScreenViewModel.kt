package com.feryaeljustice.supernewsapp.presentation.newsDetail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.usecase.news.DeleteArticle
import com.feryaeljustice.supernewsapp.domain.usecase.news.GetSavedArticle
import com.feryaeljustice.supernewsapp.domain.usecase.news.UpsertArticle
import com.feryaeljustice.supernewsapp.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailScreenViewModel
@Inject
constructor(
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val upsertArticleUseCase: UpsertArticle,
    @param:ApplicationContext private val application: Context,
) : ViewModel() {

    val state: StateFlow<NewsDetailState>
        field = MutableStateFlow(NewsDetailState())

    fun setArticle(article: Article) {
        state.update { it.copy(article = article) }
        viewModelScope.launch {
            checkIfArticleIsSaved()
        }
    }

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: NewsDetailEvent) {
        when (event) {
            is NewsDetailEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val savedArticle = getSavedArticleUseCase(url = event.article.url)
                    if (savedArticle == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                    checkIfArticleIsSaved()
                }
            }

            is NewsDetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is NewsDetailEvent.CheckIfArticleIsSaved -> {
                viewModelScope.launch {
                    checkIfArticleIsSaved()
                }
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(article = article)
        sideEffect = UIComponent.Toast(application.getString(R.string.article_deleted))
    }

    private suspend fun upsertArticle(article: Article) {
        upsertArticleUseCase(article = article)
        sideEffect = UIComponent.Toast(application.getString(R.string.article_inserted))
    }

    private suspend fun checkIfArticleIsSaved() {
        val currentArticle = state.value.article
        val isSaved = currentArticle?.let { getSavedArticleUseCase(url = it.url) != null } ?: false
        state.update { it.copy(isSaved = isSaved) }
    }
}

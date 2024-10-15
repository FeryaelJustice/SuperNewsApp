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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailScreenViewModel @Inject constructor(
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val upsertArticleUseCase: UpsertArticle,
    @ApplicationContext private val application: Context
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: NewsDetailEvent) {
        when (event) {
            is NewsDetailEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = getSavedArticleUseCase(url = event.article.url)
                    if (article == null) {
                        upsertArticle(article = event.article)
                    } else {
                        deleteArticle(article = event.article)
                    }
                }
            }

            is NewsDetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is NewsDetailEvent.CheckIfArticleIsSaved -> {
                viewModelScope.launch {
                    checkIfArticleIsSaved(event.article)
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

    suspend fun checkIfArticleIsSaved(article: Article): Boolean {
        return getSavedArticleUseCase(url = article.url) != null
    }
}


package com.feryaeljustice.supernewsapp.presentation.newsDetail

import com.feryaeljustice.supernewsapp.domain.model.Article

sealed class NewsDetailEvent {
    data class UpsertDeleteArticle(val article: Article) : NewsDetailEvent()

    data object RemoveSideEffect : NewsDetailEvent()

    data class CheckIfArticleIsSaved(val article: Article) : NewsDetailEvent()
}

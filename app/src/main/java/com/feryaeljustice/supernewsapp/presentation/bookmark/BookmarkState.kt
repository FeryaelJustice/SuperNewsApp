

package com.feryaeljustice.supernewsapp.presentation.bookmark

import com.feryaeljustice.supernewsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
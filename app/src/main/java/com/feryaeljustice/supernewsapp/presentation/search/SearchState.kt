

package com.feryaeljustice.supernewsapp.presentation.search

import androidx.paging.PagingData
import com.feryaeljustice.supernewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(val searchQuery: String = "", val articles: Flow<PagingData<Article>>? = null)
/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeldev.supernewsapp.presentation.search

import androidx.paging.PagingData
import com.feryaeldev.supernewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(val searchQuery: String = "", val articles: Flow<PagingData<Article>>? = null) {
}
/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.feryaeljustice.supernewsapp.domain.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String,
    private val sources: String,
    private val apiKey: String
) :
    PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val aMonthAgo = LocalDateTime.now().minusMonths(1).format(formatter)
            val current = LocalDateTime.now().format(formatter)

            val response = newsApi.searchNews(
                query,
                page,
                sources,
                from = aMonthAgo,
                to = current,
                apiKey = apiKey
            )
            totalNewsCount += response.articles.size
            val articles = response.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}
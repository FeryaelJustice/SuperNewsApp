package com.feryaeljustice.supernewsapp.data.remote

import androidx.annotation.Keep
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.feryaeljustice.supernewsapp.domain.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Keep
class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String,
    private val sources: String,
    private val apiKey: String,
) : PagingSource<Int, Article>() {
    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val aMonthAgo = LocalDateTime.now().minusMonths(1).format(formatter)
            val current = LocalDateTime.now().format(formatter)

            val response =
                newsApi.searchNews(
                    page = page,
                    pageSize = 20,
                    query,
                    sources,
                    from = aMonthAgo,
                    to = current,
                    apiKey = apiKey,
                )
            totalNewsCount += response.articles.size
            val articles = response.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}

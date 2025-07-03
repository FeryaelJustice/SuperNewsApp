package com.feryaeljustice.supernewsapp.data.repository

import androidx.annotation.Keep
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.feryaeljustice.supernewsapp.annotations.NewsApiKey
import com.feryaeljustice.supernewsapp.data.local.NewsDao
import com.feryaeljustice.supernewsapp.data.remote.NewsApi
import com.feryaeljustice.supernewsapp.data.remote.NewsPagingSource
import com.feryaeljustice.supernewsapp.data.remote.SearchNewsPagingSource
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Keep
class NewsRepositoryImpl
@Inject
constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    @param:NewsApiKey private val apiKey: String,
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                NewsPagingSource(newsApi, sources.joinToString(","), apiKey)
            },
        ).flow

    override fun searchNews(
        searchQuery: String,
        sources: List<String>,
    ): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchNewsPagingSource(newsApi, searchQuery, sources.joinToString(","), apiKey)
            },
        ).flow

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun getArticles(): Flow<List<Article>> = newsDao.getArticles()

    override suspend fun getArticle(url: String): Article? = newsDao.getArticle(url = url)
}

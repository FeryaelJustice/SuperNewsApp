package com.feryaeljustice.supernewsapp.domain.usecase.news

import com.feryaeljustice.supernewsapp.data.local.NewsDao
import com.feryaeljustice.supernewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArticles
@Inject
constructor(
    private val newsDao: NewsDao,
) {
    operator fun invoke(): Flow<List<Article>> = newsDao.getArticles()
}

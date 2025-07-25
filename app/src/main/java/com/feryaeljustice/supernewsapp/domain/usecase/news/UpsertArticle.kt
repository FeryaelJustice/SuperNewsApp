package com.feryaeljustice.supernewsapp.domain.usecase.news

import com.feryaeljustice.supernewsapp.data.local.NewsDao
import com.feryaeljustice.supernewsapp.domain.model.Article
import javax.inject.Inject

class UpsertArticle
@Inject
constructor(
    private val newsDao: NewsDao,
) {
    suspend operator fun invoke(article: Article) {
        newsDao.upsert(article = article)
    }
}

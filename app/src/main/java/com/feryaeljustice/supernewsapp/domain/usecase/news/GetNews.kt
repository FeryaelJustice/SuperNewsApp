package com.feryaeljustice.supernewsapp.domain.usecase.news

import com.feryaeljustice.supernewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNews
@Inject
constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(sources: List<String>) = newsRepository.getNews(sources)
}

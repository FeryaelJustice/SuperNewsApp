

package com.feryaeljustice.supernewsapp.domain.usecase.news

import com.feryaeljustice.supernewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class SearchNews @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(searchQuery: String, sources: List<String>) =
        newsRepository.searchNews(searchQuery, sources)

}
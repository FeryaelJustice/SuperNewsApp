package com.feryaeljustice.supernewsapp.domain.usecase.news

import androidx.paging.PagingData
import androidx.paging.filter
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import java.time.format.DateTimeParseException
import javax.inject.Inject

class SearchNews
@Inject
constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        searchQuery: String,
        sources: List<String>,
    ): Flow<PagingData<Article>> =
        newsRepository.searchNews(searchQuery, sources).map { pagingData ->
            pagingData.filter { article ->
                // Intentamos parsear publishedAt como ISO-8601
                val publishedDate = try {
                    OffsetDateTime.parse(article.publishedAt)
                } catch (e: DateTimeParseException) {
                    // si no se puede parsear, descartamos la noticia
                    return@filter false
                }
                // calculamos hace 3 meses
                val threeMonthsAgo = OffsetDateTime.now().minusMonths(3)
                // sólo dejamos las más recientes
                publishedDate.isAfter(threeMonthsAgo)
            }
        }
}

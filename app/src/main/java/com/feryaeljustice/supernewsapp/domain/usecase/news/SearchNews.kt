/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.domain.usecase.news

import com.feryaeljustice.supernewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class SearchNews @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(searchQuery: String, sources: List<String>) =
        newsRepository.searchNews(searchQuery, sources)

}
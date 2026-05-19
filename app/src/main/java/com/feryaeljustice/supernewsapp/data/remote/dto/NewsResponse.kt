package com.feryaeljustice.supernewsapp.data.remote.dto

import androidx.annotation.Keep
import com.feryaeljustice.supernewsapp.domain.model.Article
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NewsResponse(
    @SerialName("articles") val articles: List<Article>,
    @SerialName("status") val status: String,
    @SerialName("totalResults") val totalResults: Int,
)

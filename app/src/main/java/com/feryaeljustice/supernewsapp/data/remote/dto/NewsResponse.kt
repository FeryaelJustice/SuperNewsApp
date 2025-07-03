package com.feryaeljustice.supernewsapp.data.remote.dto

import androidx.annotation.Keep
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

@Keep
data class NewsResponse(
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
)

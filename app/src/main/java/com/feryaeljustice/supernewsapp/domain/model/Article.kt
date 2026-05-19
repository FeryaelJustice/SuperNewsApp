package com.feryaeljustice.supernewsapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity
@Keep
data class Article(
    @SerialName("author") val author: String? = "",
    @SerialName("content") val content: String? = "",
    @SerialName("description") val description: String? = "",
    @SerialName("publishedAt") val publishedAt: String? = "",
    @SerialName("source") val source: Source? = null,
    @SerialName("title") val title: String? = "",
    @SerialName("url") @PrimaryKey val url: String,
    @SerialName("urlToImage") val urlToImage: String? = "",
) : Parcelable

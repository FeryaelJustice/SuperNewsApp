

package com.feryaeljustice.supernewsapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
@Keep
data class Article(
    @SerializedName("author") val author: String? = "",
    @SerializedName("content") val content: String,
    @SerializedName("description") val description: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("source") val source: Source,
    @SerializedName("title") val title: String? = "",
    @SerializedName("url") @PrimaryKey val url: String,
    @SerializedName("urlToImage") val urlToImage: String? = ""
) : Parcelable
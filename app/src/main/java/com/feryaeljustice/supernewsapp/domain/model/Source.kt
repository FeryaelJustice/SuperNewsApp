package com.feryaeljustice.supernewsapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Keep
data class Source(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String?,
) : Parcelable

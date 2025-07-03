package com.feryaeljustice.supernewsapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Source(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
) : Parcelable

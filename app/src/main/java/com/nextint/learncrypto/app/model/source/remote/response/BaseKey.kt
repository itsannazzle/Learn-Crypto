package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseKeyPrice(

    @field:SerializedName("price")
    val price: Double,

    @field:SerializedName("volume_24h")
    val volume24h: Double
) : Parcelable

@Parcelize
data class BaseKeyVolume(

    @field:SerializedName("adjusted_volume_7d")
    val adjustedVolume7d: Int,

    @field:SerializedName("reported_volume_7d")
    val reportedVolume7d: Int,

    @field:SerializedName("adjusted_volume_24h")
    val adjustedVolume24h: Int,

    @field:SerializedName("adjusted_volume_30d")
    val adjustedVolume30d: Int,

    @field:SerializedName("reported_volume_24h")
    val reportedVolume24h: Int,

    @field:SerializedName("reported_volume_30d")
    val reportedVolume30d: Int
) : Parcelable
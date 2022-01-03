package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(

    @field:SerializedName("youtube")
    val youtube: List<String>,

    @field:SerializedName("website")
    val website: List<String>,

    @field:SerializedName("facebook")
    val facebook: List<String>,

    @field:SerializedName("explorer")
    val explorer: List<String>,

    @field:SerializedName("reddit")
    val reddit: List<String>,

    @field:SerializedName("medium")
    val medium: String? = "",

    @field:SerializedName("source_code")
    val sourceCode: List<String>
) : Parcelable
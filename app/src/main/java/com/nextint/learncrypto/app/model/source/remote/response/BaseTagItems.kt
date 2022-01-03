package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagsItem(

    @field:SerializedName("coin_counter")
    val coinCounter: Int,

    @field:SerializedName("ico_counter")
    val icoCounter: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String
) : Parcelable
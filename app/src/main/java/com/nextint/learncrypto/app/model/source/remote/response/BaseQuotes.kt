package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseQuotesPrice(

    @field:SerializedName("$/KEY")
    val baseKeyPrice: BaseKeyPrice
) : Parcelable

@Parcelize
data class BaseQuotesVolume(

    @field:SerializedName("$/KEY")
    val baseKeyPrice: BaseKeyPrice
) : Parcelable
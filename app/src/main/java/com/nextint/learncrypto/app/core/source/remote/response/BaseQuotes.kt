package com.nextint.learncrypto.app.core.source.remote.response

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

@Parcelize
data class BaseQuotesPriceUSD(

    @field:SerializedName("USD")
    val baseKeyPrice: BaseKeyPrice
) : Parcelable

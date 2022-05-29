package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceConvertedResponse(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("quote_currency_id")
	val quoteCurrencyId: String? = null,

	@field:SerializedName("base_currency_id")
	val baseCurrencyId: String? = null,

	@field:SerializedName("quote_price_last_updated")
	val quotePriceLastUpdated: String? = null,

	@field:SerializedName("quote_currency_name")
	val quoteCurrencyName: String? = null,

	@field:SerializedName("base_currency_name")
	val baseCurrencyName: String? = null,

	@field:SerializedName("base_price_last_updated")
	val basePriceLastUpdated: String? = null
) : Parcelable

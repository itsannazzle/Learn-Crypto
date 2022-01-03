package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketsByCoinIdResponse(

	@field:SerializedName("MarketsByCoinIdResponse")
	val marketsByCoinIdResponse: List<MarketsByCoinIdResponseItem>
) : Parcelable

@Parcelize
data class MarketsByCoinIdResponseItem(

	@field:SerializedName("adjusted_volume_24h_share")
	val adjustedVolume24hShare: Double,

	@field:SerializedName("last_updated")
	val lastUpdated: String,

	@field:SerializedName("outlier")
	val outlier: Boolean,

	@field:SerializedName("base_currency_id")
	val baseCurrencyId: String,

	@field:SerializedName("fee_type")
	val feeType: String,

	@field:SerializedName("quote_currency_name")
	val quoteCurrencyName: String,

	@field:SerializedName("pair")
	val pair: String,

	@field:SerializedName("quotes")
	val quotes: BaseQuotesPrice,

	@field:SerializedName("exchange_name")
	val exchangeName: String,

	@field:SerializedName("market_url")
	val marketUrl: String,

	@field:SerializedName("exchange_id")
	val exchangeId: String,

	@field:SerializedName("quote_currency_id")
	val quoteCurrencyId: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("base_currency_name")
	val baseCurrencyName: String
) : Parcelable

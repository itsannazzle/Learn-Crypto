package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangesResponse(

	@field:SerializedName("ExchangesResponse")
	val exchangesResponse: List<ExchangesResponseItem>
) : Parcelable

@Parcelize
data class ExchangesResponseItem(

	@field:SerializedName("adjusted_rank")
	val adjustedRank: Int,

	@field:SerializedName("last_updated")
	val lastUpdated: String,

	@field:SerializedName("active")
	val active: Boolean,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("quotes")
	val quotes: BaseKeyVolume,

	@field:SerializedName("markets")
	val markets: Int,

	@field:SerializedName("markets_data_fetched")
	val marketsDataFetched: Boolean,

	@field:SerializedName("fiats")
	val fiats: List<FiatsItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("reported_rank")
	val reportedRank: Int,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("currencies")
	val currencies: Int,

	@field:SerializedName("confidence_score")
	val confidenceScore : Double? = 0.0

) : Parcelable


@Parcelize
data class FiatsItem(

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("name")
	val name: String
) : Parcelable



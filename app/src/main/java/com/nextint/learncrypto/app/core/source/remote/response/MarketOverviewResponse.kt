package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketOverviewResponse(

	@field:SerializedName("last_updated")
	val lastUpdated: Int,

	@field:SerializedName("volume_24h_ath_date")
	val volume24hAthDate: String,

	@field:SerializedName("volume_24h_change_24h")
	val volume24hChange24h: Double,

	@field:SerializedName("market_cap_usd")
	val marketCapUsd: Long,

	@field:SerializedName("market_cap_ath_date")
	val marketCapAthDate: String,

	@field:SerializedName("market_cap_change_24h")
	val marketCapChange24h: Double,

	@field:SerializedName("market_cap_ath_value")
	val marketCapAthValue: Long,

	@field:SerializedName("volume_24h_percent_to_ath")
	val volume24hPercentToAth: Double,

	@field:SerializedName("bitcoin_dominance_percentage")
	val bitcoinDominancePercentage: Double,

	@field:SerializedName("volume_24h_ath_value")
	val volume24hAthValue: Long,

	@field:SerializedName("volume_24h_usd")
	val volume24hUsd: Long,

	@field:SerializedName("cryptocurrencies_number")
	val cryptocurrenciesNumber: Int,

	@field:SerializedName("volume_24h_percent_from_ath")
	val volume24hPercentFromAth: Double
) : Parcelable

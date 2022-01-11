package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(

	@field:SerializedName("exchanges")
	val exchanges: List<ExchangesItem>,

	@field:SerializedName("people")
	val people: List<PeopleItem>,

	@field:SerializedName("currencies")
	val currencies: List<CurrenciesItem>,

	@field:SerializedName("icos")
	val icos: List<IcosItem>,

	@field:SerializedName("tags")
	val tags: List<TagsItem>
) : Parcelable

@Parcelize
data class ExchangesItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class IcosItem(

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("is_new")
	val isNew: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class CurrenciesItem(

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("is_new")
	val isNew: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable

@Parcelize
data class PeopleItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("teams_count")
	val teamsCount: Int
) : Parcelable


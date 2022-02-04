package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinByIdResponse(

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("parent")
	val parent: Parent,

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("is_new")
	val isNew: Boolean,

	@field:SerializedName("proof_type")
	val proofType: String,

	@field:SerializedName("first_data_at")
	val firstDataAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("team")
	val team: List<TeamItem>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("tags")
	val tags: List<TagsItem>,

	@field:SerializedName("last_data_at")
	val lastDataAt: String,

	@field:SerializedName("whitepaper")
	val whitepaper: Whitepaper,

	@field:SerializedName("org_structure")
	val orgStructure: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("development_status")
	val developmentStatus: String,

	@field:SerializedName("hash_algorithm")
	val hashAlgorithm: String,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("open_source")
	val isOpenSource: Boolean,

	@field:SerializedName("hardware-wallet")
	val isHardwareWallet: Boolean,

	@field:SerializedName("started_at")
	val startedAt: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class TeamItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("position")
	val position: String
) : Parcelable



@Parcelize
data class Whitepaper(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("link")
	val link: String? = null
) : Parcelable

@Parcelize
data class Parent(

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class Stats(

	@field:SerializedName("contributors")
	val contributors: Int,

	@field:SerializedName("stars")
	val stars: Int,

	@field:SerializedName("subscribers")
	val subscribers: Int
) : Parcelable




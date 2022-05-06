package com.nextint.learncrypto.app.core.source.remote.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinByIdResponse(

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("parent")
	val parent: Parent? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("is_new")
	val isNew: Boolean? = null,

	@field:SerializedName("proof_type")
	val proofType: String? = null,

	@field:SerializedName("first_data_at")
	val firstDataAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("team")
	val team: List<TeamItem>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("tags")
	val tags: List<TagByIdResponse>? = null,

	@field:SerializedName("last_data_at")
	val lastDataAt: String? = null,

	@field:SerializedName("whitepaper")
	val whitepaper: Whitepaper? = null,

	@field:SerializedName("org_structure")
	val orgStructure: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("development_status")
	val developmentStatus: String? = null,

	@field:SerializedName("hash_algorithm")
	val hashAlgorithm: String? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("open_source")
	val isOpenSource: Boolean? = null,

	@field:SerializedName("hardware-wallet")
	val isHardwareWallet: Boolean? = null,

	@field:SerializedName("started_at")
	val startedAt: String? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: String? = null
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




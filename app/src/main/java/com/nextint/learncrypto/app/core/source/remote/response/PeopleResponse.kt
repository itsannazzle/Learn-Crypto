package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeopleResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("links")
	val links: PersonSosMed,

	@field:SerializedName("positions")
	val positions: List<PositionsItem>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("teams_count")
	val teamsCount: Int
) : Parcelable

@Parcelize
data class MediumItem(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("url")
	val url: String
) : Parcelable

@Parcelize
data class LinkedinItem(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("url")
	val url: String
) : Parcelable

@Parcelize
data class PositionsItem(

	@field:SerializedName("coin_name")
	val coinName: String,

	@field:SerializedName("coin_id")
	val coinId: String,

	@field:SerializedName("position")
	val position: String
) : Parcelable

@Parcelize
data class AdditionalItem(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("url")
	val url: String
) : Parcelable

@Parcelize
data class TwitterItem(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("url")
	val url: String
) : Parcelable

@Parcelize
data class GithubItem(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("url")
	val url: String
) : Parcelable

@Parcelize
data class PersonSosMed(

	@field:SerializedName("github")
	val github: List<GithubItem>,

	@field:SerializedName("twitter")
	val twitter: List<TwitterItem>,

	@field:SerializedName("additional")
	val additional: List<AdditionalItem>,

	@field:SerializedName("linkedin")
	val linkedin: List<LinkedinItem>,

	@field:SerializedName("medium")
	val medium: List<MediumItem>
) : Parcelable

package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagsResponse(

	@field:SerializedName("VocabularyResponse")
	val vocabularyResponse: List<TagsResponseItem>
) : Parcelable

@Parcelize
data class TagsResponseItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable

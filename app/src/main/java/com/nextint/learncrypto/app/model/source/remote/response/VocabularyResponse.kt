package com.nextint.learncrypto.app.model.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VocabularyResponse(

	@field:SerializedName("VocabularyResponse")
	val vocabularyResponse: List<VocabularyResponseItem>
) : Parcelable

@Parcelize
data class VocabularyResponseItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable

package com.nextint.learncrypto.app.core.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagByIdResponse(

	@field:SerializedName("coin_counter")
	val coinCounter: Int? = null,

	@field:SerializedName("ico_counter")
	val icoCounter: Int? = null,

	@field:SerializedName("coins")
	val coins: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("icos")
	val icos: List<String?>? = null
) : Parcelable

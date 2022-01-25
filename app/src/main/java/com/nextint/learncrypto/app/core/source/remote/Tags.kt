package com.nextint.learncrypto.app.core.source.remote

import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import retrofit2.http.GET

interface Tags {

    @GET("tags")
    suspend fun getAllVocabulary() : TagsResponse
}
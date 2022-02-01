package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import retrofit2.http.GET

interface TagsService {

    @GET("tags")
    suspend fun getAllVocabulary() : TagsResponse
}
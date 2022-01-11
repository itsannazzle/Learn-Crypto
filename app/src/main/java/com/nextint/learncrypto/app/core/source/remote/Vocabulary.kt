package com.nextint.learncrypto.app.core.source.remote

import com.nextint.learncrypto.app.core.source.remote.response.VocabularyResponse
import retrofit2.http.GET

interface Vocabulary {

    @GET("tags")
    suspend fun getAllVocabulary() : VocabularyResponse
}
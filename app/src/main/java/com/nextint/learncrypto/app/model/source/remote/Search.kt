package com.nextint.learncrypto.app.model.source.remote

import com.nextint.learncrypto.app.model.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Search {
    @GET("search")
    suspend fun search(
        @Query("q") keyword : String
    ) : SearchResponse
}
package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService
{
    @GET("search")
    suspend fun search(@Query("q") keyword : String) : SearchResponse
}
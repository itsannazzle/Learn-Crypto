package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TagsService
{

    @GET("tags")
    suspend fun getAllTag() : List<TagByIdResponse>

    @GET("tags/{tag_id}")
    suspend fun getTagById(@Path("tag_id")stringTagId : String) : TagByIdResponse
}
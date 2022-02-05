package com.nextint.learncrypto.app.features.tags.data

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.networksource.TagsNetwork
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(private val remoteData : TagsNetwork) : ITagsRepository
{
    override suspend fun getAllTags(): Flow<ApiResponse<TagsResponse>>
    {
        return remoteData.getAllTags()
    }

    override suspend fun getTagById(stringTagId: String): Flow<ApiResponse<TagByIdResponse>>
    {
        return remoteData.getTagById(stringTagId)
    }
}
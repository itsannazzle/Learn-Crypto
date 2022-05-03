package com.nextint.learncrypto.app.features.concept.data

import com.nextint.learncrypto.app.core.source.remote.network.TagsNetwork
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(private val remoteData : TagsNetwork) : ITagsRepository
{
    override suspend fun getAllTags(): Flow<ApiResponse<List<TagByIdResponse>>>
    {
        return remoteData.getAllTags()
    }

    override suspend fun getTagById(stringTagId: String): Flow<ApiResponse<TagByIdResponse>>
    {
        return remoteData.getTagById(stringTagId)
    }
}

interface ITagsRepository
{
    suspend fun getAllTags() : Flow<ApiResponse<List<TagByIdResponse>>>

    suspend fun getTagById(stringTagId: String) : Flow<ApiResponse<TagByIdResponse>>
}
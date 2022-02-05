package com.nextint.learncrypto.app.features.tags.data

import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TagsUseCase
{
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>

    suspend fun getTagById(stringTagId : String) : Flow<ApiResponse<TagByIdResponse>>
}

interface ITagsRepository
{
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>

    suspend fun getTagById(stringTagId: String) : Flow<ApiResponse<TagByIdResponse>>
}

class TagsUseCaseImpl @Inject constructor(private val iTagsRepository: ITagsRepository) :
    TagsUseCase
{
    override suspend fun getAllTags(): Flow<ApiResponse<TagsResponse>>
    {
        return iTagsRepository.getAllTags()
    }

    override suspend fun getTagById(stringTagId: String): Flow<ApiResponse<TagByIdResponse>>
    {
        return iTagsRepository.getTagById(stringTagId)
    }
}
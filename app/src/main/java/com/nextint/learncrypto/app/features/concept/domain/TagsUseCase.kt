package com.nextint.learncrypto.app.features.concept.domain

import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.features.concept.data.ITagsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TagsUseCase
{
    suspend fun getAllTags() : Flow<ApiResponse<List<TagByIdResponse>>>

    suspend fun getTagById(stringTagId : String) : Flow<ApiResponse<TagByIdResponse>>
}

class TagsUseCaseImpl @Inject constructor(private val iTagsRepository: ITagsRepository) :
    TagsUseCase
{
    override suspend fun getAllTags(): Flow<ApiResponse<List<TagByIdResponse>>>
    {
        return iTagsRepository.getAllTags()
    }

    override suspend fun getTagById(stringTagId: String): Flow<ApiResponse<TagByIdResponse>>
    {
        return iTagsRepository.getTagById(stringTagId)
    }
}
package com.nextint.learncrypto.app.features.concept.data

import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TagsUseCase {
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>
}

interface ITagsRepository {
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>
}

class TagsUseCaseImpl @Inject constructor(private val iTagsRepository: ITagsRepository) : TagsUseCase
{
    override suspend fun getAllTags(): Flow<ApiResponse<TagsResponse>> {
        return iTagsRepository.getAllTags()
    }
}
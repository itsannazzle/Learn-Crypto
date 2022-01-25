package com.nextint.learncrypto.app.features.concept.data

import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.networksource.TagsNetwork
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(private val remoteData : TagsNetwork) : ITagsRepository {
    override suspend fun getAllTags(): Flow<ApiResponse<TagsResponse>> {
        return remoteData.getAllTags()
    }
}
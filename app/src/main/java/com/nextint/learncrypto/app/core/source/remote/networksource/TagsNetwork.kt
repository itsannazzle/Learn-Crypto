package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.TagsService
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsNetwork @Inject constructor(private val tagsServiceService : TagsService)
{
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>
    {
        return flow ()
        {
            val response = tagsServiceService.getAllTag()
            try
            {
                if (response.vocabularyResponse.isNotEmpty())
                {
                    emit(ApiResponse.Success(response))
                } else
                {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception)
            {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTagById(stringTagId : String) : Flow<ApiResponse<TagByIdResponse>>
    {
        return flow()
        {
            val response = tagsServiceService.getTagById(stringTagId)
            try
            {
                emit(ApiResponse.Success(response))
            } catch (exception : Exception)
            {
                emit(ApiResponse.Error(exception.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}